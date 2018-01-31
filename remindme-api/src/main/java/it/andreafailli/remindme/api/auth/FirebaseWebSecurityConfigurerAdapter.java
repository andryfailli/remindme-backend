
package it.andreafailli.remindme.api.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

@Configuration
@Order(1)
public class FirebaseWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private FirebaseAuthFilter firebaseAuthFilter;
	
	@Autowired
    private FirebaseAuthenticationProvider firebaseAuthenticationProvider;
	
	@Autowired
	private DefaultWebSecurityConfigurerAdapter defaultWebSecurityConfigurerAdapter;

	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(firebaseAuthenticationProvider);
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(firebaseAuthFilter, SecurityContextPersistenceFilter.class);
        this.defaultWebSecurityConfigurerAdapter.configure(http);
        	
    }
}