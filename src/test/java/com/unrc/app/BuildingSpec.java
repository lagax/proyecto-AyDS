package com.unrc.app;

import com.unrc.app.models.Building;
import com.unrc.app.models.Owner;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.javalite.test.jspec.JSpec.the;

public class BuildingSpec {

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

        Building building = new Building();

        //check errors
        the(building).shouldNotBe("valid");
		the(building.errors().get("type")).shouldBeEqual("value is missing");
        the(building.errors().get("address_id")).shouldBeEqual("value is missing");
        the(building.errors().get("status")).shouldBeEqual("value is missing");

        //set missing values
        building.set("type", 2,"address_id", 1,"status",1);

        //all is good
        the(building).shouldBe("valid");
    }
    
    @Test
    public void shouldValidateTypeRange(){
    	Building building = new Building();
    	building.set("address_id",1,"status",1);
    	
    	//check errors
    	building.set("type", 0);
    	the(building).shouldNotBe("valid");
    	building.set("type",7);
    	the(building).shouldNotBe("valid");
    	
    	//set correct values
    	building.set("type",1);

    	//all is good
    	the(building).shouldBe("valid");
    }
    
    @Test
    public void shouldValidateStatusRange(){
    	Building building = new Building();
    	building.set("address_id",1,"type",1);
    	
    	//check errors
    	building.set("status", 0);
    	the(building).shouldNotBe("valid");
    	building.set("status",3);
    	the(building).shouldNotBe("valid");
    	
    	//set correct values
    	building.set("status",1);

    	//all is good
    	the(building).shouldBe("valid");
    }
}
