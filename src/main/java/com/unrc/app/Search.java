package com.unrc.app;	

import java.util.*;
import com.unrc.app.models.*;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Search {
	
	public static List<Building> buildings(int type, int status, int minPrice, int maxPrice, String city, int dni, String realEstate){
		List<Building> lb = new LinkedList<Building>();
		lb = Building.findAll();
		lb = searchBy("type",type,lb,1);
		lb = searchBy("status",status,lb,1);
		lb = searchBy("price",maxPrice,lb,2);
		lb = searchBy("price",minPrice,lb,3);
		lb = searchByCity(city,lb);
	
		Owner o = Owner.findFirst("dni = ?", dni);
		if(o != null){
			searchBy("owner_id",o.getInteger("id"),lb,1);
		}
		 lb = searchByRealEstate(realEstate,lb);
		return lb;
	}
	
	private static List<Building> searchBy(String param, int value ,List<Building> lb,int cmp){
		if(value >= 0){
			Iterator<Building> iter = lb.iterator();
			while(iter.hasNext()){
				Building b = iter.next();
				switch(cmp){
					case 1:
						if(b.getInteger(param) != value){
							iter.remove();
						}	
					break;
					case 2:
						if(b.getInteger(param) <= value){
							iter.remove();
						}	
					break;
					default:
						if(b.getInteger(param) >= value){
							iter.remove();
						}
				}
			}
		}
		return lb;
	}
	
	private static List<Building> searchByRealEstate(String name, List<Building> lb){
		RealEstate r = RealEstate.findFirst("name = ?",name);
		if(r != null){
			Iterator<Building> iter = lb.iterator();
			while(iter.hasNext()){
				Building b = iter.next();
				RealEstatesOwners r1 = RealEstatesOwners.findFirst("owner_id = ? and real_estate_id = ?", b.getInteger("owner_id"),r.getInteger("id"));
				if(r1 == null){
					iter.remove();
				}
			}
		}	
		return lb;
	}
	
	private static List<Building> searchByCity(String city,List<Building> lb){
		if(city != null){
			City c = City.findFirst("name = ?", city);
			if(c != null){
				Iterator<Building> iter = lb.iterator();
				while(iter.hasNext()){
					Building b = iter.next();
					Address a = Address.findFirst("id = ?", b.getInteger("address_id"));
					if(a.getInteger("city_id") != c.getInteger("id")){
						iter.remove();
					}
				}
			}
		}
		return lb;
	}

}
