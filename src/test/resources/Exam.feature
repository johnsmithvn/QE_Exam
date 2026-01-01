@exam
Feature: Login to the application



@exam_01
  Scenario: Add new user successfully and search for the user
    Given Open website url
    When I login with valid credentials
    And I click on the "Admin" item on sidebar menu
    And I click on the " Add " button
    And I fill in the add user form with valid data
    And I click on the Save button
    Then Verify new user is created successfully
    And I search for the new user by username
    And Verify new user is displayed in search results

    

@exam_02
  Scenario: Analyze SeleniumHQ GitHub organization
    Given I call the GitHub REST API for organization "SeleniumHQ"
    When I calculate the total number of open issues across all repositories
    And I determine the highest-rated repository by the highest number of stars
    Then I print the GitHub analysis result
   
