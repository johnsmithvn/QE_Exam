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

    


