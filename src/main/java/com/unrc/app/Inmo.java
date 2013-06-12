
package com.unrc.app;


import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static spark.Spark.*;
import spark.*;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import com.unrc.app.models.Owner;


import freemarker.template.Template;
import freemarker.template.TemplateException;

import freemarker.template.Configuration;
import com.unrc.app.models.Building;

import java.util.*;

public class Inmo {
	
	/*funcion para pasar los paramtros no nulos a Integer*/
	private static Integer valor(String param, Set<String> s,Request request){
		if(s.contains(param) && request.queryParams(param)!=""){
			return Integer.parseInt(request.queryParams(param));
		}
		else{
			return null;
		}
	}
	

	public static void main( String[] args ){
		
		
		Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "root");
		/*creamos algunos dueños y realEstate como ejemplo*/
		ABMOwner.createOwner(37453263,"Juan","Doe","San Martin 12","centro", "Rio Cuarto",5800, "vlastra@hotmail.com");
		ABMOwner.createOwner(32678354,"Pablo","Palpoller","Ocean Drive 677", "Miami Beach", "Miami",4400,"pablo@gmail.com");
		ABMOwner.createOwner(25897653,"Raul","Sangarengue","Ranksgargen 233","Trensrten","Berlin",8900,"rasanga@gmail.com");
		List<Integer> owners = new LinkedList<Integer>();
		owners.add(37453263);
		ABMRealEstate.createRealEstate("Inmo", "koala 123","Canguro","Sidney",7600,15890076,"Inmo@gmail.com","www.inmo.com",owners);
		Base.close();
		
		/*menu inicial*/
		get(new Route("/") {
	         @Override
	        
	         public Object handle(Request request, Response response) { 
				Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "root");
	            String title = "<h1> Inmobiliaria </h1>";

	            String color = "<body BGCOLOR=#CCCC99>";
	            
				String menu = "<ul> <li> <a href=\"/buildings\"> Inmuebles </a> </li> </ul>";

	            String footer = "<p> created by: lagax</p>";
				Base.close();
	            return title + color + menu + footer;
	         };
			 });
		
		/*submenu*/
		get(new Route("/buildings") {// muestra el menu
	         @Override
	        
	         public Object handle(Request request, Response response) { 
				Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "root");
	            String title = "<h1> Inmuebles </h1>";

	            String color = "<body BGCOLOR=#CCCC99>";
	            
				String menu = "<ul> <li> <a href=\"/filter\"> BuscarInmuebles </a> </li> </ul>";
				
				String menu2 = "<ul> <li> <a href=\"/obtainingdata\"> AgregarInmuebles </a> </li> </ul>";
				
				String menu3 = "<ul> <li> <a href=\"/deleting\"> BorrarInmuebles </a> </li> </ul>";

	            String footer = "<p> created by: lagax</p>";
				Base.close();
	            return title + color + menu + menu2 + menu3 +footer;
	         };
			 });
		
		/*menu para obtener datos del inmueble a crear*/
		get(new Route("/obtainingdata") {
			public Object handle(Request request, Response response) {
				Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "root");
				Base.openTransaction();
				StringWriter writer = new StringWriter();
				final Configuration configuracion = new Configuration();	
				configuracion.setClassForTemplateLoading(Inmo.class, "/");
				Template plantillaInmoApp = null;
				try {
					plantillaInmoApp = configuracion.getTemplate("inmobiliaria1.html");
				} catch (IOException e) {
					e.printStackTrace();
				}
				Map<String, Object> buildingMap = new HashMap<String, Object>();
				try {
					plantillaInmoApp.process(buildingMap, writer);
				} catch (TemplateException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
		
					e.printStackTrace();
				}
				Base.commitTransaction();
				Base.close();
				 return writer;
		     };
			 });
		
		/*menu para selecionar el tipo de filtro de inmuebles a buscar*/ 
		get(new Route("/filter") {
			public Object handle(Request request, Response response) {
				Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "root");
				Base.openTransaction();
				StringWriter writer = new StringWriter();
				final Configuration configuracion = new Configuration();	
				configuracion.setClassForTemplateLoading(Inmo.class, "/");
				Template plantillaInmoApp = null;
				try {
					plantillaInmoApp = configuracion.getTemplate("busqueda.html");
				} catch (IOException e) {
					e.printStackTrace();
				}
				Map<String, Object> buildingMap = new HashMap<String, Object>();
				try {
					plantillaInmoApp.process(buildingMap, writer);
				} catch (TemplateException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
		
					e.printStackTrace();
				}
				Base.commitTransaction();
				Base.close();
				 return writer;
		     };
			 });
	
		/*menu para elegir el inmueble a borrar*/
		get(new Route("/deleting") {
			public Object handle(Request request, Response response) {
				Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "root");
				Base.openTransaction();
				StringWriter writer = new StringWriter();
				final Configuration configuracion = new Configuration();	
				configuracion.setClassForTemplateLoading(Inmo.class, "/");
				Template plantillaInmoApp = null;
				try {
					plantillaInmoApp = configuracion.getTemplate("borrar.html");
				} catch (IOException e) {
					e.printStackTrace();
				}
				Map<String, Object> buildingMap = new HashMap<String, Object>();
				try {
					plantillaInmoApp.process(buildingMap, writer);
				} catch (TemplateException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
		
					e.printStackTrace();
				}
				Base.commitTransaction();
				Base.close();
				 return writer;
		     };
			 });
		
		/*creacion de inmuebles*/
    	post(new Route("/Createbuildings") {
			@Override
			public Object handle(Request request, Response response) {
				Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "root");
				Set<String> parametros = request.queryParams();
				Integer type = valor("type",parametros,request);
				Integer status = valor("status",parametros,request);
				Integer price = valor("price",parametros,request);
				Integer postcode = valor("postcode",parametros,request); 
				Integer dni = valor("dni",parametros,request);
                String description = request.queryParams("description");
                String city = request.queryParams("city");
                String address = request.queryParams("address");
                String neighborhood = request.queryParams("neighborhood");
                ABMBuilding.createBuilding(type, description, status, price, address, neighborhood, city, postcode, dni);  
                Base.close();
                response.status(201);
                String color = "<body BGCOLOR=#CCCC99>";
                return color + "inmueble registrado";
			}
		});
    	
    	/*eliminacion del inmueble*/
    	get(new Route("/Deletebuildings"){
    		@Override
    		public Object handle(Request request, Response response) {
    			Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "root");
    			String a = request.queryParams("address");
    			ABMBuilding.deleteBuilding(a);
    			Base.close();
    			return "Inmueble eliminado";
    		}
    	});
		
    	/*busqueda de inmuebles*/
		get(new Route("/Searchbuildings") {
	         @Override
	         public Object handle(Request request, Response response) {
	        	Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/inmoapp_development", "root", "root");
				Set<String> parametros = request.queryParams();
				Integer type = valor("type",parametros,request); 				
				Integer status = valor("status",parametros,request);
				Integer minPrice = valor("minPrice",parametros,request);
				Integer maxPrice = valor("maxPrice",parametros,request);
				String city = request.queryParams("city");
				Integer dni = valor("dni",parametros,request);
				String realEstate = request.queryParams("realEstate");
	        	List<Building> inmuebles = Search.buildings(type,status,maxPrice,minPrice,city,dni,realEstate);
	        	String pantalla="";
	        	Iterator<Building> iter=inmuebles.iterator();
	        	while(iter.hasNext()){
	        	 Building b = iter.next();
	        	 pantalla = pantalla + b.toString()+("\n");
	        	}
	        	Base.close();
	        	return  pantalla;
	         }
	      });
        
	}
}

