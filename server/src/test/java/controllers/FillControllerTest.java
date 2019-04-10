package controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import controllers.coders.EncoderJSON;
import result.RegisterResult;

public class FillControllerTest {
    String test = "blah/blah/blah/blah/username/generations";

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testEncoding() throws Exception {
        FillController ctrl = new FillController();

        ArrayList<String> list =  ctrl.getUriComponents(test);

        System.out.println(list.get(0));
        System.out.println(list.get(1));
    }
}
