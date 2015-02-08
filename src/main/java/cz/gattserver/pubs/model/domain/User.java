package cz.gattserver.pubs.model.domain;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import cz.gattserver.pubs.security.Role;

@Entity
@Table(name = "USER_ACCOUNTS", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class User implements Serializable {

	private static final long serialVersionUID = 1370519912799856102L;

	/**
	 * Jméno uživatele
	 */
	@Column(nullable = false)
	private String name;

	/**
	 * Heslo uživatele
	 */
	@Column(nullable = false)
	private String password;

	/**
	 * Role uživatele
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private Set<Role> roles = EnumSet.noneOf(Role.class);

	/**
	 * Email
	 */
	@Column(nullable = false)
	private String email;

	/**
	 * DB identifikátor
	 */
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	
}
