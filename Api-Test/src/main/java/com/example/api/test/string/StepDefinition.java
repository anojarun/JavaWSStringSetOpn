package com.example.api.test.string;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.runner.RunWith;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import static org.junit.Assert.*;

@ContextConfiguration(classes = SettingApplication.class, loader = SpringApplicationContextLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class StepDefinition implements En{

    @Autowired
    private StringHelp stringHelp;

    public StepDefinition(){
        System.out.println("In StepDefinition");


        When("^the set of string (.*) , (.*)  uplloaded successfully$", (String arg0, String arg1) -> {
            System.out.println("arg0: " + arg0 + "arg1: " + arg1);
            stringHelp.postStrings(arg0,arg1);
        });
        Then("^successful response returned and Id is created$", () -> {
           stringHelp.validateSuccessfulResponse();
        });
        When("^the set of string is being  fetched$", () -> {
            stringHelp.getListStrings();
        });
        Then("^successful response returned$", () -> {
            stringHelp.validateSuccessfulResponse();
        });
        When("^the set of string is being  fetched in a given set$", () -> {
            stringHelp.getGivenStrings();
        });
        When("^Delete a given set of strings by giving its id$", () -> {
            stringHelp.deleteGivenStrings();
        });
    }
}
