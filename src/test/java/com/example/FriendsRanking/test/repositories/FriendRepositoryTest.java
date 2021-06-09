package com.example.FriendsRanking.test.repositories;

import static com.example.FriendsRanking.test.utils.FriendCreator.createFriend;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.FriendsRanking.entities.Friend;
import com.example.FriendsRanking.repositories.FriendRepository;;

@ActiveProfiles("test")
@DataJpaTest
class FriendRepositoryTest {
	
	@Autowired
	private FriendRepository friendRepository;
	
	@Test
	void findById_ReturnsFriend_WhenSuccessful() {
		friendRepository.save(createFriend());
		Optional<Friend> friend = friendRepository.findById(1L);
		
		assertThat(friend).isNotNull().isNotEmpty();
	}
	
	@Test
	void save_SaveAndReturnsFriend_WhenSuccessful() {
		Friend friend = friendRepository.save(createFriend());
		Optional<Friend> savedFriend = friendRepository.findById(friend.getId());
		
		assertThat(friend).isNotNull();
		assertThat(friend.getName()).isEqualTo(savedFriend.get().getName());
	}
	
	@Test
	void deleteById_DeletesFriend_WhenSuccessful() {
		Friend friend = friendRepository.save(createFriend());
		friendRepository.deleteById(friend.getId());
		Optional<Friend> deletedFriend = friendRepository.findById(friend.getId());
		
		assertThat(deletedFriend).isEmpty();
	}
}
