//Powered by: https://github.com/awaters1
package com.atomiclab.socialgamerbackend.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atomiclab.socialgamerbackend.security.model.FirebaseAuthenticationToken;
import com.google.api.client.util.Strings;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

public class FirebaseAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {
	private final static String TOKEN_HEADER = "X-Firebase-Auth";

	public FirebaseAuthenticationTokenFilter() {
		super("/auth/**");
	}
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		final String authToken = request.getHeader(TOKEN_HEADER);
		System.out.println(authToken);
		if (Strings.isNullOrEmpty(authToken)) {
			throw new RuntimeException("Invaild auth token");
		}
		return getAuthenticationManager().authenticate(new FirebaseAuthenticationToken(authToken));
	}
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
		System.out.println("Se intento");
        super.successfulAuthentication(request, response, chain, authResult);
        System.out.println("Lo logramos");
        chain.doFilter(request, response);
    }
}