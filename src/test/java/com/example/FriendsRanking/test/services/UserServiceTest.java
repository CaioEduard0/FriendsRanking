package com.example.FriendsRanking.test.services;

import static com.example.FriendsRanking.test.utils.UserCreator.createUser;
import static com.example.FriendsRanking.test.utils.UserCreator.createUserAdmin;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.FriendsRanking.entities.User;
import com.example.FriendsRanking.repositories.UserRepository;
import com.example.FriendsRanking.services.UserService;
import com.example.FriendsRanking.services.exceptions.RepeatedEmailException;;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class UserServiceTest {
	
	@InjectMocks
	private UserService userService; 
	
	@Mock
	private UserRepository userRepositoryMock;
	
	@BeforeEach
	void setUp() {
		
		when(userRepositoryMock.findByEmail(anyString())).thenReturn(Optional.of(createUser()));
		
		when(userRepositoryMock.save(any(User.class))).thenReturn(createUser());
	}
	
	@Test
	void findUserByEmail_ReturnsUser_WhenSuccessful() {
		User user = userService.findUserByEmail("xxx");
		
		assertThat(user).isNotNull().isInstanceOf(User.class);
		assertThat(user.getEmail()).isEqualTo(createUser().getEmail());
	}
	
	@Test
	void findUserByEmail_ThrowsUsernameNotFoundException_WhenUserIsNotFound() {
		when(userRepositoryMock.findByEmail(anyString())).thenReturn(Optional.empty());
		
		assertThatExceptionOfType(UsernameNotFoundException.class).isThrownBy(() -> userService.findUserByEmail("xxx"));
	}
	
	@Test
	void insertUser_SavesUser_WhenSuccessful() {
		when(userRepositoryMock.save(any(User.class))).thenReturn(createUserAdmin());
		when(userRepositoryMock.findByEmail(anyString())).thenReturn(Optional.empty());
		
		ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
		userService.insertUser(createUserAdmin());
		verify(userRepositoryMock).save(argumentCaptor.capture());
		
		assertThat(argumentCaptor.getValue().getName()).isEqualTo(createUserAdmin().getName());
		assertThat(argumentCaptor.getValue().getPassword()).isEqualTo(createUserAdmin().getPassword());
		assertThatCode(() -> userService.insertUser(createUserAdmin())).doesNotThrowAnyException();
		assertThatCode(() -> userService.insertUser(createUserAdmin())).isNull();
	}
	
	@Test
	void insertUser_ThrowsRepeatedEmailException_WhenEmailIsAlreadyRegistered() {
		assertThatExceptionOfType(RepeatedEmailException.class).isThrownBy(() -> userService.insertUser(createUser()));
	}
}
