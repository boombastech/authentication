package uk.co.boombastech.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepositoryStub implements UserRepository {

	private Map<String, Profile> profiles;

	public UserRepositoryStub() {
		profiles = new HashMap<>();
	}

	@Override
	public Optional<Profile> findByEmail(String email) {
		if (profiles.containsKey(email)) {
			return Optional.of(profiles.get(email));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Profile create(Profile profile) {
		profiles.put(profile.getEmail(), profile);

		return profile;
	}

	@Override
	public void update(Profile profile) {
		profiles.put(profile.getEmail(), profile);
	}

	public UserRepositoryStub withProfile(Profile profile) {
		profiles.put(profile.getEmail(), profile);

		return this;
	}

	public UserRepositoryStub withProfile(ProfileBuilder profileBuilder) {
		Profile profile = profileBuilder.build();
		profiles.put(profile.getEmail(), profile);

		return this;
	}
}