package b22.spartan.admin;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static net.serenitybdd.rest.SerenityRest.given;

@SerenityTest
public class SpartanAdminGetTest {


    @BeforeAll
    public static void init() {
        baseURI = "http://52.90.56.79:7000";
    }


    @Test
    public void getAllSpartan() {

        given().accept(ContentType.JSON)
                .and()
                .auth().basic("admin", "admin")
                .when()
                .get("/api/spartans")
                .then().statusCode(200)
                .and().contentType(ContentType.JSON);

    }

    @Test
    public void getOneSpartan() {

        given().accept(ContentType.JSON)
                .and()
                .auth().basic("admin", "admin")
                .pathParam("id", 15)
                .when()
                .get("/api/spartans/{id}");

        //if you send a request using serenityRest, the response object
        //can be onbtained from the method called lastResponse() without being saved seperately
        //same with Response response object
        System.out.println("StatusCode = " + lastResponse().statusCode());

        //print id
        //instead of response.path , we use lastRespoınse.path()
        System.out.println("lastResponse().path(\"id\") = " + lastResponse().path("id"));

        //use jsonpath wiyj last response and get the name
        String name = lastResponse().jsonPath().getString("name");

        System.out.println("name = " + name);


    }

    @Test
    public void getOneSpartanAssertion() {

        given().accept(ContentType.JSON)
                .and()
                .auth().basic("admin", "admin")
                .pathParam("id", 15)
                .when()
                .get("/api/spartans/{id}");

        //Serenity way of assertions
  //variable name and assertion
        Ensure.that("Status code is 200",validatableResponse -> validatableResponse.statusCode(200));

        Ensure.that("Content-type is JSON",vRes->vRes.contentType(ContentType.JSON));
        //we can use hamcrest
        Ensure.that("Id is 15",vRes->vRes.body("id",is(15)));




    }
}
