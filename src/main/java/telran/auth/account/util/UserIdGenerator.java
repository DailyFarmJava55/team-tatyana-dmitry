package telran.auth.account.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class UserIdGenerator {

	public static String generateId(String email, String password) {
		return email + "_" + BCrypt.hashpw(password, BCrypt.gensalt(10));
	}
}
