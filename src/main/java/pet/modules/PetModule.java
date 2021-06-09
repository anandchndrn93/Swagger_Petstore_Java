/**
* @description: This class is meant to put together all data for making a specific api call. Each method would refer to a specific api. 
*               To make an api call the method can be accessed from test classes using instance of this class.
* @author:-Anand Chandran
*/
package pet.modules;

import java.util.HashMap;
import java.util.Map;

import io.restassured.response.Response;
import pet.endpoints.EndPoints;
import pet.helper.RequestWrapper;

public class PetModule {
	RequestWrapper req;
	EndPoints endpoint;

	public PetModule() {
		req = new RequestWrapper();
	}

	public Response getPetsByStatus(String status) {
//Find By Status api is formulated here.
		endpoint = EndPoints.valueOf("FindPetByStatus");
		Map<String, String> petStatus = new HashMap<>();
		petStatus.put("status", status);
		return req.getRequestWithQueryParams(endpoint.getResource(), null, petStatus);
	}

//TODO other methods for updating creating or deleting records can be added here. 
//All request can make a call to corresponding methods in Requestwrapper class based on the request type
}
