package AutomationTest.mission.steps;

import AutomationTest.mission.Hook;
import io.cucumber.java.en.*;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class ApiSteps {

    private Response resp;

    // Strip leading "/api" because Hook.apiSpec sets basePath("/api")
    private String normalize(String path) {
        if (path == null || path.isEmpty()) return path;
        if (path.startsWith("/api/")) return path.substring(4);
        if ("/api".equals(path)) return "/";
        return path;
    }

    @Given("the API base uri is {string}")
    public void the_api_base_uri_is(String uri) {
        System.out.println("[ApiSteps] Using Hook.apiSpec.");
    }

    @When("I GET {string}")
    public void i_get(String path) {
        resp = given().spec(Hook.apiSpec).auth().none()
                .when().get(normalize(path))
                .then().extract().response();
    }

    @When("I POST {string} with json body:")
    public void i_post_with_json_body(String path, String body) {
        resp = given().spec(Hook.apiSpec).auth().none()
                .body(body)
                .when().post(normalize(path))
                .then().extract().response();
    }

    @When("I PUT {string} with json body:")
    public void i_put_with_json_body(String path, String body) {
        resp = given().spec(Hook.apiSpec).auth().none()
                .body(body)
                .when().put(normalize(path))
                .then().extract().response();
    }

    @When("I DELETE {string}")
    public void i_delete(String path) {
        resp = given().spec(Hook.apiSpec).auth().none()
                .when().delete(normalize(path))
                .then().extract().response();
    }

    @Then("the response code should be {int}")
    public void the_response_code_should_be(Integer code) {
        assertThat(resp.statusCode())
                .as("Expected HTTP %s but got %s. Body: %s", code, resp.statusCode(), resp.asString())
                .isEqualTo(code);
    }

    @Then("the response should have json path {string} equal to {int}")
    public void the_response_should_have_json_path_equal_to(String path, Integer val) {
        assertThat(resp.jsonPath().getInt(path))
                .as("Expected %s == %s. Body: %s", path, val, resp.asString())
                .isEqualTo(val);
    }

    @Then("the response json path {string} should be an array with size >= {int}")
    public void the_response_json_path_should_be_array_with_size(String path, Integer min) {
        assertThat(resp.jsonPath().getList(path))
                .as("Expected %s size >= %s. Body: %s", path, min, resp.asString())
                .hasSizeGreaterThanOrEqualTo(min);
    }

    @Then("the response should have json path {string} not null")
    public void the_response_should_have_json_path_not_null(String path) {
        assertThat((Object) resp.jsonPath().get(path))
                .as("Expected %s not null. Body: %s", path, resp.asString())
                .isNotNull();
    }
}
