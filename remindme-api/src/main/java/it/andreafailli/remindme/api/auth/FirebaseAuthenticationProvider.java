package it.andreafailli.remindme.api.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import it.andreafailli.remindme.common.models.User;
import it.andreafailli.remindme.common.services.UserService;

@Component
public class FirebaseAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    public boolean supports(Class<?> authentication) {
        return (FirebaseAuthenticationToken.class.isAssignableFrom(authentication));
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }

        FirebaseAuthenticationToken authenticationToken = (FirebaseAuthenticationToken) authentication;
        User user = this.userService.get(authenticationToken.getName());
        if (user == null) {
            user = new User(authenticationToken.getName());
            this.userService.insert(user);
        }
        
        user.setEmail(authenticationToken.getCredentials().getEmail());
        user.setName(authenticationToken.getCredentials().getName());
        user.setPhotoUrl(authenticationToken.getCredentials().getPicture());
        this.userService.update(user);

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("USER");
        authenticationToken = new FirebaseAuthenticationToken(user.getId(), authenticationToken.getCredentials(), authorities);

        return authenticationToken;
    }

}