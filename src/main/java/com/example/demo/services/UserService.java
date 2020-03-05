package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.models.User;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.utils.RoleEnum;

/**
 * 
 * @author nicolas.praz Utilisé par le UserValidator pour vérifier si email pas
 *         déjà utilisé Utilisé par UserController pour signup le nouveau
 *         utilisateur et pour récupérer l'utilisateur avant de faire autologin
 *         (qui se trouve dans SecurityServiceImpl.autologin)
 */

@Service
public class UserService implements UserServiceInterface {

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
			user.setRole(roleRepository.findByName(""+RoleEnum.USER));
		}
		userRepository.save(user);

	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public Long deleteByEmail(String email) {
		System.out.println("USERSERVICE: " + email);
		return userRepository.deleteByEmail(email);
	}

}
