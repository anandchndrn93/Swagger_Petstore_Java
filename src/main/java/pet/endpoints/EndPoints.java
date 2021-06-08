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
