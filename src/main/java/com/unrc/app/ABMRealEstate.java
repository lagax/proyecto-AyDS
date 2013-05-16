package com.unrc.app;

import com.unrc.app.models.User;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ABMRealEstate {

    public static void createRealEstate(String name, Address address, Integer tel, String email, String website, List<Owner> owners)
    {
        RealEstate r = new RealEstate();
        r.set("name", name);
        r.set("email", email);
        r.set("web_site", website);
		r.set("phone_number", tel);
        r.add(address);
		while (owners.get(0)!=null){
			Owner o=owners.remove(0);
			r.add(o);
		}
        r.saveIt();
    }

	public static void removeRealEstate(RealEstate r)
	{
		RealEstatesOwners.delete("realEstate_id=?",r.get("id"));
		RealEstate.delete("id=?",r.get("id"));
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
