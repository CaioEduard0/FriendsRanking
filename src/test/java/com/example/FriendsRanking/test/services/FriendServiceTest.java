package com.example.FriendsRanking.test.services;

import static com.example.FriendsRanking.test.utils.FriendCreator.createFriend;
import static com.example.FriendsRanking.test.utils.UserCreator.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.FriendsRanking.entities.Friend;
import com.example.FriendsRanking.repositories.FriendRepository;
import com.example.FriendsRanking.repositories.UserRepository;
import com.example.FriendsRanking.services.FriendService;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class FriendServiceTest {
	
	@InjectMocks
	private FriendService friendService;
	
	@Mock
	private FriendRepository friendRepositoryMock;
	
	@Mock
	private UserRepository userRepositoryMock;
	
	@BeforeEach
	void setUp() {
		
		when(userRepositoryMock.findByEmail(anyString())).thenReturn(Optional.of(createUser()));
		
		when(friendRepositoryMock.save(any(Friend.class))).thenReturn(createFriend());
		
		doNothing().when(friendRepositoryMock).deleteById(anyLong());
		
	}
	
	@Test
	void insertFriend_SavesFriend_WhenSuccessful() {
		ArgumentCaptor<Friend> argumentCaptor = ArgumentCaptor.forClass(Friend.class);
		friendService.insertFriend("xxx", createFriend());
		verify(friendRepositoryMock).save(argumentCaptor.capture());
		
		assertThat(argumentCaptor.getValue().getName()).isEqualTo(createFriend().getName());
		assertThat(argumentCaptor.getValue().getPoints()).isEqualTo(createFriend().getPoints());
		assertThatCode(() -> friendService.insertFriend("xxx", createFriend())).doesNotThrowAnyException();
		assertThatCode(() -> friendService.insertFriend("xxx", createFriend())).isNull();
	}
	
	@Test
	void updatePoints_UpdatePointsAndSavesFriend_WhenSuccessful() {
		Friend friend = createFriend();
		friend.setPoints(80);
		when(friendRepositoryMock.findById(anyLong())).thenReturn(Optional.of(friend));
		when(friendRepositoryMock.save(any(Friend.class))).thenReturn(friend);
		ArgumentCaptor<Friend> argumentCaptor = ArgumentCaptor.forClass(Friend.class);
		friendService.updatePoints(1L, 80);
		verify(friendRepositoryMock).save(argumentCaptor.capture());
		
		assertThat(argumentCaptor.getValue().getName()).isEqualTo(friend.getName());
		assertThat(argumentCaptor.getValue().getPoints()).isEqualTo(friend.getPoints());
		assertThatCode(() -> friendService.updatePoints(1L, 80)).doesNotThrowAnyException();
		assertThatCode(() -> friendService.updatePoints(1L, 80)).isNull();
	}
	
	@Test
	void deleteFriend_deletesFriend_WhenSuccessful() {	
		assertThatCode(() -> friendService.deleteFriend(1L)).doesNotThrowAnyException();
		assertThatCode(() -> friendService.deleteFriend(1L)).isNull();
	}
}
