package qeexam.api;

// [REFACTOR] ĐỔI PACKAGE: API -> qeexam.api (tên package Java viết thường toàn bộ, nằm dưới package gốc qeexam).

public class RepoResult {
    public final String org;
    public final int totalRepos;
    public final int totalOpenIssues;
    public final String bestRepoName;
    public final int bestRepoStars;

    public RepoResult(String org, int totalRepos, int totalOpenIssues, String bestRepoName, int bestRepoStars) {
        this.org = org;
        this.totalRepos = totalRepos;
        this.totalOpenIssues = totalOpenIssues;
        this.bestRepoName = bestRepoName;
        this.bestRepoStars = bestRepoStars;
    }

    @Override
    public String toString() {
        return "Org=" + org
                + " | totalRepos=" + totalRepos
                + " | totalOpenIssues=" + totalOpenIssues
                + " | topRepo=" + bestRepoName
                + " | stars=" + bestRepoStars;
    }
}
