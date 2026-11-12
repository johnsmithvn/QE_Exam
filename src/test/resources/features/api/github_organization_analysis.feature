# [REFACTOR] CŨ: tag viết hoa @API và tên Feature trùng với Exam.feature ("Exam Scenarios"),
# nên trong report hai feature không phân biệt được.
# @API
# Feature: Exam Scenarios
# [REFACTOR] MỚI: tag @api (đối xứng với @ui bên Exam.feature) và tên Feature riêng.
@api
Feature: Exam 02 - GitHub organization analysis (API)

@exam_02
  Scenario: Analyze SeleniumHQ GitHub organization
    Given I call the GitHub REST API for organization "SeleniumHQ"
    When I calculate the total number of open issues across all repositories
    And I determine the highest-rated repository by the highest number of stars
    Then I print the GitHub analysis result
    # [REFACTOR] MỚI: 2 bước kiểm tra có ý nghĩa, thay cho các assert ">= 0" cũ trong GitHubSteps.
    And the highest-rated repository should be "selenium"
    And the total number of repositories should match the organization public repository count
