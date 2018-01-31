package it.andreafailli.remindme.auth;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import it.andreafailli.remindme.api.auth.FirebaseAuthenticationToken;

public class WithUserMockSecurityContextFactory implements WithSecurityContextFactory<WithUserMock> {
	
	@Override
	public SecurityContext createSecurityContext(WithUserMock withUserMock) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();

		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("USER");
		FirebaseAuthenticationToken authenticationToken = new FirebaseAuthenticationToken(withUserMock.id(), null, authorities);
		
		context.setAuthentication(authenticationToken);
		return context;
	}
}