package stepDefinitions;

import org.junit.Assert;

import API.GitHubApi;
import API.RepoResult;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class GitHubSteps {

    GitHubApi gitHubApi;
    RepoResult result;

    public GitHubSteps() {
        gitHubApi = new GitHubApi();
    }

    @Given("^I analyze GitHub repos in org \"([^\"]*)\"$")
    public void iAnalyzeGithubReposInOrg(String org) {
        result = gitHubApi.analyzeOrgRepos(org);
    }

    @Then("I print analysis result")
    public void iPrintAnalysisResult() {
        System.out.println(result);
        Assert.assertTrue("Org should have repos", result.totalRepos > 0);
        Assert.assertTrue("Total open issues should be >= 0", result.totalOpenIssues >= 0);
        Assert.assertNotNull("Best repo name should not be null", result.bestRepoName);
        Assert.assertFalse("Best repo name should not be blank", result.bestRepoName.isBlank());
        Assert.assertTrue("Stars should be >= 0", result.bestRepoStars >= 0);
    }
}
