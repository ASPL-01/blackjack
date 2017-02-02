package com.galvanize;

import com.galvanize.controllers.GameControllerTest;
import com.galvanize.controllers.UserControllerTest;
import com.galvanize.services.GameServiceTest;
import com.galvanize.services.UserServiceTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(Suite.class)
@SpringBootTest
@Suite.SuiteClasses({
		GameServiceTest.class,
		UserServiceTest.class,
		UserControllerTest.class,
		GameControllerTest.class
})
public class BlackjackApplicationTests {

	@Test
	public void contextLoads() {
	}

}
