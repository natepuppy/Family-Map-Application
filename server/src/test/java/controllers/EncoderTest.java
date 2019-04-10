package controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controllers.coders.EncoderJSON;
import result.RegisterResult;

import static org.junit.Assert.*;

public class EncoderTest {
    RegisterResult result = new RegisterResult();

    @Before
    public void setUp() throws Exception {
//        result.setUserName("userName");
//        result.setAuthToken("authToken");
//        result.setPersonID("personID");
        result.setErrorMessage("This is an error message");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testEncoding() throws Exception {
        String x = EncoderJSON.encodeRegister(result);
        System.out.println(x);

    }
}
