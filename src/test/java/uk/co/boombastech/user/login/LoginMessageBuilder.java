package uk.co.boombastech.user.login;

public class LoginMessageBuilder {
	private String loginName;
	private String password;
	private boolean remember;

	public LoginMessageBuilder withLoginName(String loginName) {
		this.loginName = loginName;
		return this;
	}

	public LoginMessageBuilder withPassword(String password) {
		this.password = password;
		return this;
	}

	public LoginMessageBuilder withRemember(boolean remember) {
		this.remember = remember;
		return this;
	}

	public LoginMessage build() {
		return new LoginMessage(loginName, password, remember);
	}

	public static LoginMessageBuilder newLoginMessage() {
		return new LoginMessageBuilder();
	}
}