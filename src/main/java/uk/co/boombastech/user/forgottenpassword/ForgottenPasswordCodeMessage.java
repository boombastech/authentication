package uk.co.boombastech.user.forgottenpassword;

public class ForgottenPasswordCodeMessage {
	private final String email;

	public ForgottenPasswordCodeMessage(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
}