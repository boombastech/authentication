package uk.co.boombastech.user.forgottenpassword;

import org.junit.Before;
import org.junit.Test;
import uk.co.boombastech.email.EmailServiceStub;
import uk.co.boombastech.user.Profile;
import uk.co.boombastech.user.UserRepository;
import uk.co.boombastech.user.UserRepositoryStub;
import uk.co.boombastech.user.exceptions.UnknownUserException;

import java.util.Optional;

import static org.assertj.core.api.StrictAssertions.assertThat;
import static uk.co.boombastech.user.ProfileBuilder.newProfile;

public class ForgottenPasswordServiceImplTest {

	private ForgottenPasswordService forgottenPasswordService;

	private UserRepository userRepository;
	private ForgottenPasswordRepository forgottenPasswordRepository;
	private EmailServiceStub emailService;
	private Profile validProfile;

	@Before
	public void setup() throws Exception {
		validProfile = newProfile().withId("validProfileId").withEmail("valid@email.com").build();
		userRepository = new UserRepositoryStub()
				.withProfile(validProfile);

		forgottenPasswordRepository = new ForgottenPasswordRepositoryStub();
		emailService = new EmailServiceStub();

		forgottenPasswordService = new ForgottenPasswordServiceImpl(userRepository, forgottenPasswordRepository, emailService);
	}

	@Test
	public void shouldCreateForgottenPasswordCodeForUser() throws Exception {
		String forgottenPasswordCode = forgottenPasswordService.createForgottenPasswordCode(new ForgottenPasswordCodeMessage("valid@email.com"));

		Optional<String> passwordReset = forgottenPasswordRepository.getPasswordReset(validProfile);

		assertThat(passwordReset.isPresent()).isTrue();
	}

	@Test(expected = UnknownUserException.class)
	public void shouldThrowExceptionIfEmailIsInvalid() throws Exception {
		forgottenPasswordService.createForgottenPasswordCode(new ForgottenPasswordCodeMessage("invalid@email.com"));
	}
}