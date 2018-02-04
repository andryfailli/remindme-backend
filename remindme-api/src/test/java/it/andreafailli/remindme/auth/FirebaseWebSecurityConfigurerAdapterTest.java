package it.andreafailli.remindme.auth;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import it.andreafailli.remindme.api.auth.DefaultWebSecurityConfigurerAdapter;
import it.andreafailli.remindme.api.auth.FirebaseAuthFilter;
import it.andreafailli.remindme.api.auth.FirebaseAuthenticationProvider;
import it.andreafailli.remindme.api.auth.FirebaseWebSecurityConfigurerAdapter;
import it.andreafailli.remindme.testing.UnitTestCategory;

@Category(UnitTestCategory.class)
public class FirebaseWebSecurityConfigurerAdapterTest {
	
	private static class FirebaseWebSecurityConfigurerAdapterExtension extends FirebaseWebSecurityConfigurerAdapter {
		
		@Override
	    public void configure(AuthenticationManagerBuilder auth) throws Exception {
	        super.configure(auth);
	    }
		
		@Override
		public void configure(HttpSecurity http) throws Exception {
			super.configure(http);
	    }
	}
	
	@InjectMocks
	private FirebaseWebSecurityConfigurerAdapterExtension firebaseWebSecurityConfigurerAdapter;

	@Mock
	private FirebaseAuthFilter firebaseAuthFilter;
	
	@Mock
    private FirebaseAuthenticationProvider firebaseAuthenticationProvider;
	
	@Mock
	private DefaultWebSecurityConfigurerAdapter defaultWebSecurityConfigurerAdapter;
    
    
    @Before
    public void setUp(){
    	MockitoAnnotations.initMocks(this);
    }
    
    @Test
	public void testConfigureAuth() throws Exception {
    	AuthenticationManagerBuilder authenticationManagerBuilder = mock(AuthenticationManagerBuilder.class);
    	this.firebaseWebSecurityConfigurerAdapter.configure(authenticationManagerBuilder);
		verify(authenticationManagerBuilder).authenticationProvider(this.firebaseAuthenticationProvider);
	}
    
    @Test
	public void testConfigureSecurity() throws Exception {
    	
    	ObjectPostProcessor<Object> objectPostProcessor = new ObjectPostProcessor<Object>() {
			
			@Override
			public <O> O postProcess(O object) {
				return object;
			}
		};
		AuthenticationManagerBuilder authenticationManagerBuilder = new AuthenticationManagerBuilder(objectPostProcessor);
		
    	HttpSecurity httpSecurity = new HttpSecurity(objectPostProcessor, authenticationManagerBuilder, new HashMap<Class<? extends Object>, Object>());
    	
    	this.firebaseWebSecurityConfigurerAdapter.configure(httpSecurity);
    	
		verify(this.defaultWebSecurityConfigurerAdapter).configure(httpSecurity);
	}

}
