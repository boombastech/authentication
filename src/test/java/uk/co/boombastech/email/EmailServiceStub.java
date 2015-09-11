package uk.co.boombastech.email;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

public class EmailServiceStub implements EmailService {

	private final List<Email> sentEmails;

	public EmailServiceStub() {
		sentEmails = newArrayList();
	}

	@Override
	public void send(Email email) {
		sentEmails.add(email);
	}

	public List<Email> getEmailsSentTo(String toAddress) {
		return sentEmails.stream().filter(email -> email.getToEmail().equalsIgnoreCase(toAddress)).collect(Collectors.toList());
	}
}