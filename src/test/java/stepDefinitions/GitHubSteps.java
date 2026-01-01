package stepDefinitions;

import org.junit.Assert;

import API.GitHubApi;
import API.RepoResult;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GitHubSteps {
    GitHubApi gitHubApi ;
    private RepoResult result;

    public GitHubSteps() {
        gitHubApi = new GitHubApi();
    }


    @Given("^I call the GitHub REST API for organization \"([^\"]*)\"$")
    public void iCallTheGitHubRestApiForOrganization(String org) {
        result = gitHubApi.analyzeOrgRepos(org);
    }
    @When("I calculate the total number of open issues across all repositories")
    public void iCalculateTheTotalNumberOfOpenIssuesAcrossAllRepositories() {
        // Không cần code thêm, chỉ để khớp với câu chữ trong feature file
    }

    // Step 3: "Determine" repo có nhiều sao nhất – cũng đã làm trong analyzeOrgRepos
    @When("I determine the highest-rated repository by the highest number of stars")
    public void iDetermineTheHighestRatedRepositoryByTheHighestNumberOfStars() {
        // Không cần code thêm, logic đã được tính sẵn
    }

    // Step 4: In ra và kiểm tra kết quả phân tích
    @Then("I print the GitHub analysis result")
    public void iPrintTheGitHubAnalysisResult() {
        Assert.assertNotNull("Analysis result should not be null", result);
        
        // In output đẹp theo yêu cầu đề bài
        System.out.println("\n========== GITHUB ANALYSIS RESULT ==========");
        System.out.println("Organization: " + result.org);
        System.out.println("Total repositories: " + result.totalRepos);
        System.out.println("Total open issues across all repositories: " + result.totalOpenIssues);
        System.out.println("Highest-rated repository: " + result.bestRepoName);
        System.out.println("Stars: " + result.bestRepoStars);
        System.out.println("============================================\n");

        Assert.assertTrue("Org should have repos", result.totalRepos > 0);
        Assert.assertTrue("Total open issues should be >= 0", result.totalOpenIssues >= 0);
        Assert.assertNotNull("Best repo name should not be null", result.bestRepoName);
        Assert.assertFalse("Best repo name should not be blank", result.bestRepoName.trim().isEmpty());
        Assert.assertTrue("Stars should be >= 0", result.bestRepoStars >= 0);
    }
}
