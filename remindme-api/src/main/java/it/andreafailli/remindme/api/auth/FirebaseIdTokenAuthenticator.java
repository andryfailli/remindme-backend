package it.andreafailli.remindme.api.auth;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

import it.andreafailli.remindme.Profiles;

@Service
@Profile("!"+Profiles.TEST)
public class FirebaseIdTokenAuthenticator implements IFirebaseIdTokenAuthenticator {

	@Autowired
	private FirebaseAuth firebaseAuth;
	
	@Override
	public FirebaseAuthenticationToken authenticate(String idToken) throws InterruptedException, ExecutionException {
		FirebaseToken decodedToken = this.firebaseAuth.verifyIdTokenAsync(idToken).get();
        return new FirebaseAuthenticationToken(decodedToken.getUid(), decodedToken);
	}

}
