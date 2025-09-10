Feature: Greeting feature
  Scenario: User enters a name
    Given I have a greeting service
    When I greet "Praveen"
    Then the message should be "Hello, Praveen!"