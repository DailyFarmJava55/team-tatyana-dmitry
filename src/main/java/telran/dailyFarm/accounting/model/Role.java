package telran.dailyFarm.accounting.model;

public enum Role {
	USER, FARMER, ADMIN;
	
	public static Role fromString(String role) {
        try {
            return Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return USER; 
        }
    }
}
