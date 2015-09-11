package uk.co.boombastech.user.resetpassword;

import uk.co.boombastech.user.exceptions.AuthenticationException;

public interface ResetPasswordService {
	void resetPassword(ResetPasswordMessage resetPasswordMessage) throws AuthenticationException;
}