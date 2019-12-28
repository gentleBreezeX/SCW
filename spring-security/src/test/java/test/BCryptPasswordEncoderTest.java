package test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderTest {

	@Test
	public void test() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		//$2a$10$8jWDZL0y9JHQ1XLuzSsz0eGF969ZzFMuBQDM8dZEhwrBSwmH1Putu
		System.out.println(encoder.encode("123456"));
		
	}

}
