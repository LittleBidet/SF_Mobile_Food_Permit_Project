import java.util.*;

public class FoodPermit implements Comparable<FoodPermit>{
	private String name;
	private String address;
	private String foodType;
	//((no need to make private as the data is from the SF Country Food Permits)
	public final String CITY_STATE = "San Francisco, California";
	private PermitStatus permitStatus;
	private FacilityType facilityType;

	public FoodPermit(String name, String address, String foodType, PermitStatus permitStatus, FacilityType facilityType) {
		this.name = name;
		this.address = address;
		this.foodType = foodType;
		this.permitStatus = permitStatus;
		this.facilityType = facilityType;
	}
	// getters and setters (no need for CITY_STATE getter)
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}
	public String getFoodType() {
		return foodType;
	}
	public PermitStatus getPermitStatus() {
		return permitStatus;
	}
	public FacilityType getFacilityType() {
		return facilityType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FoodPermit that = (FoodPermit) o;
		return Objects.equals(name, that.name) && Objects.equals(address, that.address) && Objects.equals(foodType, that.foodType) && Objects.equals(permitStatus, that.permitStatus) && Objects.equals(facilityType, that.facilityType);
	}
	public int compareTo(FoodPermit foodPermit)
	{
		return this.name.toLowerCase().compareTo(foodPermit.name.toLowerCase());
	}

	@Override
	public String toString() {
		return "Name: " + name + "\n" + "Address: " + address + "," + CITY_STATE +"\n" +
				"Permit Status: " + permitStatus + "\n" + "Type of Facility: " + facilityType + "\n" + "Type of Foods Served: " + foodType;
	}
}
