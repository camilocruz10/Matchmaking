//Powered by: https://github.com/awaters1
package com.atomiclab.socialgamerbackend.security;

import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.security.model.FirebaseAuthenticationToken;
import com.atomiclab.socialgamerbackend.security.model.FirebaseUserDetails;
import com.atomiclab.socialgamerbackend.service.FirebaseService;
import com.google.api.core.ApiFuture;
import com.google.firebase.auth.FirebaseToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class FirebaseAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
	@Autowired
	FirebaseService firebaseService;
	@Override
	public boolean supports(Class<?> authentication) {
		return (FirebaseAuthenticationToken.class.isAssignableFrom(authentication));
	}
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
	}
	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		final FirebaseAuthenticationToken authenticationToken = (FirebaseAuthenticationToken) authentication;
		ApiFuture<FirebaseToken> task = firebaseService.getFirebasAuth().verifyIdTokenAsync(authenticationToken.getToken());
		try {
			FirebaseToken token = task.get();
			return new FirebaseUserDetails(token.getEmail(), token.getUid());
		} catch (InterruptedException | ExecutionException e) {
			throw new SessionAuthenticationException(e.getMessage());
		}
	}
}
