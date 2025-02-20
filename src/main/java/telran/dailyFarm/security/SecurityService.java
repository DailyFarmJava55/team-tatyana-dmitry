package telran.dailyFarm.security;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.dailyFarm.accounting.dao.UserRepository;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final UserRepository userRepository;
    

    

    public boolean isUserOwner(Long userId, String username) {
        return userRepository.findById(userId)
                .map(user -> user.getUsername().equals(username))
                .orElse(false);
    }

    public boolean isProductOwner(Long productId, String username) {
		//TODO: implement this method
    	return true;
   }

}
