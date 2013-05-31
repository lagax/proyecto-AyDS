
package com.unrc.app;


import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static spark.Spark.*;
import spark.*;

import com.unrc.app.models.Building;

import java.util.*;

public class Inmo {


    public static void main( String[] args )
    {
		Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "root");
		ABMOwner.createOwner(32492642,"john","doe","ksadad 222","asnmsal", "riocuarto",5800, "jnskks@ndn.com");
		ABMOwner.createOwner(90,"brad","paaras","ssddd 662","asnmsal", "riocuarto",5800, "jnsgte@ndn.com");
     	ABMBuilding.createBuilding(2,"vlestre",1,4000,"blasbareba 123","poltrof","riocuarto",5800,90);
     	ABMBuilding.createBuilding(2,"vlestre",2,3000,"blasbareba 124","poltrof","irak",5800,90);
     	ABMBuilding.createBuilding(2,"vlestre",2,100000,"blasbareba 128","poltrof","irak",5800,90);
		Base.close();

		get(new Route("/hello") {
         @Override
        
         public Object handle(Request request, Response response) {
			Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "root");
            String title = "<h1> Inmobiliaria </h1>";

			String menu = "<ul> <li> <a href=\"/buildings\"> Inmuebles </a> </li> </ul>";
			String menu2 = "<ul> <li> <a href=\"/owners\"> Dueños </a> </li> </ul>";
			String menu3 = "<ul> <li> <a href=\"/realEstates\"> Inmobiliarias </a> </li> </ul>";

            String footer = "<p> created by: lagax</p>";
			Base.close();
            return title + menu + menu2 + menu3 + footer;
         };
		 });

		get(new Route("/buildings") { //muestra todos los edificios
	         @Override
	         public Object handle(Request request, Response response) {
	        	Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "root");
	        	List<Building> inmuebles=Building.findAll();
	        	String pantalla="";
	        	Iterator<Building> iter=inmuebles.iterator();
	        	while(iter.hasNext()){
	        	 Building o=iter.next();
	        	 pantalla=pantalla+o.toString()+("\n");
	        	}
	        	Base.close();
	        	return  pantalla;
	         }
	      });

		
         

		//ABMOwner.createOwner(32492642,"john","doe","ksadad 222","asnmsal", "riocuarto",5800, "jnskks@ndn.com");
		//ABMOwner.createOwner(90,"brad","paaras","ssddd 662","asnmsal", "riocuarto",5800, "jnsgte@ndn.com");
     	//ABMBuilding.createBuilding(2,"vlestre",1,4000,"blasbareba 123","poltrof","riocuarto",5800,90);
     	//ABMBuilding.createBuilding(2,"vlestre",2,3000,"blasbareba 124","poltrof","irak",5800,90);
     	//ABMBuilding.createBuilding(2,"vlestre",2,100000,"blasbareba 128","poltrof","irak",5800,90);
     	//List<Building> lb = new LinkedList<Building>();
     	//lb = Search.buildings(-1, -1, -1,-1, null , -1, "inmo1");
     	//System.out.println("tamaño: " + lb.size());
		/*List<Integer> ownerList = new LinkedList<Integer>();
		ownerList.add(90);
		ownerList.add(1313);
		ABMRealEstate.createRealEstate("inmo1","asss 332","sdaka","Dubai",5800,4635465,"inmo1@gamil,com","www.inmo1.com",ownerList);
		//ABMRealEstate.modifyEmail("inmo1","inmoooooo@masjs.com");
		//ABMBuilding.deleteBuilding("blasbareba 12");
		//ABMOwner.deleteOwner(32492642);
		//ABMOwner.modifyCity(1313,"cordobaCapital",800000);
		//ABMRealEstate.modifyCity("inmo1","BUENOSAIRES",2388);	*/
		
        
    }	
}

