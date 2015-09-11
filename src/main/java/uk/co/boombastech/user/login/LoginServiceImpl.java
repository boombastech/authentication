package uk.co.boombastech.user.login;

import uk.co.boombastech.encryption.EncryptionService;
import uk.co.boombastech.user.Profile;
import uk.co.boombastech.user.UserRepository;
import uk.co.boombastech.user.exceptions.AuthenticationException;
import uk.co.boombastech.user.exceptions.InvalidPasswordLoginException;
import uk.co.boombastech.user.exceptions.UnknownUserException;

import java.util.Optional;

public class LoginServiceImpl implements LoginService {

	private final UserRepository userRepository;
	private final EncryptionService encryptionService;

	public LoginServiceImpl(UserRepository userRepository, EncryptionService encryptionService) {
		this.userRepository = userRepository;
		this.encryptionService = encryptionService;
	}

	@Override
	public Profile login(LoginMessage loginMessage) throws AuthenticationException {
		Optional<Profile> potentialProfile = userRepository.findByEmail(loginMessage.getLoginName());

		Profile profile = potentialProfile.orElseThrow(() -> new UnknownUserException());

		if (encryptionService.encrypt(loginMessage.getPassword()).equals(profile.getPassword())) {
			return profile;
		} else {
			throw new InvalidPasswordLoginException();
		}
	}
}