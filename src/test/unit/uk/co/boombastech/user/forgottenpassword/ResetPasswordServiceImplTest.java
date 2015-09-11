package uk.co.boombastech.user.forgottenpassword;

import org.junit.Before;
import org.junit.Test;
import uk.co.boombastech.encryption.EncryptionService;
import uk.co.boombastech.encryption.EncryptionServiceStub;
import uk.co.boombastech.user.Profile;
import uk.co.boombastech.user.UserRepository;
import uk.co.boombastech.user.UserRepositoryStub;
import uk.co.boombastech.user.exceptions.InvalidForgottenPasswordCodeException;
import uk.co.boombastech.user.exceptions.UnknownUserException;
import uk.co.boombastech.user.resetpassword.ResetPasswordMessage;
import uk.co.boombastech.user.resetpassword.ResetPasswordService;
import uk.co.boombastech.user.resetpassword.ResetPasswordServiceImpl;

import static uk.co.boombastech.user.ProfileBuilder.newProfile;
import static uk.co.boombastech.user.model.ProfileAssertions.assertThat;

public class ResetPasswordServiceImplTest {

	private ResetPasswordService resetPasswordService;

	private UserRepository userRepository;
	private ForgottenPasswordRepository forgottenPasswordRepository;
	private EncryptionService encryptionService;
	private Profile profileWithForgottenPasswordCode;
	private Profile profileWithoutForgottenPasswordCode;

	@Before
	public void setup() throws Exception {
		profileWithForgottenPasswordCode = newProfile()
				.withId("profileId")
				.withEmail("valid@email.com")
				.withPassword("oldPassword")
				.build();

		profileWithoutForgottenPasswordCode = newProfile()
				.withId("profileId2")
				.withEmail("valid2@email.com")
				.withPassword("oldPassword")
				.build();

		userRepository = new UserRepositoryStub()
				.withProfile(profileWithForgottenPasswordCode)
				.withProfile(profileWithoutForgottenPasswordCode);

		forgottenPasswordRepository = new ForgottenPasswordRepositoryStub();
		forgottenPasswordRepository.createPasswordReset(profileWithForgottenPasswordCode);

		encryptionService = new EncryptionServiceStub();

		resetPasswordService = new ResetPasswordServiceImpl(userRepository, forgottenPasswordRepository, encryptionService);
	}

	@Test
	public void shouldChangeProfilePasswordWhenCorrectForgottenPasswordCodeUsed() throws Exception {
		ResetPasswordMessage validResetPasswordMessage = new ResetPasswordMessage(profileWithForgottenPasswordCode.getEmail(), "newPassword", "newPassword", profileWithForgottenPasswordCode.getId());

		resetPasswordService.resetPassword(validResetPasswordMessage);

		assertThat(profileWithForgottenPasswordCode).hasPassword(encryptionService.encrypt("newPassword"));
	}

	@Test(expected = InvalidForgottenPasswordCodeException.class)
	public void shouldThrowExceptionWhenNoValidForgottenPasswordCodeUsed() throws Exception {
		ResetPasswordMessage validResetPasswordMessage = new ResetPasswordMessage(profileWithoutForgottenPasswordCode.getEmail(), "newPassword", "newPassword", profileWithoutForgottenPasswordCode.getId());

		resetPasswordService.resetPassword(validResetPasswordMessage);
	}

	@Test(expected = UnknownUserException.class)
	public void shouldThrowExceptionWhenNoProfileFound() throws Exception {
		ResetPasswordMessage validResetPasswordMessage = new ResetPasswordMessage("invalid@email.com", "newPassword", "newPassword", "doesNotMatter");

		resetPasswordService.resetPassword(validResetPasswordMessage);
	}

	@Test(expected = InvalidForgottenPasswordCodeException.class)
	public void shouldThrowExceptionWhenForgottenPasswordCodeForDifferentProfileUsed() throws Exception {
		ResetPasswordMessage validResetPasswordMessage = new ResetPasswordMessage(profileWithoutForgottenPasswordCode.getEmail(), "newPassword", "newPassword", profileWithForgottenPasswordCode.getId());

		resetPasswordService.resetPassword(validResetPasswordMessage);
	}
}