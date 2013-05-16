package com.unrc.app;

import com.unrc.app.models.User;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ABMOwner {

    public static void createOwner(Integer dni, String firstName,String lastName, Address address, String email, List<RealEstate> realEstates)
    {
        Owner p = new Owner();
		p.set("dni",dni);	
        p.set("first_name", firstName);
        p.set("last_name", lastName);
        p.add(address);
		while (realEstates.get(0)!=null){
			RealEstate r = realEstates.remove(0);
			p.add(r);
		}
        p.saveIt();
    }

	public static void removeOwner(Owner p)
	{
		RealEstatesOwners.delete("owner_id=?",p.get("id"));
		Owner.delete("id=?",p.get("id"));
	}
	
	public static void updateOwnerEmail(RealEstate r, String email)
	{
		RealEstate.update("email=?","email like ?",email,p.get("email"));
	}
}