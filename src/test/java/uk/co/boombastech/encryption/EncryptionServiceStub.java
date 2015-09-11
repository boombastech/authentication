package uk.co.boombastech.encryption;

public class EncryptionServiceStub implements EncryptionService {
	@Override
	public String encrypt(String input) {
		return input;
	}
}