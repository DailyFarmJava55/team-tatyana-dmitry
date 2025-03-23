package telran.dayli_farm.api.message;

public interface ErrorMessages {

	String PASSWORD_IS_NOT_VALID = "Password must be at least 8 characters long";
	String PASSWORD_IS_REQUIRED = "Password is required";
	String OLD_NEW_PASSWORD_REQUARED = "Old password and new password - requared field";
	String OLD_PASSWORD_IS_NOT_CORECT= "Old password is not correct";
	
	String EMAIL_IS_NOT_VALID = "Email is not valid";
	String NAME_IS_NOT_VALID = "Name is not valid";
	String LAST_NAME_IS_NOT_VALID = "Last name is not valid";
	String PHONE_NUMBER_IS_NOT_VALID = "Phone number is not valid";
	String PHONE_IS_REQUIRED = "Phone is required";

	String WRONG_USER_NAME_OR_PASSWORD = "Wrong user name or password";
	String USER_NOT_FOUND = "User is not found";
	String INVALID_TOKEN = "Invalid or expired JWT token";

	String FARMER_WITH_THIS_EMAIL_EXISTS = "Farmer with this email exists";
	String FARMER_WITH_THIS_EMAIL_IS_NOT_EXISTS = "Farmer with this email is not exists";
	String FARM_NAME_IS_REQUIRED = "Company name is required";
	String FARMER_NOT_FOUND = "Farmer not found";
	
	String CUSTOMER_WITH_THIS_EMAIL_EXISTS = "Customer with this email exists";
	String CUSTOMER_WITH_THIS_EMAIL_IS_NOT_EXISTS = "Customer with this email is not exists";
	
	String LONGITUDE_REQUIRED = "Longitude is required";
	String INVALID_LONGITUDE = "Longitude must be between -180 and 180 degrees";
	String LATITUDE_REQUIRED ="Latitude is required";
	String INVALID_LATITUDE = "Latitude must be between -90 and 90 degrees";
	
	

}
