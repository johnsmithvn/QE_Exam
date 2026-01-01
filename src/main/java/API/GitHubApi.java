package API;

import commons.GlobalConstants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class GitHubApi {

    private RequestSpecification buildRequest() {
        RequestSpecification req = RestAssured.given()
                .header("Accept", "application/vnd.github+json")
                .header("X-GitHub-Api-Version", "2022-11-28")
                .header("User-Agent", "test");
        
        // Đọc token từ file github.properties
        String token = loadGitHubToken();
        if (token != null && !token.trim().isEmpty() && !token.contains("YOUR_GITHUB_TOKEN")) {
            req.header("Authorization", "Bearer " + token.trim());
        }
        
        return req;
    }

    private String loadGitHubToken() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("github.properties")) {
            props.load(fis);
            return props.getProperty("github.token");
        } catch (IOException e) {
            return null;
        }
    }

    private int toInt(Object v) {
        if (v == null) return 0;
        if (v instanceof Integer) return (Integer) v;
        if (v instanceof Long) return ((Long) v).intValue();
        if (v instanceof Double) return ((Double) v).intValue();
        return Integer.parseInt(v.toString());
    }


    public List<Map<String, Object>> fetchAllRepos(String org) {
        List<Map<String, Object>> allRepos = new java.util.ArrayList<>();
        int page = 1;

        while (true) {
            Response response = buildRequest()
                    .baseUri(GlobalConstants.GITHUB_API_URL)
                    .basePath("/orgs/{org}/repos")
                    .pathParam("org", org)
                    .queryParam("per_page", 100)
                    .queryParam("page", page)
                    .get();

            if (response.getStatusCode() != 200) {
                System.err.println("GitHub API Error - Status: " + response.getStatusCode());
                System.err.println("Response body: " + response.getBody().asString());
            }

            response.then().statusCode(200);

            List<Map<String, Object>> repos = response.jsonPath().getList("$");
            if (repos == null || repos.isEmpty()) break;

            allRepos.addAll(repos);
            page++;
        }

        return allRepos;
    }

    // Tính tổng open issues từ danh sách repos
    public int calculateTotalOpenIssues(List<Map<String, Object>> repos) {
        int total = 0;
        for (Map<String, Object> repo : repos) {
            total += toInt(repo.get("open_issues_count"));
        }
        return total;
    }

    // Tìm repo có nhiều stars nhất
    public Map<String, Object> findTopStarredRepo(List<Map<String, Object>> repos) {
        Map<String, Object> topRepo = null;
        int maxStars = 0;

        for (Map<String, Object> repo : repos) {
            int stars = toInt(repo.get("stargazers_count"));
            if (stars > maxStars) {
                maxStars = stars;
                topRepo = repo;
            }
        }

        return topRepo;
    }


}
