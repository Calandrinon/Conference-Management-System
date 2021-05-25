package com.imps.cms;

import io.github.eliux.mega.Mega;
import io.github.eliux.mega.MegaSession;
import io.github.eliux.mega.auth.MegaAuthCredentials;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CmsApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void test_JavaMegaApi_AddFileToMegaCloud() {
		System.out.println(System.getenv("MEGA_PWD"));
		System.out.println(System.getenv("MEGA_EMAIL"));

		// MegaSession sessionMega = Mega.login(MegaAuthCredentials.createFromEnvVariables());
		MegaSession sessionMega = Mega.init();
		sessionMega.uploadFile("duHast.txt", "megacmd4j/")
			.createRemotePathIfNotPresent()
			.run();
	}

}
