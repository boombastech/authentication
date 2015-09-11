package uk.co.boombastech.user.register;

public class RegisterMessageBuilder {
	private String email;
	private String password;
	private String confirmPassword;

	public RegisterMessageBuilder withEmail(String email) {
		this.email = email;
		return this;
	}

	public RegisterMessageBuilder withPassword(String password) {
		this.password = password;
		return this;
	}

	public RegisterMessageBuilder withConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
		return this;
	}

	public RegistrationMessage build() {
		return new RegistrationMessage(email, password, confirmPassword);
	}

	public static RegisterMessageBuilder newRegisterMessage() {
		return new RegisterMessageBuilder();
	}
}