package uk.co.boombastech.user.forgottenpassword;

import uk.co.boombastech.user.exceptions.AuthenticationException;

public interface ForgottenPasswordService {
	String createForgottenPasswordCode(ForgottenPasswordCodeMessage passwordResetMessage) throws AuthenticationException;
}
