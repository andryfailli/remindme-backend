package it.andreafailli.remindme.auth;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import it.andreafailli.remindme.Profiles;
import it.andreafailli.remindme.api.auth.FirebaseAuthenticationToken;
import it.andreafailli.remindme.api.auth.IFirebaseIdTokenAuthenticator;

@Service
@Profile(Profiles.TEST)
public class FirebaseIdTokenAuthenticatorMock implements IFirebaseIdTokenAuthenticator {
	
	public static final String ID_TOKEN_MOCK = "ID_TOKEN_MOCK";
	
	public static final String USER_MOCK_ID = "USER_MOCK_ID";

	@Override
	public FirebaseAuthenticationToken authenticate(String idToken) throws InterruptedException, ExecutionException {
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("USER");
		return new FirebaseAuthenticationToken(USER_MOCK_ID, null, authorities);
	}

}
