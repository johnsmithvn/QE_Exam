package stepDefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.*;

import org.openqa.selenium.WebDriver;

import API.GitHubApi;

public class GitHubSteps {
    WebDriver driver;
    GitHubApi githubApi;
    private String org;  
    private GitHubApi.AnalysisResult result; 

    public GitHubSteps() {
        githubApi = new GitHubApi();
    }

    @Given("^org is \"([^\"]*)\"$")
    public void org_is(String org) {
        this.org = org;
    }

    @When("I analyze org repos via GitHub API")
    public void i_analyze_org_repos() {
        result = githubApi.analyzeOrg(org);  
        assertNotNull(result);
        assertTrue("Repo list should not be empty", result.repoCount > 0);
    }

    @Then("total open issues should be >= 0")
    public void total_open_issues_should_be_ge_0() {
        assertTrue(result.sumOpenIssuesCount >= 0);
    }

    @Then("I can find the repo with highest stars")
    public void i_can_find_the_repo_with_highest_stars() {
        assertNotNull(result.topStarRepoFullName);
        assertTrue(result.topStars >= 0);
        System.out.println("Top repo: " + result.topStarRepoFullName + " (" + result.topStars + "⭐)");
    }

    @Then("issues-only open count should be <= open_issues_count sum")
    public void issues_only_open_count_should_be_le_sum() {
        assertTrue(result.sumOpenIssuesCount >= result.issuesOnlyOpenCount);
        System.out.println("sum open_issues_count: " + result.sumOpenIssuesCount);
        System.out.println("issues-only open count: " + result.issuesOnlyOpenCount);
    }
}