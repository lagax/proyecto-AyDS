package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Owner extends Model {
  static{
      validatePresenceOf("dni", "firs_name","last_name");
  }
}
