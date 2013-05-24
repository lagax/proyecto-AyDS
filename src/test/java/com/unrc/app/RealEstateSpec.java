package com.unrc.app;

import com.unrc.app.models.RealEstate;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;

public class RealEstateSpec{

    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "root");
        Base.openTransaction();
    }

    @After
    public void after(){
        Base.rollbackTransaction();
        Base.close();
    }

    @Test
    public void shouldValidateMandatoryFields(){

        RealEstate realEstate = new RealEstate();

        //check errors
        the(realEstate).shouldNotBe("valid");
		the(realEstate.errors().get("name")).shouldBeEqual("value is missing");

        //set missing values
        realEstate.set("name", "inmo");

        //all is good
        the(realEstate).shouldBe("valid");
    }


	

		
}

