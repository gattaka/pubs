package cz.gattserver.pubs.facades.impl;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cz.gattserver.pubs.facades.UserFacade;
import cz.gattserver.pubs.model.dao.PubRepository;
import cz.gattserver.pubs.model.dao.UserRepository;
import cz.gattserver.pubs.model.domain.User;
import cz.gattserver.pubs.model.dto.UserDTO;
import cz.gattserver.pubs.security.Role;
import cz.gattserver.pubs.util.MappingService;

@Transactional
@Component
public class UserFacadeImpl implements UserFacade {

	@Autowired
	private MappingService mapper;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UserRepository userRepository;

	/**
	 * Zkusí najít uživatele dle jména a hesla
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public UserDTO getUserByLogin(String username, String password) {
		List<User> loggedUser = userRepository.findByName(username);
		if (loggedUser != null && loggedUser.size() == 1
				&& loggedUser.get(0).getPassword().equals(encoder.encodePassword(password, null))) {

			User user = loggedUser.get(0);
			if (user != null)
				return mapper.map(user, UserDTO.class);
		}
		return null;
	}

	/**
	 * Zaregistruje nového uživatele
	 * 
	 * @param email
	 * @param username
	 * @param password
	 * @return <code>true</code> pokud se přidání zdařilo, jinak <code>false</code>
	 */
	public boolean registrateNewUser(String email, String username, String password) {

		User user = new User();
		user.setEmail(email);
		user.setName(username);
		user.setPassword(encoder.encodePassword(password, null));
		EnumSet<Role> roles = EnumSet.of(Role.USER);
		user.setRoles(roles);

		return userRepository.save(user) != null;
	}

	/**
	 * Upraví role uživatele
	 * 
	 * @param user
	 * @return <code>true</code> pokud se přidání zdařilo, jinak <code>false</code>
	 */
	public boolean changeUserRoles(Long user, Set<Role> roles) {
		User u = userRepository.findOne(user);
		u.setRoles(roles);
		return userRepository.save(u) != null;
	}

	/**
	 * Vrátí všechny uživatele
	 * 
	 * @return list uživatelů
	 */
	public List<UserDTO> getUserInfoFromAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserDTO> infoDTOs = new ArrayList<UserDTO>();
		for (User user : users) {
			infoDTOs.add(mapper.map(user, UserDTO.class));
		}
		return infoDTOs;
	}

	/**
	 * Vrátí uživatele dle jména
	 * 
	 * @param username
	 * @return
	 */
	public UserDTO getUser(String username) {
		List<User> loggedUser = userRepository.findByName(username);
		if (loggedUser != null && loggedUser.size() == 1) {
			User user = loggedUser.get(0);
			if (user != null)
				return mapper.map(user, UserDTO.class);
		}
		return null;
	}

}
