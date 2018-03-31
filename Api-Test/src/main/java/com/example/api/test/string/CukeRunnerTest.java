package com.example.api.test.string;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
/*@CucumberOptions(features = "src/main/resources/features/StringSetOpnFeature",
        format = {"pretty", "json:target/cucumber.json"},
        glue = { "classpath:com.example.api.test.string" },
        monochrome = true,
        strict = true) */
@CucumberOptions(
        format = {"pretty", "json:target/cucumber.json"},
        features = "src/main/resources/features/StringSetOpnFeature"  //refer to Feature file
)

@Suite.SuiteClasses({
        StepDefinition.class
})

public class CukeRunnerTest {
}
