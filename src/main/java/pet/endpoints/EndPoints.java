
package pet.endpoints;

/**
 * This is an enum class to maintain all the endpoints needed for different
 * apis.
 * 
 * @author Anand Chandran
 */
public enum EndPoints {

	/**
	 * findByStatus api endpoint
	 */
	FindPetByStatus("pet/findByStatus");

	// TODO All end points to be listed here

	private String endpoint;

	/**
	 * constructor.
	 * 
	 * @param endpoint The api endpoint
	 */
	EndPoints(String endpoint) {
		this.endpoint = endpoint;
	}

	/**
	 * Method can be used to get the endpoint of an api.
	 * 
	 * @return Returns the api endpoint.
	 */
	public String getResource() {
		return endpoint;
	}

}
