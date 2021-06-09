package com.example.FriendsRanking.test.repositories;

import static com.example.FriendsRanking.test.utils.UserCreator.createUser;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.FriendsRanking.entities.User;
import com.example.FriendsRanking.repositories.UserRepository;

@ActiveProfiles("test")
@DataJpaTest
class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	void findByEmail_ReturnsUser_WhenSuccessful() {
		User user = userRepository.save(createUser());
		Optional<User> userTest = userRepository.findByEmail(user.getEmail());
		
		assertThat(userTest).isNotEmpty().isNotNull();
		assertThat(userTest.get().getEmail()).isEqualTo(user.getEmail());
	}
	
	@Test
	void save_SaveAndReturnsUser_WhenSuccessful() {
		User user = userRepository.save(createUser());
		
		assertThat(user).isNotNull();
		assertThat(user.getName()).isEqualTo(createUser().getName());
	}
}
