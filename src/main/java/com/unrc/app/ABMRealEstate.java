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
				
			while (!owners.isEmpty()){ //chequeamos cada dueño, si no existe no lo agregamos a la relacion
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

	public static void deleteRealEstate(String nameRE)
	{
		RealEstate r = RealEstate.findFirst("name=?",nameRE);
		if (r!=null){
			RealEstatesOwners.delete("real_estate_id=?",r.get("id"));
			RealEstate.delete("id=?",r.get("id"));
		}
		else{
			System.out.println("Real Estate "+nameRE+" doesn't exist");
		}
	}

	public static void modifyName(String oldName, String newName)
	{
		RealEstate r = RealEstate.findFirst("name=?",oldName);
		if (r!=null){
			r.set("name",newName);
			r.saveIt();
		}
		else{
			System.out.println(" Real Estate "+oldName+" doesn't exist");
		}
	}

	public static void modifyTel(String name, int newTel)
	{
		RealEstate r = RealEstate.findFirst("name=?",name);
		if (r!=null){
			r.set("phone_number",newTel);
			r.saveIt();
		}
		else{
			System.out.println(" Real Estate "+name+" doesn't exist");
		}
	}
	
	public static void modifyEmail(String name, String email)
	{
		RealEstate r = RealEstate.findFirst("name=?",name);
		if (r!=null){
			r.set("email",email);
			r.saveIt();
		}
		else{
			System.out.println(" Real Estate "+name+" doesn't exist");
		}
	}

	public static void modifyWebsite(String name, String website)
	{
		RealEstate r = RealEstate.findFirst("name=?",name);
		if (r!=null){
			r.set("web_site",website);
			r.saveIt();
		}
		else{
			System.out.println(" Real Estate "+name+" doesn't exist");
		}
	}

	public static void modifyCity(String nameRE,String name, int postcode){
		RealEstate r = RealEstate.findFirst("name = ?", nameRE);
		if(r ==null){
			System.out.println("Real estate does not exist");
			return;
		}
		Address a = Address.findFirst("id=?",r.get("address_id"));
		City oc = City.findFirst("id = ?", a.get("city_id"));//obtiene la ciudad de la direccion vieja
		List<Address> la = new LinkedList<Address>();
		la = Address.where("city_id = ?", oc.get("id"));
		if(la.size()==1){//si la ciudad no pertenece a ninga otra direccion, la elimina
			oc.delete();
		}
		City nc = City.findFirst("postcode = ?",postcode);//chequea si la ciudad ya existe
		if(nc == null){
			nc = new City();
			nc.set("name",name,"postcode",postcode);
			nc.saveIt();
		}
		nc.add(a);
		a.saveIt();			
	}

	public static void modifyAddress(String name, String neighborhood, String address){
		{
		Address a= Address.findFirst("address=?",address);
		if (a!=null){
			System.out.println("address "+address+" is already used");
			return;
		}
		RealEstate r = RealEstate.findFirst("name=?",name);
		if (r==null){
			System.out.println("real estate "+name+ " doesn't exist");
			return;
		}
		a.set("neighborhood",neighborhood,"address",address).saveIt();
	}
	}

	
}
