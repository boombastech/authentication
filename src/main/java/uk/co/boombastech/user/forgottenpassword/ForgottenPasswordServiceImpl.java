package uk.co.boombastech.user.forgottenpassword;

import uk.co.boombastech.email.Email;
import uk.co.boombastech.email.EmailService;
import uk.co.boombastech.user.Profile;
import uk.co.boombastech.user.UserRepository;
import uk.co.boombastech.user.exceptions.AuthenticationException;
import uk.co.boombastech.user.exceptions.UnknownUserException;

public class ForgottenPasswordServiceImpl implements ForgottenPasswordService {

	private final UserRepository userRepository;
	private final ForgottenPasswordRepository forgottenPasswordRepository;
	private final EmailService emailService;

	public ForgottenPasswordServiceImpl(UserRepository userRepository, ForgottenPasswordRepository forgottenPasswordRepository, EmailService emailService) {
		this.userRepository = userRepository;
		this.forgottenPasswordRepository = forgottenPasswordRepository;
		this.emailService = emailService;
	}

	@Override
	public String createForgottenPasswordCode(ForgottenPasswordCodeMessage forgottenPasswordCodeMessage) throws AuthenticationException {
		Profile profile = userRepository.findByEmail(forgottenPasswordCodeMessage.getEmail()).orElseThrow(() -> new UnknownUserException());

		String passwordResetCode = forgottenPasswordRepository.createPasswordReset(profile);

		Email registrationEmail = new Email(profile.getEmail(), "emailFromPropertiesFile", "Password Reset", String.format("Here's your code: %s", passwordResetCode));
		emailService.send(registrationEmail);

		return passwordResetCode;
	}
}