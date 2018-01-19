package it.andreafailli.remindme.auth;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithUserMockSecurityContextFactory.class)
public @interface WithUserMock {

	String id() default "id";
}