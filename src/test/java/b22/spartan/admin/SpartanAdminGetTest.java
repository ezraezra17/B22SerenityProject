package b22.spartan.admin;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
@SerenityTest
public class SpartanAdminGetTest {


    @BeforeAll
    public static void init(){
         baseURI ="http://52.90.56.79:7000";
    }


    @Test
    public void getAllSpartan(){

        given().accept(ContentType.JSON)
                .and()
                .auth().basic("admin","admin")
                .when()
                .get("/api/spartans")
                .then().statusCode(200)
                .and().contentType(ContentType.JSON);

    }
}
