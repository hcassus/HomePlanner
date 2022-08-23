package hrp.commons.testcases;

import hrp.HomePlannerApp;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {HomePlannerApp.class}, loader = SpringBootContextLoader.class)
public abstract class ControllerTestCase {

  protected final String VALID_USERNAME = System.getenv("VALID_USERNAME");

}
