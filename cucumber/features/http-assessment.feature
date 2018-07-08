Feature: Make a number of HTTP requests

  Scenario: a list of valid urls
    Given the url "http://www.bbc.co.uk/iplayer" is returning 200 statuses with headers:
      | Content-Length | 1234                          |
      | Date           | Thu, 05 Jul 2018 21:58:20 GMT |
    And the url "https://google.com" is returning 304 statuses with headers:
      | Content-Length | 262                           |
      | Date           | Thu, 05 Jul 2018 21:58:20 GMT |
    When I run the command with the following urls
      | http://www.bbc.co.uk/iplayer |
      | https://google.com           |
    Then I should get a result containing 2 items
    And the result should include
      | Url            | http://www.bbc.co.uk/iplayer  |
      | Status_code    | 200                           |
      | Content_length | 1234                          |
      | Date           | Thu, 05 Jul 2018 21:58:20 GMT |
    And the result should include
      | Url            | https://google.com            |
      | Status_code    | 304                           |
      | Content_length | 262                           |
      | Date           | Thu, 05 Jul 2018 21:58:20 GMT |

  Scenario: a list with invalid urls
    When I run the command with the following urls
      | bad://address |
    Then I should get a result containing 1 item
    And the result should include
      | Url   | bad://address |
      | Error | invalid url   |
