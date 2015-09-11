package uk.co.boombastech.user.resetpassword;

import uk.co.boombastech.encryption.EncryptionService;
import uk.co.boombastech.user.Profile;
import uk.co.boombastech.user.UserRepository;
import uk.co.boombastech.user.exceptions.AuthenticationException;
import uk.co.boombastech.user.exceptions.InvalidForgottenPasswordCodeException;
import uk.co.boombastech.user.exceptions.NonMatchingPasswordsException;
import uk.co.boombastech.user.exceptions.UnknownUserException;
import uk.co.boombastech.user.forgottenpassword.ForgottenPasswordRepository;

public class ResetPasswordServiceImpl implements ResetPasswordService {

	private final UserRepository userRepository;
	private final ForgottenPasswordRepository forgottenPasswordRepository;
	private final EncryptionService encryptionService;

	public ResetPasswordServiceImpl(UserRepository userRepository, ForgottenPasswordRepository forgottenPasswordRepository, EncryptionService encryptionService) {
		this.userRepository = userRepository;
		this.forgottenPasswordRepository = forgottenPasswordRepository;
		this.encryptionService = encryptionService;
	}

	@Override
	public void resetPassword(ResetPasswordMessage resetPasswordMessage) throws AuthenticationException {
		Profile profile = userRepository.findByEmail(resetPasswordMessage.getEmail()).orElseThrow(() -> new UnknownUserException());

		if (nonMatchingPasswords(resetPasswordMessage)) {
			throw new NonMatchingPasswordsException();
		}

		String forgottenPasswordCode = forgottenPasswordRepository.getPasswordReset(profile).orElseThrow(() -> new InvalidForgottenPasswordCodeException());

		if (invalidForgottenPasswordCode(forgottenPasswordCode, resetPasswordMessage)) {
			throw new InvalidForgottenPasswordCodeException();
		}

		profile.setPassword(encryptionService.encrypt(resetPasswordMessage.getPassword()));

		userRepository.update(profile);

		forgottenPasswordRepository.exhaustCode(profile);
	}

	private boolean invalidForgottenPasswordCode(String forgottenPasswordCode, ResetPasswordMessage resetPasswordMessage) {
		return !forgottenPasswordCode.equals(resetPasswordMessage.getForgottenPasswordCode());
	}

	private boolean nonMatchingPasswords(ResetPasswordMessage resetPasswordMessage) {
		return !resetPasswordMessage.getPassword().equals(resetPasswordMessage.getConfirmPassword());
	}
}