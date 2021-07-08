package com.example.signup.login.security.services;

import com.example.signup.login.models.User;
import com.example.signup.login.models.VerificationToken;

public interface IUserService {

	User getUser(String verificationToken);

	void saveRegisteredUser(User user);

	void createVerificationToken(User user, String token);

	VerificationToken getVerificationToken(String verificationToken);

}
