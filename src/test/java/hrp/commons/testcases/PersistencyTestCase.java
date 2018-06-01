package hrp.commons.testcases;

import hrp.HomePlannerApp;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = HomePlannerApp.class, loader = SpringBootContextLoader.class)
public abstract class PersistencyTestCase {

}
