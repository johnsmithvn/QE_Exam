@exam
Feature: Exam Scenarios



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
  Scenario: Login success for herokuo app