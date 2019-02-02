package steps;

import cucumber.api.java.en.Given;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestStep {
    final static Logger log = LoggerFactory.getLogger(TestStep.class);

    @Given("^Execute test with param \"([^\"]*)\"$")
    public void execute_test_with_param(String arg1) {
        log.info(arg1);
    }

}
