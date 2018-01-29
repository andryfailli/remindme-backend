package it.andreafailli.remindme.api.auth;

import org.springframework.boot.autoconfigure.security.Http401AuthenticationEntryPoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(2)
public class DefaultWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
	
	@Override
    public void configure(HttpSecurity http) throws Exception {
		http
    	.csrf().disable()
    	.sessionManagement().disable()
    	.httpBasic().disable()
    	.formLogin().disable()
    	.logout().disable()
    	.rememberMe().disable()
    	
    	.authorizeRequests()
        	.antMatchers(HttpMethod.OPTIONS).permitAll()
        	.antMatchers("/api/*").authenticated()
    	.and()
            .exceptionHandling()
            .authenticationEntryPoint(new Http401AuthenticationEntryPoint(FirebaseAuthFilter.HEADER_NAME));
    }
}