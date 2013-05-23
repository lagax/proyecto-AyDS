package com.unrc.app;

import com.unrc.app.models.Owner;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;

public class OwnerSpec{

    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "Pantera1.");
        Base.openTransaction();
    }

    @After
    public void after(){
        Base.rollbackTransaction();
        Base.close();
    }

    @Test
    public void shouldValidateMandatoryFields(){

        Owner owner = new Owner();

        //check errors
        the(owner).shouldNotBe("valid");
		the(owner.errors().get("dni")).shouldBeEqual("value is missing");
        the(owner.errors().get("first_name")).shouldBeEqual("value is missing");
        the(owner.errors().get("last_name")).shouldBeEqual("value is missing");

        //set missing values
        owner.set("dni", 1,"first_name", "John", "last_name", "Doe");

        //all is good
        the(owner).shouldBe("valid");
    }

	/*@Test
	public void shouldValidateEmailFormat(){

		Owner owner = new Owner();
		owner.set("dni", 1,"first_name", "John", "last_name", "Doe");

		//check errors
		owner.set("email","mail");
		the(owner).shouldNotBe("valid");

		//set correct values
		owner.set("email","mail@vlestre.com");

		//all is good
		the(owner).shouldBe("valid");
		
	}*/

	@Test
	public void shouldValidateNumericalityOfDni(){

		Owner owner = new Owner();
		owner.set("first_name", "John", "last_name", "Doe");


		//check errors
		owner.set("dni",-1);
		the(owner).shouldNotBe("valid");
		owner.set("dni",3.21);
		the(owner).shouldNotBe("valid");

		//set correct values
		owner.set("dni",4);

		//all is good
        the(owner).shouldBe("valid");
	}

		
}

