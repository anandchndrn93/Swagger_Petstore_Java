package pet.helper;

import static pet.helper.Utilities.getBundle;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class RequestWrapper {
	public static Logger log = LogManager.getLogger(RequestWrapper.class.getName());
	public static RequestSpecification spec;

	public RequestSpecification requestSpecification(Map<String, String> headers) {

// method to building specifications such as base url that are common to all api requests.
//logging the request specification to log file is also handled here
		if (headers == null)
			headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		headers.put("content-type", "application/json");
		PrintStream logStream = IoBuilder.forLogger(log).buildPrintStream();
		RestAssuredConfig restAssuredConfig = new RestAssuredConfig();
		LogConfig logConfig = restAssuredConfig.getLogConfig();

		logConfig.defaultStream(logStream).enablePrettyPrinting(true);
		spec = new RequestSpecBuilder().setBaseUri(getBundle().get("BASE_URI"))
				.setBasePath(getBundle().get("BASE_PATH")).addFilter(RequestLoggingFilter.logRequestTo(logStream))
				.setConfig(restAssuredConfig).addHeaders(headers).build();
		return spec;
	}

//	String resolveKeys(String key) {
////method to replace values in payloads or endpoints based on regex. 
////Any value between "%%" and "%%" will be considered as key and will be replaced with corresponding value from getBundle Map
//		String patternString1 = "(%%)(.+?)(%%)";
//		Pattern pattern = Pattern.compile(patternString1);
//		Matcher matcher = pattern.matcher(key);
//		while (matcher.find()) {
//			String foundString = matcher.group(1) + matcher.group(2) + matcher.group(3);
//			String replaceWith = getBundle().getOrDefault(matcher.group(2), foundString);
//			key = key.replaceAll(foundString, Matcher.quoteReplacement(replaceWith));
//		}
//		return key;
//	}

	public Response getRequestWithQueryParams(String endpoint, Map<String, String> headers,
			Map<String, String> formParams) {
// get request with query params is sent here. This method can be called from module class
//		endpoint = resolveKeys(endpoint);
		log.info("Performing GET on end point : " + endpoint);
		Response resp = RestAssured.given().spec(requestSpecification(headers)).queryParams(formParams).when()
				.get(endpoint);
		log.debug("Response: " + resp.asString());
		return resp;

	}

//TODO add  POST, PUT, DELETE and all other necessary methods here
}
