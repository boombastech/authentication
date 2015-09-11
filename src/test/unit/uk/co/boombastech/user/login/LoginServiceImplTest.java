package uk.co.boombastech.user.login;

import org.junit.Before;
import org.junit.Test;
import uk.co.boombastech.encryption.EncryptionService;
import uk.co.boombastech.encryption.EncryptionServiceStub;
import uk.co.boombastech.user.Profile;
import uk.co.boombastech.user.UserRepository;
import uk.co.boombastech.user.UserRepositoryStub;
import uk.co.boombastech.user.exceptions.InvalidPasswordLoginException;
import uk.co.boombastech.user.exceptions.UnknownUserException;

import static uk.co.boombastech.user.ProfileBuilder.newProfile;
import static uk.co.boombastech.user.login.LoginMessageBuilder.newLoginMessage;
import static uk.co.boombastech.user.model.ProfileAssertions.assertThat;

public class LoginServiceImplTest {

	private LoginService loginService;
	private UserRepository userRepository;
	private EncryptionService encryptionService;

	@Before
	public void setup() throws Exception {
		userRepository = new UserRepositoryStub()
				.withProfile(newProfile()
						.withEmail("valid1@email.com")
						.withPassword("validPassword"));

		encryptionService = new EncryptionServiceStub();

		loginService = new LoginServiceImpl(userRepository, encryptionService);
	}

	@Test
	public void shouldReturnValidProfileIfLoginMessageCorrect() throws Exception {
		LoginMessage validLoginMessage = newLoginMessage().withLoginName("valid1@email.com").withPassword("validPassword").withRemember(true).build();

		Profile profile = loginService.login(validLoginMessage);
		assertThat(profile).hasEmail("valid1@email.com");
	}

	@Test(expected = UnknownUserException.class)
	public void shouldThrowFailedLoginExceptionWithUnknownUserMessageWhenInvalidEmailProvided() throws Exception {
		LoginMessage invalidLoginMessage = newLoginMessage().withLoginName("invalid@email.com").withPassword("validPassword").withRemember(true).build();

		loginService.login(invalidLoginMessage);

	}

	@Test(expected = InvalidPasswordLoginException.class)
	public void shouldThrowFailedLoginExceptionWithInvalidPasswordMessageWhenIncorrectPasswordProvided() throws Exception {
		LoginMessage invalidLoginMessage = newLoginMessage().withLoginName("valid1@email.com").withPassword("invalidPassword").withRemember(true).build();

		loginService.login(invalidLoginMessage);
	}
}