
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import io.restassured.response.ValidatableResponse;


public class AllTests {

    @Test
    public void testA() {
        given().        
        when().
            get("https://restcountries.com/v3.1/name/peru").
        then().
             body("[0].name.common", equalTo("Peru"));
             //body("[0].name.common", equalTo(Arrays.asList("Peru")));
    }

    //show how to transfer a response value to a new request. 
    @Test
    public void testRegions() {
        //extract region name for Peru (should be Americas)
        String val = given().        
            when().
                get("https://restcountries.com/v3.1/name/peru").
            then().
                body("[0].name.common", equalTo("Peru")).
                extract().path("[0].region");
        
        assertEquals("Americas", val);

        //then get all countries in Americas and verify that Peru is there
        given().        
            when().
                get("https://restcountries.com/v3.1/region/" + val).
            then().
                body("name.common", hasItems("Peru"));

    }
}
