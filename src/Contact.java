
public class Contact {
	private String firstName;
	private String lastName;
	private String phoneNumber;
	
	public Contact(String firstName, String lastName, String phoneNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
	}
	public String getFirst () {
		return this.firstName;
	}
	public String getLast () {
		return this.lastName;
	}
	public String getNumber() {
		return this.phoneNumber;
	}
}
