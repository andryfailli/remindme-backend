package it.andreafailli.remindme.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.api.core.ApiFuture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

import it.andreafailli.remindme.api.auth.FirebaseAuthenticationToken;
import it.andreafailli.remindme.api.auth.FirebaseIdTokenAuthenticator;
import it.andreafailli.remindme.testing.UnitTestCategory;

@Category(UnitTestCategory.class)
public class FirebaseIdTokenAuthenticatorTest {
	
	private final String ID_TOKEN_MOCK = "ID_TOKEN_MOCK";
		
	@InjectMocks
	private FirebaseIdTokenAuthenticator firebaseIdTokenAuthenticator;
	
    @Mock
    private FirebaseAuth firebaseAuth;
    
    private FirebaseToken firebaseToken;
	
	@Before
    public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.firebaseIdTokenAuthenticator = new FirebaseIdTokenAuthenticator(this.firebaseAuth);
		this.firebaseToken = new FirebaseTokenBuilder()
				.uuid("1")
				.email("user@example.com")
				.name("User")
				.picture("http://www.example.com/user.jpg")
				.build();
    }
	
	@Test
	public void testAuthenticate() throws Exception {
		
		ApiFuture<FirebaseToken> apiFuture = new ApiFuture<FirebaseToken>() {
			
			@Override
			public boolean isDone() {
				return true;
			}
			
			@Override
			public boolean isCancelled() {
				return false;
			}
			
			@Override
			public FirebaseToken get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
				return this.get();
			}
			
			@Override
			public FirebaseToken get() throws InterruptedException, ExecutionException {
				return firebaseToken;
			}
			
			@Override
			public boolean cancel(boolean mayInterruptIfRunning) {
				return false;
			}
			
			@Override
			public void addListener(Runnable arg0, Executor arg1) {
				
			}
		};
		
		when(this.firebaseAuth.verifyIdTokenAsync(this.ID_TOKEN_MOCK)).thenReturn(apiFuture);
		assertThat(apiFuture.isDone()).isTrue();
		assertThat(apiFuture.get(0, null)).isEqualTo(this.firebaseToken);
		assertThat(apiFuture.isCancelled()).isFalse();
		assertThat(apiFuture.cancel(true)).isFalse();
		assertThat(apiFuture.cancel(false)).isFalse();
		apiFuture.addListener(null, null);
		
		FirebaseAuthenticationToken firebaseAuthenticationToken = this.firebaseIdTokenAuthenticator.authenticate(this.ID_TOKEN_MOCK);
		
		verify(this.firebaseAuth).verifyIdTokenAsync(this.ID_TOKEN_MOCK);
		assertThat(firebaseAuthenticationToken.getPrincipal()).isEqualTo(this.firebaseToken.getUid());
		assertThat(firebaseAuthenticationToken.getCredentials()).isEqualTo(this.firebaseToken);
	}	
	
}
