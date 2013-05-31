package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Building extends Model{
	static{
		validatePresenceOf("type","address_id","status");
		validateRange("type", 1, 6);
		validateRange("status", 1, 2);
	}

		public String toString(){	
		return "descripcion: " + this.get("description");
	}
}

