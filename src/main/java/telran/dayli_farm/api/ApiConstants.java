package telran.dayli_farm.api;

public interface ApiConstants {

	//FARMER
	String FARMER_REGISTER = "/farmer/register";
	String FARMER_LOGIN = "/farmer/login";
	String FARMER_LOGOUT = "/farmer/logout";
	String FARMER_CURRENT = "/farmer/profile";
	
	String FARMER_EDIT = "/farmer/";
	String FARMER_CHANGE_PASSWORD = "/farmer/password";
	String FARMER_CHANGE_EMAIL = "/farmer/email";
	String FARMER_CHANGE_COMPANY_NAME = "/farmer/company";
	String FARMER_CHANGE_ADDRESS = "/farmes/address";
	String FARMER_CHANGE_COORDINATES = "/farmer/coordinates";
	String FARMER_CHANGE_PHONE = "/farmer/phone";
	
	String FARMER_REMOVE = "/farmer/";
	String FARMER_REFRESH_TOKEN = "/farmer/refresh";
	String RESET_PASSWORD = "/farmer/password-reset";
	
	
	//CUSTOMER
	
	String CUSTOMER_REGISTER = "/customer/register";
	String CUSTOMER_LOGIN = "/customer/login";
	String CUSTOMER_LOGOUT = "/customer/logout";
	String CUSTOMER_CURRENT = "/customer/profile";
	
	String CUSTOMER_EDIT = "/customer/";
	String CUSTOMER_CHANGE_PASSWORD = "/customer/password";
	String CUSTOMER_CHANGE_EMAIL = "/customer/email";
	String CUSTOMER_CHANGE_FIRST_LAST_NAME = "/customer/name";
	String CUSTOMER_CHANGE_PHONE = "/customer/phone";
	
	String CUSTOMER_REMOVE = "/customer/remove";
	String CUSTOMER_REFRESH_TOKEN = "/customer/refresh";
	String CUSTOMER_RESET_PASSWORD = "/customer/password-reset";
	
}
