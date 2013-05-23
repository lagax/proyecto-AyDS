package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Owner extends Model {
  static{
      validatePresenceOf("dni", "first_name", "last_name");
	  /*if ("email"!=null){
	  	validateEmailOf("email");
	  }*/
	  validateNumericalityOf("dni").greaterThan(0).onlyInteger() ;
  }
}
