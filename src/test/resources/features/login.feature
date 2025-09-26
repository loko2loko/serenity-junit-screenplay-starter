Feature: User Login
  As a user
  I want to log into the application
  So that I can access my account

  @smoke
  Scenario: Successful login with valid credentials
    Given I am on the login page
    When I enter username "john.doe@example.com"
    And I enter password "password123"
    And I click the login button
    Then I should be redirected to the dashboard
    And I should see welcome message "Welcome, John"

  @negative
  Scenario: Failed login with invalid credentials
    Given I am on the login page
    When I enter username "invalid@example.com"
    And I enter password "wrongpassword"
    And I click the login button
    Then I should see error message "Invalid credentials"
    And I should remain on the login page

  Scenario Outline: Login with different user types
    Given I am on the login page
    When I enter username "<username>"
    And I enter password "<password>"
    And I click the login button
    Then I should see "<result>"

    Examples:
      | username           | password    | result                    |
      | admin@example.com  | admin123    | Welcome, Administrator    |
      | user@example.com   | user123     | Welcome, User            |
      | guest@example.com  | guest123    | Welcome, Guest           |
