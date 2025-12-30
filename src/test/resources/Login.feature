@Login
Feature: Login to the application



@LoginSetup
  Scenario: Successful login
    Given Open website url
    When I login with valid credentials
    And I click on the "Admin" item on sidebar menu
    And I click on the " Add " button
    And I fill in the add user form with valid data
    And I click on the Save button
    Then Verify new user is created successfully
    And I search for the new user by username
    Then Verify new user is displayed in search results

    

@api
  Scenario: Total open issues and top starred repo
    Given org is "SeleniumHQ"
    When I analyze org repos via GitHub API
    Then total open issues should be >= 0
    And I can find the repo with highest stars
    And issues-only open count should be <= open_issues_count sum
