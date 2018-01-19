package it.andreafailli.remindme.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.google.firebase.auth.FirebaseToken;

import it.andreafailli.remindme.api.auth.FirebaseAuthenticationProvider;
import it.andreafailli.remindme.api.auth.FirebaseAuthenticationToken;
import it.andreafailli.remindme.common.models.User;
import it.andreafailli.remindme.common.services.UserService;
import it.andreafailli.remindme.testing.UnitTestCategory;

@Category(UnitTestCategory.class)
public class FirebaseAuthenticationProviderTest {
	
	private static final String USER_ID_MOCK = "USER_ID_MOCK";
	
	private static class FirebaseAuthenticationTokenExtension extends FirebaseAuthenticationToken {

		private static final long serialVersionUID = 5223876711110721487L;

		public FirebaseAuthenticationTokenExtension(String principal, FirebaseToken credentials) {
			super(principal, credentials);
		}
		
		public FirebaseAuthenticationTokenExtension(String principal, FirebaseToken credentials, Collection<? extends GrantedAuthority> authorities) {
			super(principal, credentials, authorities);
		}
		
	}

	@InjectMocks
	private FirebaseAuthenticationProvider firebaseAuthenticationProvider;

    @Mock
    private UserService userService;
    
    private FirebaseToken firebaseToken;
    
    private User user1;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        
        this.user1 = new User(USER_ID_MOCK);
        this.user1.setEmail("user@example.com");
        this.user1.setName("Andrea");
        this.user1.setPhotoUrl("http://www.example.com/photo.jpg");
        
        this.firebaseToken = new FirebaseTokenBuilder()
				.uuid(this.user1.getId())
				.email(this.user1.getEmail())
				.name(this.user1.getName())
				.picture(this.user1.getPhotoUrl())
				.build();
    }
    
    @Test
	public void testSupportsTrue() {
		assertThat(this.firebaseAuthenticationProvider.supports(FirebaseAuthenticationToken.class)).isTrue();
		assertThat(this.firebaseAuthenticationProvider.supports(FirebaseAuthenticationTokenExtension.class)).isTrue();
	}
    
    @Test
	public void testSupportsFalse() {
		assertThat(this.firebaseAuthenticationProvider.supports(UsernamePasswordAuthenticationToken.class)).isFalse();
	}
    
    @Test
	public void testAuthenticateNull() {
    	Authentication authentication = new UsernamePasswordAuthenticationToken(null, null);
    	assertThat(this.firebaseAuthenticationProvider.authenticate(authentication)).isNull();
	}
    
    @Test
	public void testAuthenticateNewUser() {
    	Authentication authentication = new FirebaseAuthenticationToken(USER_ID_MOCK, this.firebaseToken);
		when(this.userService.get(USER_ID_MOCK)).thenReturn(null);
		assertThat(this.firebaseAuthenticationProvider.authenticate(authentication).getAuthorities()).isNotEmpty();
		verify(this.userService).get(USER_ID_MOCK);
		verify(this.userService).insert(eq(this.user1));
	}
    
    @Test
	public void testAuthenticateExistingUser() {
    	Authentication authentication = new FirebaseAuthenticationToken(USER_ID_MOCK, this.firebaseToken);
		when(this.userService.get(USER_ID_MOCK)).thenReturn(this.user1);
		assertThat(this.firebaseAuthenticationProvider.authenticate(authentication).getAuthorities()).isNotEmpty();
		
		verify(this.userService).get(USER_ID_MOCK);
		verify(this.userService, times(0)).insert(this.user1);
		
		verify(this.userService).update(this.user1);
	}
	
}
