package stepDefinitions;

import java.util.List;
import java.util.Map;

import org.junit.Assert;

import API.GitHubApi;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GitHubSteps {

    GitHubApi gitHubApi;
    String orgName;
    List<Map<String, Object>> allRepos;
    Map<String, Object> topRepo;
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
        System.out
                .println("Found top repo: " + topRepo.get("name") + " (" + topRepo.get("stargazers_count") + " stars)");
    }

    @Then("I print the GitHub analysis result")
    public void iPrintTheGitHubAnalysisResult() {
        Assert.assertNotNull("Repos list should not be null", allRepos);
        Assert.assertNotNull("Top repo should not be null", topRepo);

        System.out.println("\n========== GITHUB ANALYSIS RESULT ==========");
        System.out.println("Organization: " + orgName);
        System.out.println("Total repositories: " + allRepos.size());
        System.out.println("Total open issues across all repositories: " + totalOpenIssues);
        System.out.println("Highest-rated repository: " + topRepo.get("name"));
        System.out.println("Stars: " + topRepo.get("stargazers_count"));
        System.out.println("============================================\n");

        Assert.assertTrue("Org should have repos", allRepos.size() > 0);
        Assert.assertTrue("Total open issues should be >= 0", totalOpenIssues >= 0);
        Assert.assertNotNull("Top repo name should not be null", topRepo.get("name"));
        Assert.assertTrue("Top repo stars should be >= 0", (Integer) topRepo.get("stargazers_count") >= 0);
    }
}
