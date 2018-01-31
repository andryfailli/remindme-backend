package it.andreafailli.remindme.auth;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import it.andreafailli.remindme.api.auth.FirebaseAuthFilter;
import it.andreafailli.remindme.api.auth.IFirebaseIdTokenAuthenticator;
import it.andreafailli.remindme.testing.UnitTestCategory;

@Category(UnitTestCategory.class)
public class FirebaseAuthFilterTest {
	
	private final String ID_TOKEN_MOCK = "ID_TOKEN_MOCK";
		
	@InjectMocks
	private FirebaseAuthFilter filter;
	
    @Mock
    private IFirebaseIdTokenAuthenticator firebaseIdTokenAuthenticator;
	
    @Mock
	private HttpServletRequest request;
	
    @Mock
	private HttpServletResponse response;
	
    @Mock
	private FilterChain filterChain;
	
	@Before
    public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void testDoFilterHeaderNull() throws Exception {
		when(this.request.getHeader(FirebaseAuthFilter.HEADER_NAME)).thenReturn(null);
		filter.doFilter(this.request, this.response, this.filterChain);
		verify(this.request).getHeader(FirebaseAuthFilter.HEADER_NAME);
		verify(filterChain).doFilter(this.request, this.response);
		verify(this.firebaseIdTokenAuthenticator, times(0)).authenticate(anyString());
	}
	
	@Test
	public void testDoFilterHeaderEmpty() throws Exception {
		when(this.request.getHeader(FirebaseAuthFilter.HEADER_NAME)).thenReturn("");
		filter.doFilter(this.request, this.response, this.filterChain);
		verify(this.request).getHeader(FirebaseAuthFilter.HEADER_NAME);
		verify(filterChain).doFilter(this.request, this.response);
		verify(this.firebaseIdTokenAuthenticator, times(0)).authenticate(anyString());
	}
	
	@Test
	public void testDoFilterHeader() throws Exception {
		when(this.request.getHeader(FirebaseAuthFilter.HEADER_NAME)).thenReturn(this.ID_TOKEN_MOCK);
		filter.doFilter(this.request, this.response, this.filterChain);
		verify(this.request).getHeader(FirebaseAuthFilter.HEADER_NAME);
		verify(filterChain).doFilter(this.request, this.response);
		verify(this.firebaseIdTokenAuthenticator).authenticate(this.ID_TOKEN_MOCK);
	}
	
	@Test(expected=SecurityException.class)
	public void testDoFilterHeaderThrowingException() throws Exception {
		when(this.request.getHeader(FirebaseAuthFilter.HEADER_NAME)).thenReturn(this.ID_TOKEN_MOCK);
		when(this.firebaseIdTokenAuthenticator.authenticate(this.ID_TOKEN_MOCK)).thenThrow(new RuntimeException(""));
		filter.doFilter(this.request, this.response, this.filterChain);
		verify(this.request).getHeader(FirebaseAuthFilter.HEADER_NAME);
		verify(filterChain).doFilter(this.request, this.response);
		verify(this.firebaseIdTokenAuthenticator).authenticate(this.ID_TOKEN_MOCK);
	}
	
	
}
