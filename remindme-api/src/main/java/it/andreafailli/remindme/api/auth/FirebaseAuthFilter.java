package it.andreafailli.remindme.api.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class FirebaseAuthFilter extends OncePerRequestFilter {
	
	public static final String HEADER_NAME = "X-Authorization-Firebase";
	
	@Autowired(required = false)
	public IFirebaseIdTokenAuthenticator firebaseIdTokenAuthenticator;
	
	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String idToken = request.getHeader(FirebaseAuthFilter.HEADER_NAME);
        if (idToken == null || "".equals(idToken)) {
            filterChain.doFilter(request, response);
            return;
        } else {
            try {
            	Authentication auth = this.firebaseIdTokenAuthenticator.authenticate(idToken);
                SecurityContextHolder.getContext().setAuthentication(auth);

                filterChain.doFilter(request, response);
            } catch (Exception e) {
                throw new SecurityException(e);
            }
        }
    }

}