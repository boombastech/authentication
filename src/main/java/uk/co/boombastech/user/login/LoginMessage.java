package uk.co.boombastech.user.login;

public class LoginMessage {

	private final String loginName;
	private final String password;
	private final boolean remember;

	public LoginMessage(String loginName, String password, boolean remember) {
		this.loginName = loginName;
		this.password = password;
		this.remember = remember;
	}

	public String getLoginName() {
		return loginName;
	}

	public String getPassword() {
		return password;
	}

	public boolean isRemember() {
		return remember;
	}
}