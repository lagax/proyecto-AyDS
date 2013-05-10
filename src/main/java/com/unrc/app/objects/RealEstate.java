package com.unrc.app.models;

import java.util.*;
import org.javalite.activejdbc.Model;

public class RealEstate extends Model {
	
	private String name;
	private String tel;
	private String email;
	private String website;
	private Address address;
	private List<Owner> owners;

	public String getName(){
		return name;
	}

	public void setName(String n){
		name=n;
	}

	public String getTel(){
		return tel;
	}

	public void setTel(String t){
		tel=t;
	}

	public String getEmail(){
		return email;
	}

	public void setEmail(String e){
		email=e;
	}

	public String getWebsite(){
		return website;
	}

	public void setWebsite(String w){
		website=w;
	}

	public List<Owner> getOwners(){
		return owners;
	}

	public void setOwners(List<Owner> o){
		owners=o;
	}
	public String toString() {
		return "Real Estate [name=" + name + ", telephone=" + tel
				+ ", e-mail=" + email + ", website=" + website
				+ ", address=" + address + "]";
	}
}
