
package pet.helper;

import static pet.helper.Utilities.getBundle;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.io.IoBuilder;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * This class is meant to handle all actions that might be common to making api
 * calls. Actual API call is made from this class.
 * 
 * @author Anand Chandran
 */
public class RequestWrapper {
	/**
	 * Logger object to add logs
	 */
	private static Logger log = LogManager.getLogger(RequestWrapper.class.getName());

	/**
	 * Method to building specifications such as base url that are common to all api
	 * requests.Logging the request specification to log file is also handled here
	 * 
	 * @param headers map containing headers to be passed for an api
	 * @return request specifications
	 */
	public RequestSpecification requestSpecification(Map<String, String> headers) {

		if (headers == null)
			headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		headers.put("content-type", "application/json");
		PrintStream logStream = IoBuilder.forLogger(log).buildPrintStream();
		RestAssuredConfig restAssuredConfig = new RestAssuredConfig();
		LogConfig logConfig = restAssuredConfig.getLogConfig();

		logConfig.defaultStream(logStream).enablePrettyPrinting(true);
		RequestSpecification specification = new RequestSpecBuilder().setBaseUri(getBundle().get("BASE_URI"))
				.setBasePath(getBundle().get("BASE_PATH")).addFilter(RequestLoggingFilter.logRequestTo(logStream))
				.setConfig(restAssuredConfig).addHeaders(headers).build();
		/*
		 * BASE_URI and BASE_PATH are read from
		 * "/src/main/resources/config/config.properties" file
		 */
		return specification;
	}

	/**
	 * Method to make a get api call with query params
	 * 
	 * @param endpoint    api endpoint
	 * @param headers     map of api headers
	 * @param queryParams map of query params
	 * @return api response
	 */
	public Response getRequestWithQueryParams(String endpoint, Map<String, String> headers,
			Map<String, String> queryParams) {
		log.info("Performing GET on end point : " + endpoint);
		Response resp = RestAssured.given().spec(requestSpecification(headers)).queryParams(queryParams).when()
				.get(endpoint);
		log.debug("Response: " + resp.asString());
		return resp;

	}

	// TODO add POST, PUT, DELETE and all other necessary methods here
}
