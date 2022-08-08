package b22.spartan.editor;

import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import utilities.SpartanNewBase;
import utilities.SpartanUtil;

import static net.serenitybdd.rest.SerenityRest.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.LinkedHashMap;
import java.util.Map;
@Disabled
@SerenityTest
public class SpartanEditorPostTest extends SpartanNewBase {
//when we need deseriliaxze or seriliaze, you dont need to add seperate dependecny
    //it comes with serenity

    @DisplayName("Editor should be able to Post")
    @Test
    public void postSpartanAsEditor() {
        //create one spartan using util//convertin java to json serilization
/*
                status code is 201
                content type is Json
                success message is A Spartan is Born!
                id is not null
                name is correct
                gender is correct
                phone is correct
                check location header ends with newly generated id
         */
        Map<String, Object> bodyMap = SpartanUtil.getRandomSpartanMap();

        System.out.println("bodyMap = " + bodyMap);

        //send a post request as as editor
        given().accept(ContentType.JSON)
                .and().auth().basic("admin", "admin")
                .contentType(ContentType.JSON) //send as json
                .body(bodyMap)
                .log().body()
                .when().post("/spartans")
                .then().log().all();



        Ensure statusCode = Ensure.that("Status code is 201", s -> s.statusCode(201));
        System.out.println("statusCode = " + statusCode);

        Ensure.that("Content type is Json", c -> c.contentType(ContentType.JSON));

        Ensure.that("Success message is A spartan is Born!", thenPart -> thenPart.body("success", is("A Spartan is Born!")));

        Ensure.that("id is not null", thenPart -> thenPart.body("data.id", notNullValue()));

        Ensure.that("name is correct", thenPart -> thenPart.body("data.name", is(bodyMap.get("name"))));

        Ensure.that("phone is correct", thenPart -> thenPart.body("data.phone", is(bodyMap.get("phone"))));

        Ensure.that("gender is correct",
                thenPart -> thenPart.body("data.gender", is(bodyMap.get("gender"))));
        //check location header ends withe newly generated id
        //get id and save
        String id = lastResponse().jsonPath().getString("data.id");

        Ensure.that("check location header ends with newly generated id", vr -> vr.header("Location", endsWith(id)));


    }

    /*
            we can give name to each execution using name = ""
            and if you want to get index of iteration we can use {index}
            and also if you to include parameter in your test name
            {0} , {1},{2} --> based on the order you provide as argument.
         */

/**we can give name to each execution using name=""
 * and if yoy want to get index of iteration we can use {index}
 * and also if you to include parameter in your test name
 * {0},{1},{2}--> based on the order you provide as ab argument*/


      @ParameterizedTest(name="New Spartan{index}-name: {0}")//gives index number gives the names
      @CsvFileSource(resources="/SpartanData.csv",numLinesToSkip = 1)
    public void postSpartanWithCSV(String name,String gender,long phone){
          System.out.println("name = " + name);
          System.out.println("gender = " + gender);
          System.out.println("phone = " + phone);




          Map<String, Object> bodyMap = new LinkedHashMap<>();
          bodyMap.put("name",name);
          bodyMap.put("gender",gender);
          bodyMap.put("phone",phone);

          System.out.println("bodyMap = " + bodyMap);

          //send a post request as as editor
          given().accept(ContentType.JSON)
                  .and().auth().basic("admin", "admin")
                  .contentType(ContentType.JSON) //send as json
                  .body(bodyMap)
                  .log().body()
                  .when().post("/spartans")
                  .then().log().all();



          Ensure statusCode = Ensure.that("Status code is 201", s -> s.statusCode(201));
          System.out.println("statusCode = " + statusCode);

          Ensure.that("Content type is Json", c -> c.contentType(ContentType.JSON));

          Ensure.that("Success message is A spartan is Born!", thenPart -> thenPart.body("success", is("A Spartan is Born!")));

          Ensure.that("id is not null", thenPart -> thenPart.body("data.id", notNullValue()));

          Ensure.that("name is correct", thenPart -> thenPart.body("data.name", is(bodyMap.get("name"))));

          Ensure.that("phone is correct", thenPart -> thenPart.body("data.phone", is(bodyMap.get("phone"))));

          Ensure.that("gender is correct",
                  thenPart -> thenPart.body("data.gender", is(bodyMap.get("gender"))));
          //check location header ends withe newly generated id
          //get id and save
          String id = lastResponse().jsonPath().getString("data.id");

          Ensure.that("check location header ends with newly generated id", vr -> vr.header("Location", endsWith(id)));




      }



}
