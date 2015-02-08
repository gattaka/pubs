package cz.gattserver.pubs.security;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

	ADMIN("Admin"), USER("Uživatel"), FRIEND("Host");

	private String roleName;

	private Role(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}

	public String getAuthority() {
		return toString();
	}
}
