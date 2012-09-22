Feature: manage books
  Many of us love books, but don't have enough time to read them all. Would be good to understand the size of the problem though.

  Scenario: add book
    When I add a book
    Then I see the book in the library