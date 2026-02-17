@API
Feature: Exam Scenarios

@exam_02
  Scenario: Analyze SeleniumHQ GitHub organization
    Given I call the GitHub REST API for organization "SeleniumHQ"
    When I calculate the total number of open issues across all repositories
    And I determine the highest-rated repository by the highest number of stars
    Then I print the GitHub analysis result
   
