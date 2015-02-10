package cz.gattserver.pubs.model.dto;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import cz.gattserver.pubs.security.Role;

/**
 * Objekt sloužící pro přepravu dat mezi fasádou a view třídami. Obsahuje pouze "povrchové" informace a vynechává tak
 * různé reference jako reference na oblíbené odkazy. Snižuje tak přenost z DB. Toto rozdělení je lepší než tam nechávat
 * null hodnoty.
 * 
 * @author gatt
 * 
 */
public class UserDTO implements UserDetails {

	private static final long serialVersionUID = -3792334399923911589L;

	/**
	 * Jméno uživatele
	 */
	private String name;

	/**
	 * Heslo uživatele
	 */
	private String password;

	/**
	 * Role uživatele
	 */
	private Set<Role> roles = EnumSet.noneOf(Role.class);

	/**
	 * Email
	 */
	private String email;

	/**
	 * DB identifikátor
	 */
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

	public boolean isEnabled() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public String getUsername() {
		return this.getName();
	}

	public String getPassword() {
		return password;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.getRoles();
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserDTO) {
			UserDTO p2 = (UserDTO) obj;
			return id.equals(p2.getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

}
