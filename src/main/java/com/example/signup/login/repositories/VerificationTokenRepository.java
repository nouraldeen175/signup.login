package com.example.signup.login.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.signup.login.models.User;
import com.example.signup.login.models.VerificationToken;



@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

	VerificationToken findByToken(String token);

	VerificationToken findByUser(User user);

}