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
			List<Building> lb = new LinkedList<Building>();
			lb = Building.where("owner_id = ?", o.get("id"));
			for(int i=0;i<lb.size();i++){ //elimina todos los inmuebles del dueño
				Building b = lb.get(i);
				Address a = Address.findFirst("id = ?",b.get("address_id"));
				ABMBuilding.deleteBuilding((String)a.get("address"));
			}
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
			o.set("email",email).saveIt();
		}
		else{
			System.out.println("Owner " + dniOwner + " doesn't exist");
		}
	}

	public static void modifyOwnerFirstName(int dniOwner, String firstName)
	{
		Owner o= Owner.findFirst("dni=?",dniOwner);
		if (o!=null){
			o.set("first_name",firstName).saveIt();
		}
		else{
			System.out.println("Owner " + dniOwner + " doesn't exist");
		}
	}

	public static void modifyOwnerLastName(int dniOwner, String lastName)
	{
		Owner o= Owner.findFirst("dni=?",dniOwner);
		if (o!=null){
			o.set("last_name",lastName).saveIt();
		}
		else{
			System.out.println("Owner " + dniOwner + " doesn't exist");
		}
	}

	public static void modifyAddress(int dni, String neighborhood, String newaddress){
		Owner o = Owner.findFirst("dni = ?", dni);
		if(o == null){
			System.out.println("Owner " + dni + " doesn't exist");
			return;
		}
		Address na = Address.findFirst("address = ?", newaddress);
		if(na != null){
			System.out.println("new address is already used");
			return;
		}
		Address a = Address.findFirst("id = ?", o.get("address_id"));
		a.set("neighborhood",neighborhood,"address",newaddress).saveIt();;
	}

	public static void modifyCity(int dni,String name, int postcode){
		Owner o = Owner.findFirst("dni = ?", dni);
		if(o == null){
			System.out.println("Owner " + dni + " doesn't exist");
			return;
		}
		Address a = Address.findFirst("id = ?", o.get("address_id"));
		City oc = City.findFirst("id = ?", a.get("city_id"));//obtiene la ciudad de la direccion
		List<Address> la = new LinkedList<Address>();
		la = Address.where("city_id = ?", oc.get("id"));
		if(la.size()==1){ //si la ciudad no pertenece a ninga otra direccion, la elimina
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
