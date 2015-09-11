package uk.co.boombastech.user.resetpassword;

public class ResetPasswordMessage {
	private final String email;
	private final String password;
	private final String confirmPassword;
	private final String forgottenPasswordCode;

	public ResetPasswordMessage(String email, String password, String confirmPassword, String forgottenPasswordCode) {
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.forgottenPasswordCode = forgottenPasswordCode;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public String getForgottenPasswordCode() {
		return forgottenPasswordCode;
	}
}