package uk.co.boombastech.user.register;

import uk.co.boombastech.UniqueStringGenerator;
import uk.co.boombastech.email.Email;
import uk.co.boombastech.email.EmailService;
import uk.co.boombastech.encryption.EncryptionService;
import uk.co.boombastech.user.Profile;
import uk.co.boombastech.user.UserRepository;
import uk.co.boombastech.user.exceptions.AuthenticationException;
import uk.co.boombastech.user.exceptions.NonMatchingPasswordsException;
import uk.co.boombastech.user.exceptions.UserAlreadyExistsException;

public class RegistrationServiceImpl implements RegistrationService {

	private final UserRepository userRepository;
	private final EncryptionService encryptionService;
	private final UniqueStringGenerator uniqueStringGenerator;
	private final EmailService emailService;

	public RegistrationServiceImpl(UserRepository userRepository, EncryptionService encryptionService, UniqueStringGenerator uniqueStringGenerator, EmailService emailService) {
		this.userRepository = userRepository;
		this.encryptionService = encryptionService;
		this.uniqueStringGenerator = uniqueStringGenerator;
		this.emailService = emailService;
	}

	@Override
	public Profile register(RegistrationMessage registrationMessage) throws AuthenticationException {
		if (emailAlreadyExists(registrationMessage)) {
			throw new UserAlreadyExistsException();
		}

		if (nonMatchingPasswords(registrationMessage)) {
			throw new NonMatchingPasswordsException();
		}

		Profile profile = new Profile(uniqueStringGenerator.getUniqueString(), registrationMessage.getEmail(), encryptionService.encrypt(registrationMessage.getPassword()));

		profile = userRepository.create(profile);

		Email registrationEmail = new Email(profile.getEmail(), "emailFromPropertiesFile", "Welcome", "You're registered!");
		emailService.send(registrationEmail);

		return profile;
	}

	private boolean nonMatchingPasswords(RegistrationMessage registrationMessage) {
		return !registrationMessage.getPassword().equals(registrationMessage.getConfirmPassword());
	}

	private boolean emailAlreadyExists(RegistrationMessage registrationMessage) {
		return userRepository.findByEmail(registrationMessage.getEmail()).isPresent();
	}
}