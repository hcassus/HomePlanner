package hrp.commons.testcases;

import hrp.HomePlannerApp;
import hrp.commons.configuration.AuditorTestConfig;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {HomePlannerApp.class, AuditorTestConfig.class}, loader = SpringBootContextLoader.class)
public abstract class ControllerTestCase {

  protected final String VALID_USERNAME = System.getenv("VALID_USERNAME");

}
