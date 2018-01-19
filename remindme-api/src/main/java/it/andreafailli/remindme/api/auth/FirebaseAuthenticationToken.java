package it.andreafailli.remindme.api.auth;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.google.firebase.auth.FirebaseToken;

public class FirebaseAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = -1869548132546750002L;
	
	private final String principal;
	
	private transient FirebaseToken credentials;

	public FirebaseAuthenticationToken(String principal, FirebaseToken credentials) {
		super(null);
		this.principal = principal;
		this.credentials = credentials;
		super.setAuthenticated(false);
	}

	public FirebaseAuthenticationToken(String principal, FirebaseToken credentials, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		super.setAuthenticated(true);
	}
	
	public FirebaseToken getCredentials() {
		return this.credentials;
	}

	public Object getPrincipal() {
		return this.principal;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) {
		if (isAuthenticated) {
			throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
		}
		super.setAuthenticated(false);
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
		this.credentials = null;
	}
}