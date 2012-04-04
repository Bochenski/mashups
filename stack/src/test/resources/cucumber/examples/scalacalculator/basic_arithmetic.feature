@foo
Feature: Basic Arithmetic
  Scenario: Adding
    # Try to change one of the values below to provoke a failure
    When I add 4 and 5
    Then the result is 9
  Scenario: Subracting
  	When I subtract 4 from 5
  	Then the result is 2
