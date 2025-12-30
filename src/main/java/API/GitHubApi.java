package API;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GitHubApi {

    private final RequestSpecification spec;

    public GitHubApi() {
        RequestSpecBuilder b = new RequestSpecBuilder()
                .setBaseUri("https://api.github.com")
                .addHeader("Accept", "application/vnd.github+json")
                .addHeader("X-GitHub-Api-Version", "2022-11-28");

        String token = System.getenv("GITHUB_TOKEN");
        if (token != null && !token.isBlank()) {
            b.addHeader("Authorization", "Bearer " + token);
        }

        this.spec = b.build();
    }

    public AnalysisResult analyzeOrg(String org) {
        List<Map<String, Object>> repos = fetchAllRepos(org);

        long sumOpenIssuesCount = 0;
        Map<String, Object> topStarRepo = null;
        int bestStars = -1;

        for (Map<String, Object> r : repos) {
            sumOpenIssuesCount += toLong(r.get("open_issues_count"));

            int stars = (int) toLong(r.get("stargazers_count"));
            if (stars > bestStars) {
                bestStars = stars;
                topStarRepo = r;
            }
        }

        int issuesOnlyOpenCount = fetchIssuesOnlyOpenCount(org);

        AnalysisResult out = new AnalysisResult();
        out.org = org;
        out.repoCount = repos.size();
        out.sumOpenIssuesCount = sumOpenIssuesCount;         // issues + PR (thường là vậy)
        out.issuesOnlyOpenCount = issuesOnlyOpenCount;       // issues-only
        out.topStarRepoFullName = topStarRepo == null ? null : String.valueOf(topStarRepo.get("full_name"));
        out.topStars = Math.max(bestStars, 0);

        return out;
    }

    private List<Map<String, Object>> fetchAllRepos(String org) {
        List<Map<String, Object>> all = new ArrayList<>();
        int page = 1;

        while (true) {
            Response res = given(spec)
                    .queryParam("per_page", 100)
                    .queryParam("page", page)
                    .get("/orgs/" + org + "/repos")
                    .then()
                    .statusCode(200)
                    .extract().response();

            List<Map<String, Object>> batch = res.jsonPath().getList("");
            if (batch == null || batch.isEmpty()) break;

            all.addAll(batch);

            // Nếu Link header không có rel="next" thì dừng luôn
            String link = res.getHeader("Link");
            boolean hasNext = link != null && link.contains("rel=\"next\"");
            if (!hasNext) break;

            page++;
        }

        return all;
    }

    private int fetchIssuesOnlyOpenCount(String org) {
        Response res = given(spec)
                .queryParam("q", "org:" + org + " is:issue is:open")
                .queryParam("per_page", 1)
                .get("/search/issues")
                .then()
                .statusCode(200)
                .extract().response();

        return res.jsonPath().getInt("total_count");
    }

    private long toLong(Object o) {
        if (o == null) return 0;
        if (o instanceof Number) return ((Number) o).longValue();
        try {
            return Long.parseLong(o.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    // Result object gọn gàng để Steps xài
    public static class AnalysisResult {
        public String org;
        public int repoCount;
        public long sumOpenIssuesCount;
        public int issuesOnlyOpenCount;
        public String topStarRepoFullName;
        public int topStars;
    }
}
