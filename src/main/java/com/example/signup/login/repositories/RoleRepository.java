package com.example.signup.login.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.signup.login.models.Role;
import com.example.signup.login.models.enumeration.ERole;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Optional<Role> findByName(ERole name);

}
