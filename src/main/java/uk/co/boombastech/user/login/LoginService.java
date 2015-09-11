package uk.co.boombastech.user.login;

import uk.co.boombastech.user.Profile;
import uk.co.boombastech.user.exceptions.AuthenticationException;

public interface LoginService {
	Profile login(LoginMessage loginMessage) throws AuthenticationException;
}