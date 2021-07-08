package com.example.signup.login.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.signup.login.models.Role;
import com.example.signup.login.models.User;
import com.example.signup.login.models.enumeration.ERole;
import com.example.signup.login.payload.requests.LoginRequest;
import com.example.signup.login.payload.requests.SignupRequest;
import com.example.signup.login.payload.responce.LoginResponce;
import com.example.signup.login.repositories.RoleRepository;
import com.example.signup.login.repositories.UserRepository;
import com.example.signup.login.security.jwt.JwtUtils;
import com.example.signup.login.security.services.CustomUserDetails;

@RestController
public class AppController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}
	
	@PostMapping("/register")
	public String register(@RequestBody SignupRequest signupUser) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodePassword = passwordEncoder.encode(signupUser.getPassword());
		signupUser.setPassword(encodePassword);
		
//		User user = new User(signupUser.getEmail(), signupUser.getPassword(),
				//signupUser.getFirstName(), signupUser.getLastName()
			//	);
		
		//userRepo.save(user);
		
		User user = new User(signupUser.getUsername(), signupUser.getEmail(),
				signupUser.getPassword(), signupUser.getFirstName(), signupUser.getLastName());
		
		Set<String> strRoles = signupUser.getRoles();
		Set<Role> roles = new HashSet<Role>();
		
		if (strRoles == null) {
			Role userRole = roleRepo.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("**************************** ERROR *******************************"
							+ "Role is not found."
							+ "**************************** ERROR *******************************"));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepo.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
					
				case "user":
					Role userRole = roleRepo.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		
		user.setRoles(roles);
		userRepo.save(user);
				
		return "registered successfully";
	}
	
	@GetMapping("/users")
	public @ResponseBody List<User> getAllUsers() {
		return userRepo.findAll();
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login (@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.GeneraateToken(authentication);
		
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList()); 		
		
		return ResponseEntity.ok(new LoginResponce(jwt, userDetails.getFirstName(),
				userDetails.getLastName(), userDetails.getEmail(), roles));
		
	}
	
	
	@GetMapping("/fill-roles")
	public String fillRoles() {
		
		Role userRole = new Role(ERole.ROLE_USER);
		Role adminRole = new Role(ERole.ROLE_ADMIN);
		
		roleRepo.save(userRole);
		roleRepo.save(adminRole);
		
		return "Done.";
	}
	
	

}
