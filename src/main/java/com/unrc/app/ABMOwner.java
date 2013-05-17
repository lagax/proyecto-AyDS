package com.unrc.app;

import com.unrc.app.models.*;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class ABMOwner {

    public static void createOwner(int dni, String firstName,String lastName, String address, String neighborhood, String city,int postcode, String email)
    {
		Owner p = Owner.findFirst("dni=?",dni);
		if (p==null){ //si no existe este dueño lo creamos 
       	 	p = new Owner();
			p.set("dni",dni);	
        	p.set("first_name", firstName);
       		p.set("last_name", lastName);
			p.set("email",email);
        	Address a = Address.findFirst("address=?",address);
			if (a==null){
				City c = City.findFirst("name=?",city);
				if (c==null){ // si la ciudad no existe la creamos
					c= new City();
					c.set("name",city, "postcode", postcode);
					c.saveIt();
				}
				a = Address.create("address",address,"neighborhood",neighborhood);
				c.add(a);
				a.saveIt();
				a.add(p);
				p.saveIt();
			}
        	else{
				System.out.println("Address "+address+" already exists" );
			}
		}
		else{
			System.out.println("Owner "+dni+" already exists" );
		}
    }

	public static void deleteOwner(int dniOwner)
	{
		Owner o= Owner.findFirst("dni=?",dniOwner);
		if (o!=null){
			RealEstatesOwners.delete("owner_id=?",o.get("id"));
			Owner.delete("id=?",o.get("id"));
		}
		else{
			System.out.println("Owner " + dniOwner + " doesn't exist");
		}
	}
	
	public static void modifyOwnerEmail(int dniOwner, String email)
	{
		Owner o= Owner.findFirst("dni=?",dniOwner);
		if (o!=null){
			Owner.update("email=?","email like ?",email,o.get("email"));
		}
		else{
			System.out.println("Owner " + dniOwner + " doesn't exist");
		}
	}

	public static void modifyAddress(int dniOwner,String address, String neighborhood, String city)
	{
		Address a= Address.findFirst("address=?",address);
		if (a!=null){
			System.out.println("address "+address+" is already used");
			return;
		}
		Owner o = Owner.findFirst("dni=?",dniOwner);
		if (o==null){
			System.out.println("owner "+dniOwner+ " doesn't exist");
			return;
		}
		a.set("neighborhood",neighborhood,"address",address).saveIt();
	}

	public static void modifyCity(String address,String name, int postcode){
		Address a = Address.findFirst("address = ?", address);
		if(a ==null){
			System.out.println("address does not exist");
			return;
		}
		City oc = City.findFirst("id = ?", a.get("city_id"));//obtiene la ciudad de la direccion vieja
		List<Address> la = new LinkedList<Address>();
		la = Address.where("city_id = ?", oc.get("id"));
		if(la.size()==1){
			oc.delete();
		}
		City nc = City.findFirst("postcode = ?",postcode);//chequea si la ciudad ya existe
		if(nc == null){
			nc = new City();
			nc.set("name",name,"postcode",postcode);
			nc.saveIt();
			nc.add(a);
			a.saveIt();			
		}
		else{
			nc.add(a);
			a.saveIt();
		}
	}
}
