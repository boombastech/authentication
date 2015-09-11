package uk.co.boombastech.user;

public class ProfileBuilder {
	private String id;
	private String email;
	private String password;

	public static ProfileBuilder newProfile() {
		return new ProfileBuilder();
	}

	public ProfileBuilder withId(String id) {
		this.id = id;
		return this;
	}

	public ProfileBuilder withEmail(String email) {
		this.email = email;
		return this;
	}

	public ProfileBuilder withPassword(String password) {
		this.password = password;
		return this;
	}

	public Profile build() {
		return new Profile(id, email, password);
	}
}