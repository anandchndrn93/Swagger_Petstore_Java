
package pet.tests;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.aventstack.extentreports.Status;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import pet.helper.Listners;
import pet.modules.PetModule;

/**
 * This is a test class. All validations on api response can be done here.
 * 
 * @author Anand Chandran
 */
public class FindPetsTest {

	private PetModule pet;
	/**
	 * Logger object to add logs
	 */
	private static Logger log = LogManager.getLogger(FindPetsTest.class.getName());

	/**
	 * Method to run before a test. initialize instance of PetModule class
	 */
	@BeforeTest(alwaysRun = true)
	public void testSetup() {
		pet = new PetModule();
	}

	/**
	 * Test Method for findByStatus API.
	 * 
	 * @param strStatus status of pet to be passed as query param to fidByStatus API
	 */
	@Test(dataProvider = "statusProvider")
	public void findPets(String strStatus) {
		/*
		 * Get all pets based on different status
		 */
		log.info("fetching all pets in " + strStatus + " status");
		Listners.getReporter().log(Status.INFO, "fetching all pets in pending status");
		Response petResponse = pet.getPetsByStatus(strStatus); // This is the api call

		/*
		 * Validating if the response code is 200
		 */
		int intStatusCode = petResponse.getStatusCode();
		Assert.assertEquals(intStatusCode, 200, " Status code mismatch");
		log.debug("status code returned is :" + intStatusCode);
		Listners.getReporter().log(Status.PASS, "status code returned is :" + intStatusCode);

		/*
		 * Validating if the response time is less than 200 ms
		 */
		long longResponseTime = petResponse.getTime();
		try {
			Assert.assertTrue(longResponseTime < 200, "response took more time to complete");
			log.debug("response time was :" + longResponseTime);
			Listners.getReporter().log(Status.PASS, "response time was :" + longResponseTime);
		} catch (AssertionError ae) {
			log.fatal("Response time was more than expected. Response time was :" + longResponseTime);
			Listners.getReporter().log(Status.FAIL,
					"Response time was more than expected. Response time in milliseconds was :" + longResponseTime);
		}

		/**
		 * Iterating through all pets and printing the details if the pet id Lion. All
		 * pet names are fetched as a list. Then iterate through the list to find
		 * matching pet. Based on the index of matching pet from the list pick other
		 * details from api response
		 */
		List<String> listPetNames = petResponse.jsonPath().getList("category.name");
		log.info("a total of " + listPetNames.size() + " records found for pets in status " + strStatus);
		int intPetCount = 0;
		int intPetMatchCount = 0;
		for (String petname : listPetNames) {
			if (petname.equals("Lions")) {
				log.info(intPetCount);
				int intCategoryId = petResponse.path("category[" + intPetCount + "].id");
				int intId = petResponse.path("id[" + intPetCount + "]");
				String strName = petResponse.path("name[" + intPetCount + "]");
				log.info("pet found: " + petname);
				log.info("pet category id is: " + intCategoryId);
				log.info("pet id is: " + intId);
				log.info("pet name is: " + strName);
				Listners.getReporter().log(Status.INFO, "pet Lion was found with name " + strName);
				intPetMatchCount++;
			}
			intPetCount++;
		}

		if (intPetMatchCount == 0) {
			log.info("The pet name Lion was not found for status " + strStatus);
			Listners.getReporter().log(Status.INFO, "The pet name Lion was not found for status " + strStatus);
		} else {
			log.info("a total of " + intPetMatchCount + " records found for Lions in status " + strStatus);
			Listners.getReporter().log(Status.INFO,
					"a total of " + intPetMatchCount + " records found for Lions in status " + strStatus);
		}

		/*
		 * Validating the header "content-type"
		 */
		String strHeader = petResponse.getHeader("content-type");
		Assert.assertEquals(strHeader, "application/json", " Header mismatch");
		log.debug("content-type header is :" + strHeader);
		Listners.getReporter().log(Status.PASS, "content-type header is :" + strHeader);

		/*
		 * validating the response schema. Sample schema is kept in
		 * "/src/main/resources/findByStatus.json"
		 */
		petResponse.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("findByStatus.json"));
		log.debug("The response Json Schema is valid");
		Listners.getReporter().log(Status.PASS, "The response Json Schema is valid");

	}

	/**
	 * Data provider method to provide different status to be queried
	 * 
	 * @return String array with all the valid status to be passed to findByStatus
	 *         API as query param
	 */
	@DataProvider
	public String[] statusProvider() {

		String[] testStatusArray = new String[3];
		testStatusArray[0] = "available";
		testStatusArray[1] = "pending";
		testStatusArray[2] = "sold";
		return testStatusArray;
	}
}
