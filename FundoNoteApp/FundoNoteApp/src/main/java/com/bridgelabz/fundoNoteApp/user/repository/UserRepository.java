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

	Optional<User> findById(int id);

	boolean deleteById(int varifiedUserId);

	public User findByEmail(String email);

}