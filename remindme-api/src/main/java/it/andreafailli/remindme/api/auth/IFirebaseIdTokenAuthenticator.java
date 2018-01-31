package it.andreafailli.remindme.api.auth;

import java.util.concurrent.ExecutionException;

public interface IFirebaseIdTokenAuthenticator {
	
	public FirebaseAuthenticationToken authenticate(String idToken) throws InterruptedException, ExecutionException;

}
