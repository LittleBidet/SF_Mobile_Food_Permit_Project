public enum FacilityType {
	TRUCK ("Truck"), PUSH_CART ("Push Cart");

	private String facilityType;

	private FacilityType (String facilityType) {
		this.facilityType = facilityType;
	}

	@Override
	public String toString() {
		return facilityType;
	}
}
