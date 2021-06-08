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

public class FindPetsTest {

	PetModule pet;
	private static Logger log = LogManager.getLogger(FindPetsTest.class.getName());

// method to run before a test
	@BeforeTest(alwaysRun = true)
	public void testSetup() {
		pet = new PetModule();
	}

	@Test(dataProvider = "statusProvider")
	public void findPets(String strStatus) {
// get all pets based on different status
		log.info("fetching all pets in pending status");
		Listners.getReporter().log(Status.INFO, "fetching all pets in pending status");
		Response petResponse = pet.getPetsByStatus(strStatus); // This is the api call

//Validating if the response code is 200
		int intStatusCode = petResponse.getStatusCode();
		Assert.assertEquals(intStatusCode, 200, " Status code mismatch");
		log.debug("status code returned is :" + intStatusCode);
		Listners.getReporter().log(Status.PASS, "status code returned is :" + intStatusCode);

// validating if the response time is less than 200 ms
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

// iterating through all pets and printing the details if the pet id Lion. All pet names are fetched as a list. 
//Then iterate through the list to find matching pet.
//Based on the index of matching pet from the list pick other details from api response
		List<String> listPetNames = petResponse.jsonPath().getList("category.name");
		int intPetCount = 0;
		int intPetMatchCount = 0;
		for (String petname : listPetNames) {
			if (petname.equals("Lions")) {
				String strCategoryId = petResponse.path("category[" + intPetCount + "].id");
				String strId = petResponse.path("id[" + intPetCount + "]");
				String strName = petResponse.path("name[" + intPetCount + "]");
				log.info("pet found: " + petname);
				log.info("pet category id is: " + strCategoryId);
				log.info("pet id is: " + strId);
				log.info("pet name is: " + strName);
				Listners.getReporter().log(Status.PASS, "pet Lion was found with name" + strName);
				intPetMatchCount++;
			}
			intPetCount++;
		}

		if (intPetMatchCount == 0) {
			log.fatal("The pet name Lion was not found");
			Listners.getReporter().log(Status.FAIL, "The pet name Lion was not found");
		}

//validating the header "content-type"
		String strHeader = petResponse.getHeader("content-type");
		Assert.assertEquals(strHeader, "application/json", " Header mismatch");
		log.debug("content-type header is :" + strHeader);
		Listners.getReporter().log(Status.PASS, "content-type header is :" + strHeader);

//validating the response schema. Sample schema is kept in /src/main/resources/findByStatus.json
		petResponse.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("findByStatus.json"));
		log.debug("The response Json Schema is valid");
		Listners.getReporter().log(Status.PASS, "The response Json Schema is valid");
	}

//data provider method to provide different status to be queried
	@DataProvider
	public String[] statusProvider() {

		String[] testObjArray = new String[3];
		testObjArray[0] = "available";
		testObjArray[1] = "pending";
		testObjArray[2] = "sold";
		return testObjArray;
	}
}
