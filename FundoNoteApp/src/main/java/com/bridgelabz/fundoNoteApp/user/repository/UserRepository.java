package com.bridgelabz.fundoNoteApp.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoNoteApp.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	// public User saveUser(User user);

	List<User> findByIdAndPassword(int id, String password);

	Optional<User> findAllById(int id);

	Optional<User> findById(int id);

	boolean deleteById(int varifiedUserId);

//		static User findUserByEmail(String name) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	static boolean checkIfValidOldPassword(User user, String oldPassword) {
//		// TODO Auto-generated method stub
//		return false;
//	}	

}