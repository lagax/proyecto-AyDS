package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Building extends Model{
	static{
		validatePresenceOf("type","address_id","status");
		validateRange("type", 1, 6);
		validateRange("status", 1, 2);
	}

	public String toString(){	
		Address a = Address.findFirst("id= ?", this.get("address_id"));
		City c = City.findFirst("id = ?", a.get("city_id") );
		return " descripcion: " + this.get("description") +", price: "+this.get("price")+ ", type: "+ this.get("type")+ ", status: " +this.get("status") 
				+ ", address: "+ a.get("address") + ", city: " + c.get("name") ;
	}
}

