package com.example.demo.service;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.Role_E;

/**
 * 
 * @author nicolas.praz Utilisé par le UserValidator pour vérifier si email pas
 *         déjà utilisé Utilisé par UserController pour signup le nouveau
 *         utilisateur et pour récupérer l'utilisateur avant de faire autologin
 *         (qui se trouve dans SecurityServiceImpl.autologin)
 */

@Service
public class UserService implements UserService_I {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void save(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		if (user.getRole() == null) {
			user.setRole(roleRepository.findByName(""+Role_E.USER));
		}
		userRepository.save(user);

	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}
