public enum PermitStatus {
	APPROVED ("Approved"),
	EXPIRED ("Expired"),
	ISSUED ("Issued"),
	REQUESTED ("Requested"),
	SUSPEND ("Suspended");

	private String permitStatus;

	private PermitStatus (String permitStatus) {
		this.permitStatus = permitStatus;
	}

	@Override
	public String toString() {
		return permitStatus;
	}
}
