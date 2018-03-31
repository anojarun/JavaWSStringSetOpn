package com.example.api.test.string;


import com.jayway.restassured.response.ValidatableResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Fail.fail;

@Component
public class StringHelp {


    @Value("${string.server.url}")
    private String stringServerUrl;

    private ValidatableResponse response;

    public StringHelp() {
        System.out.println("In StringHelp constr");
    }



    public void postStrings(String string1, String string2) {
        String paramters = "?strings="+string1+"&strings="+string2;
        System.out.println("In postStrings"+ paramters);
        System.out.println(String.format("%s/JavaWSStringSetOpn/rest/set/upload%s", stringServerUrl,paramters));
        response = given()
                .contentType("application/json")
                .when().log().all()
                .post(String.format("%s/JavaWSStringSetOpn/rest/set/upload%s", stringServerUrl,paramters))
                .then().log().all();
       // String id =response.extract().body().asString();

    }

    public String getID(){
        String id = response.extract().body().asString();

        return id;
    }

    public void getListStrings( ) {
        response = given()
                .contentType("application/json")
                .when().log().all()
                .get(String.format("%s/JavaWSStringSetOpn/rest/set/", stringServerUrl))
                .then().log().all();
    }

    public void getGivenStrings( ) {
        String id = getID();
        System.out.println("id getgiven sttings"+ id);
        response = given()
                .contentType("application/json")
                .when().log().all()
                .get(String.format("%s/JavaWSStringSetOpn/rest/set/%s", stringServerUrl,id))
                .then().log().all();
    }

    public void deleteGivenStrings( ) {
        String id = getID();
        System.out.println("id getgiven sttings"+ id);
        response = given()
                .contentType("application/json")
                .when().log().all()
                .get(String.format("%s/JavaWSStringSetOpn/rest/set/%s/delete", stringServerUrl,id))
                .then().log().all();
    }

    public void validateSuccessfulResponse() {

        response.statusCode(HttpURLConnection.HTTP_OK);


    }
}
