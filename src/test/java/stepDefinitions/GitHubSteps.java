package stepDefinitions;

import java.util.List;
// [REFACTOR] CŨ: không còn dùng Map sau khi chuyển sang POJO Repo.
// import java.util.Map;

import org.junit.Assert;

import API.GitHubApi;
import API.Repo;
import API.RepoResult;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GitHubSteps {

    GitHubApi gitHubApi;
    String orgName;
    // [REFACTOR] CŨ:
    // List<Map<String, Object>> allRepos;
    // Map<String, Object> topRepo;
    // [REFACTOR] MỚI: dùng POJO Repo thay cho Map (lý do: xem API/Repo.java).
    List<Repo> allRepos;
    Repo topRepo;
    int totalOpenIssues;

    public GitHubSteps() {
        gitHubApi = new GitHubApi();
    }

    @Given("^I call the GitHub REST API for organization \"([^\"]*)\"$")
    public void iCallTheGitHubRestApiForOrganization(String org) {
        orgName = org;
        allRepos = gitHubApi.fetchAllRepos(org);
        System.out.println("All repositories: " + allRepos.size() + " from " + org);
    }

    @When("I calculate the total number of open issues across all repositories")
    public void iCalculateTheTotalNumberOfOpenIssuesAcrossAllRepositories() {
        totalOpenIssues = gitHubApi.calculateTotalOpenIssues(allRepos);
        System.out.println("Calculated total open issues: " + totalOpenIssues);
    }

    @When("I determine the highest-rated repository by the highest number of stars")
    public void iDetermineTheHighestRatedRepositoryByTheHighestNumberOfStars() {
        topRepo = gitHubApi.findTopStarredRepo(allRepos);
        // [REFACTOR] MỚI: báo lỗi rõ ràng thay vì NullPointerException ở dòng in bên dưới khi org không có repo.
        Assert.assertNotNull("Organization has no repositories, cannot determine top repo", topRepo);
        // [REFACTOR] CŨ:
        // System.out
        //         .println("Found top repo: " + topRepo.get("name") + " (" + topRepo.get("stargazers_count") + " stars)");
        // [REFACTOR] MỚI:
        System.out.println("Found top repo: " + topRepo.getName() + " (" + topRepo.getStargazersCount() + " stars)");
    }

    @Then("I print the GitHub analysis result")
    public void iPrintTheGitHubAnalysisResult() {
        Assert.assertNotNull("Repos list should not be null", allRepos);
        Assert.assertNotNull("Top repo should not be null", topRepo);

        // [REFACTOR] MỚI: gom kết quả vào RepoResult - lớp này có sẵn trong project nhưng trước đây không dùng.
        RepoResult result = new RepoResult(orgName, allRepos.size(), totalOpenIssues, topRepo.getName(),
                topRepo.getStargazersCount());

        System.out.println("\n========== GITHUB ANALYSIS RESULT ==========");
        // [REFACTOR] CŨ:
        // System.out.println("Organization: " + orgName);
        // System.out.println("Total repositories: " + allRepos.size());
        // System.out.println("Total open issues across all repositories: " + totalOpenIssues);
        // System.out.println("Highest-rated repository: " + topRepo.get("name"));
        // System.out.println("Stars: " + topRepo.get("stargazers_count"));
        // [REFACTOR] MỚI: nội dung in ra giữ nguyên, chỉ đổi nguồn dữ liệu sang RepoResult.
        System.out.println("Organization: " + result.org);
        System.out.println("Total repositories: " + result.totalRepos);
        System.out.println("Total open issues across all repositories: " + result.totalOpenIssues);
        System.out.println("Highest-rated repository: " + result.bestRepoName);
        System.out.println("Stars: " + result.bestRepoStars);
        System.out.println("============================================\n");

        Assert.assertTrue("Org should have repos", allRepos.size() > 0);
        // [REFACTOR] CŨ: các assert này gần như không bao giờ fail (số đếm luôn >= 0, name vừa được dùng ở trên)
        // nên không phát hiện được lỗi nào.
        // Assert.assertTrue("Total open issues should be >= 0", totalOpenIssues >= 0);
        // Assert.assertNotNull("Top repo name should not be null", topRepo.get("name"));
        // Assert.assertTrue("Top repo stars should be >= 0", (Integer) topRepo.get("stargazers_count") >= 0);
        // [REFACTOR] MỚI: thay bằng 2 step kiểm tra có ý nghĩa bên dưới (khai báo trong API.feature).
    }

    // [REFACTOR] MỚI: kiểm tra kết quả "repo nhiều sao nhất" với giá trị mong đợi ghi trong feature.
    @Then("^the highest-rated repository should be \"([^\"]*)\"$")
    public void theHighestRatedRepositoryShouldBe(String expectedRepoName) {
        Assert.assertEquals("Highest-rated repository of " + orgName, expectedRepoName, topRepo.getName());
    }

    // [REFACTOR] MỚI: đối chiếu số repo lấy được qua phân trang với con số GitHub công bố cho org,
    // để bắt lỗi phân trang (thiếu hoặc trùng trang).
    @Then("the total number of repositories should match the organization public repository count")
    public void theTotalNumberOfRepositoriesShouldMatchTheOrganizationPublicRepositoryCount() {
        int publicRepoCount = gitHubApi.fetchPublicRepoCount(orgName);
        Assert.assertEquals("Number of repositories fetched for " + orgName, publicRepoCount, allRepos.size());
    }
}
