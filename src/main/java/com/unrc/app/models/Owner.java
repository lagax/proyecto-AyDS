package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Owner extends Model {
  static{
      validatePresenceOf("dni", "first_name", "last_name");
	  validateNumericalityOf("dni").greaterThan(0).onlyInteger() ;
  }
}
