@api @all @requiresKey
Feature: Reqres API checks
  Background:
    Given the API base uri is "https://reqres.in"

  Scenario: List users should return page 2 with data
    When I GET "/api/users?page=2"
    Then the response code should be 200
    And the response should have json path "page" equal to 2
    And the response json path "data" should be an array with size >= 1

  @requiresWrite
  Scenario: Create user returns 201 with id
    When I POST "/api/users" with json body:
      """
      {"name":"morpheus","job":"leader"}
      """
    Then the response code should be 201
    And the response should have json path "id" not null

  @requiresWrite
  Scenario: Update user returns 200
    When I PUT "/api/users/2" with json body:
      """
      {"name":"neo","job":"the one"}
      """
    Then the response code should be 200

  @requiresWrite
  Scenario: Delete user returns 204
    When I DELETE "/api/users/2"
    Then the response code should be 204
