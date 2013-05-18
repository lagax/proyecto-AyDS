package com.unrc.app;


import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class Inmo {
    public static void main( String[] args )
    {
		Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "root");

		ABMOwner.createOwner(32492642,"john","doe","ksadad 222","asnmsal", "riocuarto",5800, "jnskks@ndn.com");
     	ABMBuilding.createBuilding(5,"vlestre",1,4000,"blasbareba 12","poltrof","riocuarto",5800,32492642);
		ABMOwner.createOwner(1313,"jake","dose","ssddd 662","asnmsal", "riocuarto",5800, "jnsgte@ndn.com");
		List<Integer> ownerList = new LinkedList<Integer>();
		ownerList.add(1213);
		ownerList.add(1313);
		ownerList.add(3413);
		ABMRealEstate.createRealEstate("inmo1","asss 332","sdaka","riocuarto",5800,4635465,"inmo1@gamil,com","www.inmo1.com",ownerList);
		ABMRealEstate.modifyEmail("inmo1","inmoooooo@masjs.com");
		ABMBuilding.deleteBuilding("blasbareba 12");
		ABMOwner.deleteOwner(32492642);
		ABMOwner.modifyCity(1313,"cordobaCapital",800000);
		ABMRealEstate.modifyCity("inmo1","BUENOSAIRES",2388);	
		
		Base.close();	
        
        System.out.println( "Hello World!" );
    }	
}

