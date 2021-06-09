/**
* @description:-This is an enum class to maintain all the endpoints needed for different apis.
* @author:-Anand Chandran
*/
package pet.endpoints;

public enum EndPoints {
// All end points to be listed here
	FindPetByStatus("pet/findByStatus");

	private String endpoint;

	EndPoints(String endpoint) {
//constructor
		this.endpoint = endpoint;
	}

	public String getResource() {
// return the endpoint
		return endpoint;
	}

}
