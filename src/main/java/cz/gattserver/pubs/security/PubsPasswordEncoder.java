package cz.gattserver.pubs.security;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Vytváří SHA1 encoder
 */
@Component("pubsPasswordEncoder")
public class PubsPasswordEncoder extends ShaPasswordEncoder {

	private final String SALT;

	public PubsPasswordEncoder() {
		// TODO ... ani tahle výchozí konfigurace by asi neměla být takhle tady
		// napevno dána
		this(1, "a&~54%|$gre564a45sa54sđĐ[#54");
	}

	public PubsPasswordEncoder(int strength, String salt) {
		super(strength);
		SALT = salt;
	}

	@Override
	public String encodePassword(String rawPass, Object salt) {
		return super.encodePassword(rawPass, salt == null ? SALT : salt);
	}

}
