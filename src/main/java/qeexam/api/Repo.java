package qeexam.api;

// [REFACTOR] ĐỔI PACKAGE: API -> qeexam.api (tên package Java viết thường toàn bộ, nằm dưới package gốc qeexam).

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * [REFACTOR] MỚI - POJO cho một repository trả về từ GitHub API.
 *
 * Tại sao: trước đây mỗi repo là Map<String, Object>, phải nhớ tên key dạng chuỗi ("stargazers_count")
 * và ép kiểu thủ công bằng toInt(). Gõ sai key thì chỉ nhận về null lúc chạy. Với POJO, compiler
 * kiểm tra tên trường và kiểu dữ liệu.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Repo {

    private String name;

    @JsonProperty("stargazers_count")
    private int stargazersCount;

    @JsonProperty("open_issues_count")
    private int openIssuesCount;

    public String getName() {
        return name;
    }

    public int getStargazersCount() {
        return stargazersCount;
    }

    public int getOpenIssuesCount() {
        return openIssuesCount;
    }
}
