package com.unrc.app;

import java.util.*;
import com.unrc.app.models.Building;
import com.unrc.app.models.Address;
import com.unrc.app.models.City;
import com.unrc.app.models.Owner;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbmBuilding2 {
	
	public static void createBuilding(int type,String description,int status, int price, String address,String neighborhood ,String city,int postcode,int dni){
		if(type>=1 && type<=6 && status<=2 && status>=1){//chequea si status y type estan en el rango correcto
			Owner o = Owner.findFirst("dni = ?", dni );
			if(o == null){
				System.out.println("Owner does not exist");
				return;
			}
			Address la = Address.findFirst("address = ?", address);//chequea si la direccion ya existe
			if(la == null){//si no existe, crea la direccion y el inmuble a guardar
				Building b = new Building();
				b.set("type",type,"description",description,"status",status,"price",price);
				Address a = new Address();
				a.set("address",address,"neighborhood",neighborhood);
				
				City lc = City.findFirst("postcode = ?",postcode);//chequea si la ciudad ya existe
				if(lc == null){ //si no existe, la crea
					City c = new City();
					c.set("name",city,"postcode",postcode);
					c.saveIt();
					c.add(a);
					a.saveIt();
					a.add(b);
					o.add(b);
					b.saveIt();
				}
				else{
					lc.add(a);
					a.saveIt();
					a.add(b);
					o.add(b);
					b.saveIt();
				}	
			}
			else{
				System.out.println("address already exists");
			}
		}
		else{
			System.out.println("type or status out of bounds");
		}
	}
	
	public static void deleteBuilding(String address){
		Address a = Address.findFirst("address = ?", address);//chequea si la direccion ya existe
		if(a != null){
			Building b = Building.findFirst("address_id = ?",a.get("id"));//chequea que la direccion concuerde con el inmueble
			if(b!=null){
				List<Address> la = new LinkedList<Address>();
				la = Address.where("city_id = ?", a.get("city_id"));
				if(la.size() > 1){//si la ciudad esta usada por otra direccion no la elimina
					a.delete();
					b.delete();
				}
				else{
					City c = City.findFirst("id = ?", a.get("city_id"));
					b.delete();
					a.delete();
					c.delete();					
				}
			}
			else{
				System.out.println("building does not exist");
			}
		}
		else{
			System.out.println("address does not exist");
		}
		
	}
	
	public static void modifyType(int type, String address){
		if(type>=1 && type<=6){
			Address a = Address.findFirst("address = ?", address);
			if(a!=null){
				Building e = Building.findFirst("address_id = ?", a.get("id"));
				if(e!=null){
					e.set("type",type).saveIt();
				}	
			}
			else{
				System.out.println("address does not exist");
			}
		}
		else{
			System.out.println("type out of bounds");
		}
	}
	
	public static void modifyStatus(int status, String address){
		if(status>=1 && status<=2){
			Address a = Address.findFirst("address = ?", address);
			if(a!=null){
				Building b = Building.findFirst("address_id = ?", a.get("id"));
				if(b!=null){
					b.set("status",status).saveIt();
				}	
			}
			else{
				System.out.println("address does not exist");
			}
		}
		else{
			System.out.println("status out of bounds");
		}
	}
	
	public static void modifyDescription(String description, String address){
		Address a = Address.findFirst("address = ?", address);
		if(a!=null){
			Building b = Building.findFirst("address_id = ?", a.get("id"));
			if(b!=null){
				b.set("description",description).saveIt();
			}	
		}
		else{
			System.out.println("address does not exist");
		}
	}
			
	public static void modifyPrice(int price, String address){
		Address a = Address.findFirst("address = ?", address);
		if(a!=null){
			Building b = Building.findFirst("address_id = ?", a.get("id"));
			if(b!=null){
				b.set("price",price).saveIt();
			}	
		}
		else{
			System.out.println("address does not exist");
		}
	}
	
	public static void modifyAddress(String neighborhood, String oldaddress, String newaddress){
		Address na = Address.findFirst("address = ?", newaddress);
		if(na != null){
			System.out.println("new address is already used");
			return;
		}
		Address a = Address.findFirst("address = ?", oldaddress);
		if(a!=null){
			Building b = Building.findFirst("address_id = ?", a.get("id"));
			if(b!=null){
				a.set("neighborhood",neighborhood,"address",newaddress).saveIt();
			}
		}
		else{
			System.out.println("address does not exist");
		}
	}

	public static void modifyOwner(int dni, String address){
		Owner o = Owner.findFirst("dni = ?", dni);
		if(o == null){
			System.out.println("owner does not exist");
			return;
		}
		Address a = Address.findFirst("address = ?", address);
		if(a!=null){
			Building b = Building.findFirst("address_id = ?", a.get("id"));
			if(b!=null){
				b.set("owner_id",o.get("id")).saveIt();
			}	
		}
		else{
			System.out.println("address does not exist");
		}
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
