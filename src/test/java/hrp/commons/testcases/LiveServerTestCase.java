package hrp.commons.testcases;

import hrp.HomePlannerApp;
import hrp.commons.configuration.AuditorTestConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {HomePlannerApp.class, AuditorTestConfig.class}, loader = SpringBootContextLoader.class)
public abstract class LiveServerTestCase {

}
