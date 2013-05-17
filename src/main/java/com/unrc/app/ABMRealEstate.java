package com.unrc.app;

import com.unrc.app.models.*;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class ABMRealEstate {

    public static void createRealEstate(String name,String address, String neighborhood, String city,int postcode, int tel, String email, String 	website, List<Integer> owners)
    {
		RealEstate r = RealEstate.findFirst("name=?",name);
		if (r==null){ //si no existe esta inmobiliaria la creamos
        	r = new RealEstate();
        	r.set("name", name);
        	r.set("email", email);
        	r.set("web_site", website);
			r.set("phone_number", tel);
			Address a = Address.findFirst("address=?",address);
			if (a!=null){ 
        		a.add(r);
			}
			else{ // si la direccion no existe la creamos
				City c = City.findFirst("name=?",city);
				if (c==null){ // si la ciudad no existe la creamos
					c= new City();
					c.set("name",city, "postcode", postcode);
					c.saveIt();
				}
				a = Address.create("address",address,"neighborhood",neighborhood);
				c.add(a);
				a.saveIt();
				a.add(r);
			}
				
			while (owners.get(0)!=null){ //chequeamos cada dueño, si no existe no lo agregamos a la relacion
				int dniOwner = owners.remove(0);
				Owner o=Owner.findFirst("dni=?",dniOwner);
				if (o!=null){
					r.add(o);
				}
				else{
					System.out.println("Owner "+ dniOwner + " does not exist");
				}
			}
        	r.saveIt();
		}
		else{
			System.out.println("RealEstate" + name + " already exists");
		}
    }

	public static void removeRealEstate(String nameRE)
	{
		RealEstate r = RealEstate.findFirst("name=?",nameRE);
		if (r!=null){
			RealEstatesOwners.delete("realEstate_id=?",r.get("id"));
			RealEstate.delete("id=?",r.get("id"));
		}
		else{
			System.out.println("Real Estate "+nameRE+" doesn't exist");
		}
	}

	public static void updateRealEstateName(RealEstate r, String name)
	{
		RealEstate.update("name=?","name like ?",name,r.get("name"));
	}

	public static void updateRealEstateTel(RealEstate r, Integer tel)
	{
		RealEstate.update("phone_number=?","phone_number like ?",tel,r.get("phone_number"));
	}
	
	public static void updateRealEstateEmail(RealEstate r, String email)
	{
		RealEstate.update("email=?","email like ?",email,r.get("email"));
	}

	public static void updateRealEstateWebsite(RealEstate r, String website)
	{
		RealEstate.update("web_site=?","web_site like ?",website,r.get("web_site"));
	}
	
}
