package api_test;

import api.endpoint.UseEndPoints2;
import api.payload.User;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserTests2 {
    Faker faker;
    User userPayload;
    public Logger logger;

    @BeforeClass
    public void setUpData(){
        faker = new Faker();
        userPayload= new User();

        userPayload.setId(faker.idNumber().hashCode());
        userPayload.setUsername(faker.name().username());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        userPayload.setPassword(faker.internet().password(5,10));
        userPayload.setPhone(faker.phoneNumber().cellPhone());
        //log
        logger= LogManager.getLogger(this.getClass());
    }

    @Test(priority = 1)
    public void testPostUser(){
        logger.info("creating the user");
        Response response =UseEndPoints2.createUser(userPayload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
        logger.info("SuccessFully create user");
    }

    @Test(priority = 2)
    public void testGetUserByName(){
        logger.info("getting the user");
        Response response = UseEndPoints2.getUser(this.userPayload.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
        logger.info("SuccessFully getting details user");
    }
    @Test(priority = 3)
    public void testUpdateUserByName(){
        logger.info("user is updating the data");
        //update the data by using payload
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        Response response = UseEndPoints2.updateUser(this.userPayload.getUsername(),userPayload);
        response.then().log().body().statusCode(200);
        logger.info("SuccessFully update details user");


        //checking data update or not
        Response responseAfterUpdate = UseEndPoints2.getUser(this.userPayload.getUsername());
        response.then().log().all();
        Assert.assertEquals(responseAfterUpdate.getStatusCode(),200);

    }
    @Test(priority = 4)
    public void testDeleteUserByName(){
        logger.info("deleting details user");
        Response response =  UseEndPoints2.deleteUser(this.userPayload.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
        logger.info("SuccessFully delete details user");
    }
}
