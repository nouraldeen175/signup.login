package com.example.signup.login.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.signup.login.models.User;
import com.example.signup.login.models.VerificationToken;
import com.example.signup.login.repositories.UserRepository;
import com.example.signup.login.repositories.VerificationTokenRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService, IUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private VerificationTokenRepository verificationtokenRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException("******************************* ERROR **********************************"
						+ "There is no user with this username -> " + username
						+ "******************************* ERROR **********************************"));
		
		return CustomUserDetails.build(user);
	}

	@Override
	public User getUser(String verificationToken) {
		// TODO Auto-generated method stub

		User user = verificationtokenRepository.findByToken(verificationToken).getUser();
		return user;
	}

	@Override
	public void saveRegisteredUser(User user) {
		// TODO Auto-generated method stub
		//userRepository.updateUserEnable(user.getUsername());

	}

	@Override
	public void createVerificationToken(User user, String token) {
		// TODO Auto-generated method stub
		VerificationToken myToken = new VerificationToken(token, user);
        verificationtokenRepository.save(myToken);
	}

	@Override
	public VerificationToken getVerificationToken(String verificationToken) {
		// TODO Auto-generated method stub
		return verificationtokenRepository.findByToken(verificationToken);
	}

}
