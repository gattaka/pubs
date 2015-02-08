package cz.gattserver.pubs.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cz.gattserver.pubs.model.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public List<User> findByName(String name);

	public List<User> findByNameAndPassword(String name, String passwordHash);

}
