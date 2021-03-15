package Apisampletest;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Apitest {

	@Test(enabled = true)
	public void getRequest() throws Exception {

		RestAssured.baseURI = "http://api.intigral-ott.net/popcorn-api-rs-7.9.10/v1/promotions?";

		RequestSpecification request = RestAssured.given().param("apikey", "GDMSTGExy0sVDlZMzNDdUyZ");

		Response response = request.request(Method.GET);
		// First Validation
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);

		ResponseBody jsonString = response.getBody();
		String bodyAsString = jsonString.asString();
		Assert.assertEquals(bodyAsString.contains("promotionId") /* Expected value */, true /* Actual Value */,
				"Response body contains promotionId");
		Assert.assertEquals(bodyAsString.contains("orderId") /* Expected value */, true /* Actual Value */,
				"Response body contains orderId");
		Assert.assertEquals(bodyAsString.contains("promoArea") /* Expected value */, true /* Actual Value */,
				"Response body contains promoArea");
		Assert.assertEquals(bodyAsString.contains("promoType") /* Expected value */, true /* Actual Value */,
				"Response body contains promoType");
		Assert.assertEquals(bodyAsString.contains("showPrice") /* Expected value */, true /* Actual Value */,
				"Response body contains showPrice");
		Assert.assertEquals(bodyAsString.contains("showText") /* Expected value */, true /* Actual Value */,
				"Response body contains showText");
		Assert.assertEquals(bodyAsString.contains("localizedText") /* Expected value */, true /* Actual Value */,
				"Response body contains localizedText");

	}

	@Test(enabled = true)
	public void validation3() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectMapper objectMapper = new ObjectMapper();
		RestAssured.baseURI = "http://api.intigral-ott.net/popcorn-api-rs-7.9.10/v1/promotions?";

		RequestSpecification request = RestAssured.given().param("apikey", "InvalidKEY");
		Response response = request.request(Method.GET);

		// Third Validation - 1st Point
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 403);

		try {

			Map<String, String> validationmap = new HashMap<>();
			JsonObject responseResultObject = new Gson().fromJson(objectMapper.writeValueAsString(
					mapper.readValue(response.getBody().asString(), Map.class).get("error")), JsonObject.class);
			validationmap.put("message", responseResultObject.get("message").getAsString());
			validationmap.put("code", responseResultObject.get("code").getAsString());
			validationmap.put("requestId", responseResultObject.get("requestId").getAsString());

			// Third Validation - 2nd Point
			Assert.assertEquals(validationmap.get("message"), "invalid api key",
					"Given Message is displaied as " + validationmap.get("message"));
			Assert.assertEquals(validationmap.get("code"), "8001",
					"Given Code is displaied as " + validationmap.get("code"));
			if (validationmap.get("requestId") != null) {
				System.out.println("Request ID is not null");
			}

		} catch (IOException e) {
			e.printStackTrace();

		}
	}

}
