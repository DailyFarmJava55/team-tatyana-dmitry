package telran.accounting.model;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserAccountId implements Serializable {
	private static final long serialVersionUID = -935002833744670082L;
	 String email;
     String password;

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserAccountId that = (UserAccountId) obj;
        return Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }
}