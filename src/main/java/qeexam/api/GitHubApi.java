package qeexam.api;

// [REFACTOR] ĐỔI PACKAGE: API -> qeexam.api (tên package Java viết thường toàn bộ, nằm dưới package gốc qeexam).

import qeexam.commons.ConfigReader;
import qeexam.commons.GlobalConstants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
// [REFACTOR] CŨ: không còn dùng Map sau khi chuyển sang POJO Repo.
// import java.util.Map;
import java.util.Properties;

public class GitHubApi {

    // [REFACTOR] MỚI: số repo mỗi trang, dùng chung cho query param và điều kiện dừng phân trang.
    private static final int PER_PAGE = 100;

    // [REFACTOR] MỚI: token chỉ đọc một lần cho cả lần chạy (xem getGitHubToken()).
    private static String cachedToken;
    private static boolean tokenLoaded;

    // [REFACTOR] MỚI: base URL lấy từ -Dgithub.api.url nếu có (GitHub Enterprise, mock server khi test
    // offline), mặc định vẫn là GlobalConstants.GITHUB_API_URL như cũ.
    private final String baseUrl = ConfigReader.get("github.api.url", GlobalConstants.GITHUB_API_URL);

    // l
    // [REFACTOR] CŨ: private String loadGitHubToken() {
    // [REFACTOR] MỚI: thêm static để gọi được từ getGitHubToken() (token cache dùng chung cho cả lần chạy).
    private static String loadGitHubToken() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("github.properties")) {
            props.load(fis);
            return props.getProperty("github.token");
        } catch (IOException e) {
            return null;
        }
    }

    // [REFACTOR] MỚI: ưu tiên biến môi trường GITHUB_TOKEN (tiện cho CI, không cần tạo file), không có
    // thì đọc github.properties như cũ. Kết quả được cache vì trước đây file bị đọc lại ở MỖI request.
    private static synchronized String getGitHubToken() {
        if (!tokenLoaded) {
            String envToken = System.getenv("GITHUB_TOKEN");
            cachedToken = (envToken != null && !envToken.trim().isEmpty()) ? envToken : loadGitHubToken();
            tokenLoaded = true;
        }
        return cachedToken;
    }

    private RequestSpecification buildRequest() {
        RequestSpecification req = RestAssured.given()
                // [REFACTOR] MỚI: đặt baseUri một chỗ thay vì lặp lại ở từng request.
                .baseUri(baseUrl)
                .header("Accept", "application/vnd.github+json")
                .header("X-GitHub-Api-Version", "2022-11-28")
                .header("User-Agent", "test");

        // Đọc token từ file github.properties
        // [REFACTOR] CŨ: đọc lại file ở mỗi request.
        // String token = loadGitHubToken();
        // [REFACTOR] MỚI:
        String token = getGitHubToken();
        if (token != null && !token.trim().isEmpty() && !token.contains("YOUR_GITHUB_TOKEN")) {
            req.header("Authorization", "Bearer " + token.trim());
        }

        return req;
    }


    // [REFACTOR] CŨ: ép kiểu thủ công giá trị lấy từ Map. Không còn cần vì Jackson đã map thẳng sang int.
    // private int toInt(Object v) {
    //     if (v == null) return 0;
    //     if (v instanceof Integer) return (Integer) v;
    //     if (v instanceof Long) return ((Long) v).intValue();
    //     if (v instanceof Double) return ((Double) v).intValue();
    //     return Integer.parseInt(v.toString());
    // }

    // [REFACTOR] MỚI: in lỗi (nếu có) rồi assert status 200; tách ra để dùng chung cho mọi request.
    private void assertOk(Response response) {
        if (response.getStatusCode() != 200) {
            System.err.println("GitHub API Error - Status: " + response.getStatusCode());
            System.err.println("Response body: " + response.getBody().asString());
        }
        response.then().statusCode(200);
    }


    // Get all repos for an org with pagination
    // [REFACTOR] CŨ: trả về List<Map<String, Object>> và luôn gọi thêm một request để nhận trang rỗng.
    // public List<Map<String, Object>> fetchAllRepos(String org) {
    //     List<Map<String, Object>> allRepos = new java.util.ArrayList<>();
    //     int page = 1;
    //
    //     while (true) {
    //         Response response = buildRequest()
    //                 .baseUri(GlobalConstants.GITHUB_API_URL)
    //                 .basePath("/orgs/{org}/repos")
    //                 .pathParam("org", org)
    //                 .queryParam("per_page", 100)
    //                 .queryParam("page", page)
    //                 .get();
    //
    //         if (response.getStatusCode() != 200) {
    //             System.err.println("GitHub API Error - Status: " + response.getStatusCode());
    //             System.err.println("Response body: " + response.getBody().asString());
    //         }
    //
    //         response.then().statusCode(200);
    //
    //         List<Map<String, Object>> repos = response.jsonPath().getList("$");
    //         if (repos == null || repos.isEmpty()) break;
    //
    //         allRepos.addAll(repos);
    //         page++;
    //     }
    //
    //     return allRepos;
    // }
    // [REFACTOR] MỚI: trả về List<Repo>; dừng ngay khi một trang ít hơn PER_PAGE (đó là trang cuối),
    // tiết kiệm một request - đáng kể khi không có token (giới hạn 60 request/giờ).
    public List<Repo> fetchAllRepos(String org) {
        List<Repo> allRepos = new ArrayList<>();
        int page = 1;

        while (true) {
            Response response = buildRequest()
                    .basePath("/orgs/{org}/repos")
                    .pathParam("org", org)
                    .queryParam("per_page", PER_PAGE)
                    .queryParam("page", page)
                    .get();

            assertOk(response);

            List<Repo> repos = Arrays.asList(response.as(Repo[].class));
            allRepos.addAll(repos);
            if (repos.size() < PER_PAGE) break;

            page++;
        }

        return allRepos;
    }

    // [REFACTOR] MỚI: lấy số repo public mà GitHub công bố cho org (GET /orgs/{org}), dùng để đối chiếu
    // với số repo thu được qua phân trang -> phát hiện lỗi phân trang (thiếu/trùng trang).
    public int fetchPublicRepoCount(String org) {
        Response response = buildRequest()
                .basePath("/orgs/{org}")
                .pathParam("org", org)
                .get();

        assertOk(response);

        return response.jsonPath().getInt("public_repos");
    }

    // Calculate total open issues across all repos
    // [REFACTOR] CŨ:
    // public int calculateTotalOpenIssues(List<Map<String, Object>> repos) {
    //     int total = 0;
    //     for (Map<String, Object> repo : repos) {
    //         total += toInt(repo.get("open_issues_count"));
    //     }
    //     return total;
    // }
    // [REFACTOR] MỚI: cùng logic, đọc qua getter của Repo.
    public int calculateTotalOpenIssues(List<Repo> repos) {
        int total = 0;
        for (Repo repo : repos) {
            total += repo.getOpenIssuesCount();
        }
        return total;
    }

    // Find the repo with the most stars
    // [REFACTOR] CŨ: maxStars khởi tạo = 0 và so sánh ">" nên nếu mọi repo đều 0 sao thì trả về null.
    // public Map<String, Object> findTopStarredRepo(List<Map<String, Object>> repos) {
    //     Map<String, Object> topRepo = null;
    //     int maxStars = 0;
    //
    //     for (Map<String, Object> repo : repos) {
    //         int stars = toInt(repo.get("stargazers_count"));
    //         if (stars > maxStars) {
    //             maxStars = stars;
    //             topRepo = repo;
    //         }
    //     }
    //
    //     return topRepo;
    // }
    // [REFACTOR] MỚI: maxStars khởi tạo = -1 nên chỉ trả null khi danh sách rỗng.
    public Repo findTopStarredRepo(List<Repo> repos) {
        Repo topRepo = null;
        int maxStars = -1;

        for (Repo repo : repos) {
            if (repo.getStargazersCount() > maxStars) {
                maxStars = repo.getStargazersCount();
                topRepo = repo;
            }
        }

        return topRepo;
    }


}
