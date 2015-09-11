package uk.co.boombastech.user.forgottenpassword;

import uk.co.boombastech.user.Profile;

import java.util.Optional;

public interface ForgottenPasswordRepository {
	Optional<String> getPasswordReset(Profile profile);
	String createPasswordReset(Profile passwordResetObject);
	void exhaustCode(Profile profile);
}