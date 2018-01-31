package it.andreafailli.remindme.auth;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import com.google.firebase.auth.FirebaseToken;

import it.andreafailli.remindme.api.auth.FirebaseAuthenticationToken;
import it.andreafailli.remindme.testing.UnitTestCategory;

@Category(UnitTestCategory.class)
public class FirebaseAuthenticationTokenTest {
	
	private String principal = "principal";
	
	private FirebaseToken firebaseToken;
	
	private List<GrantedAuthority> authorities;
	
	@Before
    public void setUp() throws Exception {

		this.authorities = AuthorityUtils.createAuthorityList("USER");
        
		this.firebaseToken = new FirebaseTokenBuilder()
				.uuid("1")
				.email("user@example.com")
				.name("User")
				.picture("http://www.example.com/user.jpg")
				.build();
    }
	
	private FirebaseAuthenticationToken createAuthenticated() {
		return new FirebaseAuthenticationToken(this.principal, this.firebaseToken, this.authorities);
	}
	
	private FirebaseAuthenticationToken createNotAuthenticated() {
		return new FirebaseAuthenticationToken(this.principal, this.firebaseToken);
	}
	
	@Test
	public void testConstructorAuthenticated() {
		FirebaseAuthenticationToken firebaseAuthenticationToken = this.createAuthenticated();
		assertThat(firebaseAuthenticationToken.getAuthorities()).containsAll(this.authorities);
		assertThat(firebaseAuthenticationToken.getPrincipal()).isEqualTo(this.principal);
		assertThat(firebaseAuthenticationToken.getCredentials()).isEqualTo(this.firebaseToken);
		assertThat(firebaseAuthenticationToken.isAuthenticated()).isTrue();
	}
	
	@Test
	public void testConstructorNotAuthenticated() {
		FirebaseAuthenticationToken firebaseAuthenticationToken = this.createNotAuthenticated();
		assertThat(firebaseAuthenticationToken.getAuthorities()).isEmpty();
		assertThat(firebaseAuthenticationToken.getPrincipal()).isEqualTo(this.principal);
		assertThat(firebaseAuthenticationToken.getCredentials()).isEqualTo(this.firebaseToken);
		assertThat(firebaseAuthenticationToken.isAuthenticated()).isFalse();
	}
	
	@Test
	public void testGetCredentials() {
		FirebaseAuthenticationToken firebaseAuthenticationToken = this.createAuthenticated();
		assertThat(firebaseAuthenticationToken.getCredentials()).isEqualTo(this.firebaseToken);
	}
	
	@Test
	public void testGetPrincipal() {
		FirebaseAuthenticationToken firebaseAuthenticationToken = this.createAuthenticated();
		assertThat(firebaseAuthenticationToken.getPrincipal()).isEqualTo(this.principal);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetAuthenticatedTrue() {
		FirebaseAuthenticationToken firebaseAuthenticationToken = this.createAuthenticated();
		firebaseAuthenticationToken.setAuthenticated(true);
	}
	
	@Test
	public void testSetAuthenticatedFalse() {
		FirebaseAuthenticationToken firebaseAuthenticationToken = this.createAuthenticated();
		firebaseAuthenticationToken.setAuthenticated(false);
	}
	
	@Test
	public void testEraseCredentials() {
		FirebaseAuthenticationToken firebaseAuthenticationToken = this.createAuthenticated();
		firebaseAuthenticationToken.eraseCredentials();
		assertThat(firebaseAuthenticationToken.getCredentials()).isNull();
	}
}
