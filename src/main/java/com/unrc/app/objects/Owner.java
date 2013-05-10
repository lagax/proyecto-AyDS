package com.unrc.app.models;

import java.util.*;

public class Owner {

	private String name;
	private Address address; //(ciudad, barrio, calle)
	private String email;
	private List<RealEstate> realEstates;
	
	public void setRealEstates(List<RealEstate> i){
		realEstates = i;
	}
	
	public void setName(String n){
		name = n;
	}

	public void setEmail(String e){
		email = e;
	}

	public void setAddress(Address d){
		address = d;
	}

	public String getName(){
		return name;
	}

	public Address getAddress(){
		return address;
	}

	public String getEmail(){
		return email;
	}

	public List<RealEstate> getRealEstates(){
		return realEstates;
	}
}
