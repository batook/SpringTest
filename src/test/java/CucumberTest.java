import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/features", glue = "steps", tags = "@all", plugin = {"pretty", "html:target/cucumber"}, monochrome = true)
public class CucumberTest {
}
