package steps;

import cucumber.api.java.en.Given;

public class TestStep {
    @Given("^Execute test with param \"([^\"]*)\"$")
    public void execute_test_with_param(String arg1) {
        System.out.println(arg1);
    }

}
