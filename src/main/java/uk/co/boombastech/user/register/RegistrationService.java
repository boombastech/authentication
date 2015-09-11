package uk.co.boombastech.user.register;

import uk.co.boombastech.user.Profile;
import uk.co.boombastech.user.exceptions.AuthenticationException;

public interface RegistrationService {
	Profile register(RegistrationMessage registrationMessage) throws AuthenticationException;
}