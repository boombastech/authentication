package uk.co.boombastech.email;

public class Email {

	private final String toEmail;
	private final String fromEmail;
	private final String subject;
	private final String message;

	public Email(String toEmail, String fromEmail, String subject, String message) {
		this.toEmail = toEmail;
		this.fromEmail = fromEmail;
		this.subject = subject;
		this.message = message;
	}

	public String getToEmail() {
		return toEmail;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public String getSubject() {
		return subject;
	}

	public String getMessage() {
		return message;
	}
}