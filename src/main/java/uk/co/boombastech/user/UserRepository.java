package uk.co.boombastech.user;

import java.util.Optional;

public interface UserRepository {
	Optional<Profile> findByEmail(String email);

	Profile create(Profile profile);

	void update(Profile profile);
}