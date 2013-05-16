package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Owner extends Model {
  static{
<<<<<<< HEAD
      validatePresenceOf("dni", "firs_name","last_name");
=======
      validatePresenceOf("name");
>>>>>>> 1b16bc2d2a0edb965ec856b92eba2e6512cbeb15
  }
}
