package introsde.rest.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.glassfish.jersey.client.ClientConfig;
import org.json.JSONArray;
import org.json.JSONObject;


public class ClientApp {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		Scanner input = new Scanner(System.in);
		String name = null;
		int choice = -1;

		System.out.println(
"                                           "
				+ "LifESaVER APPLicaTioN\n"+"                                                   "
						+ ".... giving you those extra 9 cat-lives");
		System.out.println("This APP would help you keep the doctors away by monitoring:\n"
				+ "                                                          - WATER CONSUMPTION(Recommended not less than 3 Litres per day);\n"
				+ "                                                          - HOURS OF SLEEP(Recommended not less than 8 hours );\n"
				+ "                                                          - NUMBER OF STEPS(Recommended not less than 3000 steps);\n"
				+ "                                                          - BMI MEASUREMENT.\n"
				+ "These standards(GOALS) are already set by default but feel free to IMPROVE them\n");


		 System.out.println("To go to the MENU please type \"Y\"");
		 Scanner s= new Scanner(System.in);
		 char c = s.next().charAt(0);

	        if (c=='Y'|| c=='y') {
	        	System.out.println("\n\n");

		while (choice != 0) {
			System.out.println("                                         LifESaVER APPLicaTioN");
			System.out.println("                                               MAIN MENU\n");
			System.out.println("                                 PRESS 1 - To DISPLAY your current personal details.");
			System.out.println("                                 PRESS 2 - To ENTER the amount of steps you made today and CHECK goal.");
			System.out.println("                                 PRESS 3 - To ENTER the hours of sleep you get last night and CHECK goal");
			System.out.println("                                 PRESS 4 - To ENTER the amount of water you drank today and CHECK goal.");
			System.out.println("                                 PRESS 5 - To SET the goal - number of steps you should make, at least 3000 recommended.");
			System.out.println("                                 PRESS 6 - To SET the goal - hours of sleep you should get, at least 8 hours recommended.");
			System.out.println("                                 PRESS 7 - To SET the goal - how many litres of water you should drink, at least 3 litres recommended.");
			System.out.println("                                 PRESS 8 - To DISPLAY all your current goals.");
			System.out.println("                                 PRESS 0 - TO SEE ME SOON.");

			System.out.println("Waiting...: ");
			choice = Integer.parseInt(input.nextLine());

			if (choice < 0 || choice > 6) {
				System.out.print("\nSORRY, WRONG CHOICE, read carefully and choose from the available options!\n\n");
			}

			switch (choice) {
				//DIAPLAY PERSONAL DETAILS
				case 1:
					 //String ENDPOINT = "http://10.218.200.214:5500/introsde/businessLogic/getPersonDetails";
					 String ENDPOINT = "https://businesslogic.herokuapp.com/introsde/businessLogic/getPersonDetails";

					 DefaultHttpClient client = new DefaultHttpClient();
					 HttpGet request = new HttpGet(ENDPOINT);
					 HttpResponse response = client.execute(request);

					 BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

					 StringBuffer result = new StringBuffer();
					 String line = "";
					while ((line = rd.readLine()) != null) {
						result.append(line);
					}

					JSONObject o = new JSONObject(result.toString());

					if (response.getStatusLine().getStatusCode() == 200) {

						System.out.println("                     ________________\n");
						System.out.println("                     PERSONAL DETAILS");
						System.out.println("                     ________________\n");

						System.out.format("%15s%15s%15s%20s",
								"First Name", "Last Name", "ID Number", "Birthday\n");
						System.out.format ("%12s%18s%11d%24s\n",
								o.getString("name"), o.getString("lastname"), o.getInt("idPerson"),o.getString("birthdate"));



						 System.out.println("                   ____________________________\n");
						 System.out.println("                   YOUR CURRENT HEALTH PROFILE");
						 System.out.println("                   ____________________________\n");

						 System.out.format("%15s%15s%15s%20s",
									"MEASURE:", "STEPS", "WATER(L)", "SLEEP(hrs)\n");

						 	System.out.format("%29s%15s%15s", o.getJSONArray("lifeStatus").getJSONObject(0).get("value"),
						 			o.getJSONArray("lifeStatus").getJSONObject(1).get("value"),
						 			o.getJSONArray("lifeStatus").getJSONObject(2).get("value"));

						 System.out.println("\n");


			}
			 break;

			 //STEPS AND CHECK GOAL
   				case 2:
			  			int value = -1;
			  			while(value < 0){
			  				System.out.print("Enter the number of steps taken today: ");
			  				value = Integer.parseInt(input.nextLine());

			  				if(value < 0){
			  					System.out.println("\nSorry, VALUE NOT ACCEPTED. Please, try again.!");
			  				}
			  			}

			 	//Edit measure STEPS in Life Status
			      //  ENDPOINT = "http://10.218.200.214:5901/introsde/processCentric/updateHP";
			      ENDPOINT = "https://processcentric.herokuapp.com/introsde/processCentric/updateHP";
			        ClientConfig clientConfig9 = new ClientConfig();
				 	Client client9 = ClientBuilder.newClient(clientConfig9);
	                client = new DefaultHttpClient();


	                   HttpGet request9 = new HttpGet(ENDPOINT);
	                   HttpResponse response9 = client.execute(request9);

				 		WebTarget service9 = client9.target(ENDPOINT);

				    	Response res9 = null;
				 		String putResp9 = null;

				    	String updateLifeStatus9 ="{" + "\"value\": " + value + "," + "\"measureName\": \"" + "steps" + "\"" + "}";

				    	res9 = service9.request(MediaType.APPLICATION_JSON_TYPE).put(Entity.json(updateLifeStatus9));//update lifeStatus
				    	putResp9 = res9.readEntity(String.class);

				    	//get quote motivation from foresmatic

				    	//String ENDPOINT3 = "http://10.218.200.214:5901/introsde/processCentric/getQuote2";
				    	String ENDPOINT3 = "https://processcentric.herokuapp.com/introsde/processCentric/getQuote2";

				    	DefaultHttpClient client3 = new DefaultHttpClient();
				    	HttpGet request3 = new HttpGet(ENDPOINT3);
				    	HttpResponse response3 = client3.execute(request3);

				    	BufferedReader rd3 = new BufferedReader(new InputStreamReader(response3.getEntity().getContent()));

				    	StringBuffer result3 = new StringBuffer();
				    	String line3 = "";
				    	while ((line3= rd3.readLine()) != null) {
				    	    result3.append(line3);
				    	}

				    	JSONObject testing = new JSONObject(result3.toString());
				    	 String quoteText = testing.getString("quoteText");
				    	 String quoteAuthor = testing.getString("quoteAuthor");
				    	 String quoteLink = testing.getString("quoteLink");

				    	  //get quote motivation from quotedesignapi

				    	    //String ENDPOINT4 = "http://10.218.200.214:5901/introsde/processCentric/getQuote";
				    	    String ENDPOINT4 = "https://processcentric.herokuapp.com/introsde/processCentric/getQuote";
				        	DefaultHttpClient newclient4 = new DefaultHttpClient();
				        	HttpGet newrequest4 = new HttpGet(ENDPOINT4);
				        	HttpResponse newresponse4 = newclient4.execute(newrequest4);

				        	BufferedReader newrd4 = new BufferedReader(new InputStreamReader(newresponse4.getEntity().getContent()));

				        	StringBuffer newresult4 = new StringBuffer();
				        	String newline4 = "";
				        	while ((newline4 = newrd4.readLine()) != null) {
				        		newresult4.append(newline4);
				        	}

				        	JSONObject newObject4 = new JSONObject(newresult4.toString());
				        	 String quote = newObject4.getString("quote");
					    	 String author = newObject4.getString("author");
					    	   String permalink= newObject4.getString("permalink");
				    	// get value from DB  and compare with new value

			    		//String ENDPOINT2 = "http://10.218.200.214:5500/introsde/businessLogic/getGoals";
			    		String ENDPOINT2 = "https://businesslogic.herokuapp.com/introsde/businessLogic/getGoals";
						 DefaultHttpClient client2 = new DefaultHttpClient();
						 HttpGet request2 = new HttpGet(ENDPOINT2);
						 HttpResponse response2 = client2.execute(request2);

						 BufferedReader rd2 = new BufferedReader(new InputStreamReader(response2.getEntity().getContent()));

						 StringBuffer result2 = new StringBuffer();
						 String line2 = "";
						while ((line2 = rd2.readLine()) != null) {
							result2.append(line2);
						}

						JSONArray newO = new JSONArray(result2.toString());



						if(res9.getStatus() == 200 ){
							int idGoal =1;//step id
				    		//check if the goal is hit
				    		 for(int i = 0; i < newO.length(); i++){
				    			if ( newO.getJSONObject(i).getInt("idGoal")== idGoal){
				    				 if (value >= newO.getJSONObject(i).getInt("value")){
				    					 System.out.println("\nWAY TO GO, BRAVO! Goal hit!");
				    					 System.out.println("\nYour step goal was: "+newO.getJSONObject(i).getInt("value"));
				    					 System.out.println("Your current number of steps is:"+ value);
				    					 System.out.println("\nYou are hereby rewarded with a quote: ");
				    					 System.out.println("Quote : "+ quoteText);
				    					 System.out.println("Author : "+ quoteAuthor);
				    					 System.out.println("Source : "+ quoteLink);
				    				 }
				    				 else if  (value < newO.getJSONObject(i).getInt("value")   ){
				    					 System.out.println("\nTOO BAD, GOAL NOT REACHED. YOU NEED TO FOCUS. YOU CAN DO IT");
				    					 System.out.println("\nYour step goal was: "+newO.getJSONObject(i).getInt("value"));
				    					 System.out.println("Your current steps is :"+ value);;
				    					 System.out.println("\nYou need to buckle down! This is a motivational phrase for you:");
				    					 System.out.println("Quote : "+ quote);
				    					 System.out.println("Author : "+ author);
				    					 System.out.println("Source : "+ permalink);
				    				 }
				    				 else {
				    					 System.out.println("UPDATE ERROR, PLEASE, TRY AGAIN!");
				    				 }
				    			 }

				    			 else {
				    				 //System.out.println("\nDoesnt even check the s==steps");

				    			 }

				    		 }

				    	}else{
				  	    		System.out.println("UPDATE ERROR, PLEASE, TRY AGAIN!");
				  	    	}
				break;

				//HOURS SLEPT AND CHECK GOAL
   				case 3:
		  			int value2 = -1;
		  			while(value2 < 0){
		  				System.out.println("How many hours did you sleep last night: ");
		  				value2 = Integer.parseInt(input.nextLine());
		  				if(value2 < 0){
		  					System.out.println("VALUE NOT ACCEPTED. Please, try again.!");
		  				}
		  			}

		 	//Edit measure sleep in LifeStatus
		      //  ENDPOINT = "http://10.218.200.214:5901/introsde/processCentric/updateHP";
		      ENDPOINT = "https://processcentric.herokuapp.com/introsde/processCentric/updateHP";
		       ClientConfig clientConfig1 = new ClientConfig();
		 	   Client client1 = ClientBuilder.newClient(clientConfig1);
                client = new DefaultHttpClient();


               HttpGet request1 = new HttpGet(ENDPOINT);
               HttpResponse response1 = client.execute(request1);

		 		WebTarget service1 = client1.target(ENDPOINT);

		    	Response res1 = null;
		 		String putResp1 = null;

		    	String updateLifeStatus1 ="{" + "\"value\": " + value2 + "," + "\"measureName\": \"" + "sleep" + "\"" + "}";

		    	res1 = service1.request(MediaType.APPLICATION_JSON_TYPE).put(Entity.json(updateLifeStatus1));//update lifestatus
		    	putResp1 = res1.readEntity(String.class);

		    	//get quote motivation from foresmatic

		    	//String ENDPOINT5 = "http://10.218.200.214:5901/introsde/processCentric/getQuote2";
		    	String ENDPOINT5 = "https://processcentric.herokuapp.com/introsde/processCentric/getQuote2";
		    	DefaultHttpClient newclient3 = new DefaultHttpClient();
		    	HttpGet newrequest3 = new HttpGet(ENDPOINT5);
		    	HttpResponse newresponse3 = newclient3.execute(newrequest3);

		    	BufferedReader newrd3 = new BufferedReader(new InputStreamReader(newresponse3.getEntity().getContent()));

		    	StringBuffer newresult3 = new StringBuffer();
		    	String newline3 = "";
		    	while ((newline3= newrd3.readLine()) != null) {//
		    		newresult3.append(newline3);
		    	}

		    	JSONObject foresmatic = new JSONObject(newresult3.toString());
		    	 String quoteText1 = foresmatic.getString("quoteText");
		    	    String quoteAuthor1 = foresmatic.getString("quoteAuthor");
		    	    String quoteLink1 = foresmatic.getString("quoteLink");

		    	  //get quote motivation from quotedesignapi

		    	    //String ENDPOINT6 = "http://10.218.200.214:5901/introsde/processCentric/getQuote";
		    	    String ENDPOINT6 = "https://processcentric.herokuapp.com/introsde/processCentric/getQuote";
		        	DefaultHttpClient newclient6 = new DefaultHttpClient();
		        	HttpGet newrequest6 = new HttpGet(ENDPOINT6);
		        	HttpResponse newresponse6 = newclient6.execute(newrequest6);

		        	BufferedReader newrd6 = new BufferedReader(new InputStreamReader(newresponse6.getEntity().getContent()));

		        	StringBuffer newresult6 = new StringBuffer();
		        	String newline6 = "";
		        	while ((newline6 = newrd6.readLine()) != null) {
		        		newresult6.append(newline6);
		        	}

		        	JSONObject newObject6 = new JSONObject(newresult6.toString());
		        	 String quote1 = newObject6.getString("quote");
			    	 String author1 = newObject6.getString("author");
			    	   String permalink1= newObject6.getString("permalink");
		    	// get value from DB  and compare with new value

	    		 //String ENDPOINT7 = "http://10.218.200.214:5500/introsde/businessLogic/getGoals";
	    		 String ENDPOINT7 = "https://businesslogic.herokuapp.com/introsde/businessLogic/getGoals";
				 DefaultHttpClient newclient2 = new DefaultHttpClient();
				 HttpGet newrequest2 = new HttpGet(ENDPOINT7);
				 HttpResponse newresponse2 = newclient2.execute(newrequest2);

				 BufferedReader newrd2 = new BufferedReader(new InputStreamReader(newresponse2.getEntity().getContent()));

				 StringBuffer newresult2 = new StringBuffer();
				 String newline2 = "";
				while ((newline2 = newrd2.readLine()) != null) {
					newresult2.append(newline2);
				}

				JSONArray sleepObjectJson = new JSONArray(newresult2.toString());



				if(res1.getStatus() == 200 ){
					int idGoal =3;//sleep id
		    		//check if the goal is hit
		    		 for(int i = 0; i < sleepObjectJson.length(); i++){
		    			if ( sleepObjectJson.getJSONObject(i).getInt("idGoal")== idGoal){
		    				 if (value2 >= sleepObjectJson.getJSONObject(i).getInt("value")){
		    					 System.out.println("\nWAY TO GO, BRAVO! Goal hit!");
		    					 System.out.print("Your sleep goal is "+sleepObjectJson.getJSONObject(i).getInt("value")+ " hours ");
		    					 System.out.println("and you slept for "+ value2 + " hour(s).");
		    					 System.out.println("You are hereby rewarded with a quote by "+ quoteAuthor1+":");
		    					 System.out.println(quoteText1+ "(Source: "+quoteLink1+")");
		    					 System.out.println("");
		    				 }
		    				 else if  (value2 < sleepObjectJson.getJSONObject(i).getInt("value")   ){
		    					 System.out.println("\nTOO BAD, GOAL NOT REACHED. YOU NEED TO FOCUS. YOU CAN DO IT.");
		    					 System.out.print("\nYou need to sleep for at least "+sleepObjectJson.getJSONObject(i).getInt("value")+ " hours, ");
		    					 System.out.println("but you only slept for "+ value2 + " hour(s).");
		    					 System.out.println("\nSleep is a very important aspect of healthy living!"+" Our friends at "+ permalink1+ " want to motivate you with the following quote by "+ author1+":");
		    					 System.out.println("Quote : "+ quote1);
		    					 System.out.println("");
		    				 }
		    				 else {
		    					 System.out.println("UPDATE ERROR, PLEASE, TRY AGAIN!");
		    					 System.out.println("");
		    				 }
		    			 }

		    			 else {
		    				 //System.out.println("\nDoesnt even check the s==steps");

		    			 }

		    		 }

		    	}else{
		  	    		System.out.println("UPDATE ERROR, PLEASE, TRY AGAIN!");
		  	    	}
        break;
        			//WATER DRANK AND CHECK GOAL
			  		case 4:
			  			int newvalue2 = -1;
			  			while(newvalue2 < 0){
			  				System.out.println("How many litres of water have you drank today: ");
			  				newvalue2 = Integer.parseInt(input.nextLine());
			  				if(newvalue2 < 0){
			  					System.out.println("VALUE NOT ACCEPTED. Please, try again.!");
			  				}
			  			}

			 	//Edit measure water in Life Status
			   //    ENDPOINT = "http://10.218.200.214:5901/introsde/processCentric/updateHP";
			   ENDPOINT = "https://processcentric.herokuapp.com/introsde/processCentric/updateHP";
			       ClientConfig newclientConfig1 = new ClientConfig();
			 	   Client newclient1 = ClientBuilder.newClient(newclientConfig1);
                    client = new DefaultHttpClient();


                   HttpGet newrequest1 = new HttpGet(ENDPOINT);
                   HttpResponse newresponse1 = client.execute(newrequest1);

			 		WebTarget newservice1 = newclient1.target(ENDPOINT);

			    	Response newres1 = null;
			 		String newputResp1 = null;

			    	String newupdateLifeStatus1 ="{" + "\"value\": " + newvalue2 + "," + "\"measureName\": \"" + "water" + "\"" + "}";

			    	newres1 = newservice1.request(MediaType.APPLICATION_JSON_TYPE).put(Entity.json(newupdateLifeStatus1));
			    	newputResp1 = newres1.readEntity(String.class);

			    	//get quote motivation from foresmatic

			    	//String ENDPOINT8 = "http://10.218.200.214:5901/introsde/processCentric/getQuote2";
			    	String ENDPOINT8 = "https://processcentric.herokuapp.com/introsde/processCentric/getQuote2";
			    	DefaultHttpClient newclient8= new DefaultHttpClient();
			    	HttpGet newrequest8 = new HttpGet(ENDPOINT8);
			    	HttpResponse newresponse8= newclient8.execute(newrequest8);

			    	BufferedReader newrd8 = new BufferedReader(new InputStreamReader(newresponse8.getEntity().getContent()));

			    	StringBuffer newresult8 = new StringBuffer();
			    	String newline8 = "";
			    	while ((newline8= newrd8.readLine()) != null) {
			    		newresult8.append(newline8);
			    	}

			    	JSONObject foresmatic2 = new JSONObject(newresult8.toString());
			    	 String quoteText2 = foresmatic2.getString("quoteText");
			    	 String quoteAuthor2 = foresmatic2.getString("quoteAuthor");
			    	 String quoteLink2 = foresmatic2.getString("quoteLink");

			    	  //get quote motivation from quotedesignapi

			    	    //String ENDPOINT9 = "http://10.218.200.214:5901/introsde/processCentric/getQuote";
			    	    String ENDPOINT9 = "https://processcentric.herokuapp.com/introsde/processCentric/getQuote";
			        	DefaultHttpClient newclient9 = new DefaultHttpClient();
			        	HttpGet newrequest9 = new HttpGet(ENDPOINT9);
			        	HttpResponse newresponse9 = newclient9.execute(newrequest9);

			        	BufferedReader newrd9 = new BufferedReader(new InputStreamReader(newresponse9.getEntity().getContent()));

			        	StringBuffer newresult9 = new StringBuffer();
			        	String newline9 = "";
			        	while ((newline9 = newrd9.readLine()) != null) {
			        		newresult9.append(newline9);
			        	}

			        	JSONObject newObject9 = new JSONObject(newresult9.toString());
			        	 String quote2 = newObject9.getString("quote");
				    	 String author2 = newObject9.getString("author");
				    	   String permalink2= newObject9.getString("permalink");
			    	// get value from DB  and compare with new value

		    		//String ENDPOINT10 = "http://10.218.200.214:5500/introsde/businessLogic/getGoals";
		    		String ENDPOINT10 = "https://businesslogic.herokuapp.com/introsde/businessLogic/getGoals";

					 DefaultHttpClient newclient10 = new DefaultHttpClient();
					 HttpGet newrequest10 = new HttpGet(ENDPOINT10);
					 HttpResponse newresponse10 = newclient10.execute(newrequest10);

					 BufferedReader newrd10 = new BufferedReader(new InputStreamReader(newresponse10.getEntity().getContent()));

					 StringBuffer newresult10 = new StringBuffer();
					 String newline10 = "";
					while ((newline10 = newrd10.readLine()) != null) {
						newresult10.append(newline10);
					}

					JSONArray waterObjectJson = new JSONArray(newresult10.toString());



					if(newres1.getStatus() == 200 ){
						int idGoal =2;
			    		//check if the goal is hit
			    		 for(int i = 0; i < waterObjectJson.length(); i++){
			    			if ( waterObjectJson.getJSONObject(i).getInt("idGoal")== idGoal){
			    				 if (newvalue2 >= waterObjectJson.getJSONObject(i).getInt("value")){
			    					 System.out.println("\nWAY TO GO, BRAVO! Goal hit!");
			    					 System.out.print("Your water goal is "+waterObjectJson.getJSONObject(i).getInt("value")+ " litres ");
			    					 System.out.println("and you dranks "+ newvalue2 + " litre(s) of water");
			    					 System.out.println("You are hereby rewarded with a quote by "+ quoteAuthor2+":");
			    					 System.out.println(quoteText2+ "(Source: "+quoteLink2+")");
			    					 System.out.println("");
			    				 }
			    				 else if  (newvalue2 < waterObjectJson.getJSONObject(i).getInt("value")   ){
			    					 System.out.println("\nTOO BAD, GOAL NOT REACHED. YOU NEED TO FOCUS. YOU CAN DO IT.");
			    					 System.out.print("\nYou need to drink at least "+waterObjectJson.getJSONObject(i).getInt("value")+ " litres of water, ");
			    					 System.out.println("but you only drank "+ newvalue2 + " litre(s) of water.");
			    					 System.out.println("\nShame on you!!!! Water is what keeps us alive as the body contains 60% of water."+" Our friends at "+ permalink2+ " want to motivate you with the following quote by "+ author2+":");
			    					 System.out.println("Quote : "+ quote2);
			    					 System.out.println("");
			    				 }
			    				 else {
			    					 System.out.println("UPDATE ERROR, PLEASE, TRY AGAIN!");
			    				 }
			    			 }

			    			 else {
			    				 //System.out.println("\nDoesnt even check the s==steps");

			    			 }

			    		 }

			    	}else{
			  	    		System.out.println("UPDATE ERROR, PLEASE, TRY AGAIN!");
			  	    	}

            break;
            		//SET GOAL - STEPS
			  		case 5:
			  			int value3 = -1;
			  			while(value3 < 0 || value3 > 1000000){
			  				System.out.println("ENTER YOUR STEP GOAL(preferably not less than 3000 steps): ");
			  				value3 = Integer.parseInt(input.nextLine());
			  				if(value3 < 0 || value3 > 1000000){
			  					System.out.println("VALUE NOT ACCEPTED. Please, try again!");
			  				}
			  			}

			 	//Change the goal STEPS
			    	//ENDPOINT = "http://10.218.200.214:5901/introsde/processCentric/updateGoal";
			    	ENDPOINT = "https://processcentric.herokuapp.com/introsde/processCentric/updateGoal";
			    	ClientConfig clientConfig4 = new ClientConfig();
			 	Client client4 = ClientBuilder.newClient(clientConfig4);

			 	WebTarget service4 = client4.target(ENDPOINT);

			    	Response res4 = null;
			 	String putResp4 = null;

			    	String updateGoal ="{" + "\"value\": " + value3 + "," + "\"type\": \"" + "steps" + "\"" + ","
                            + "\"idGoal\": \"" + "1" + "\""
                            + "}";


			    	res4 = service4.request(MediaType.APPLICATION_JSON_TYPE).put(Entity.json(updateGoal));
			    	putResp4 = res4.readEntity(String.class);

			    	if(res4.getStatus() != 200 ){
			    		System.out.println("UPDATE ERROR. Please, try again!");
			    	}else{
			    		System.out.println("GOAL UPDATED!");
			    	}

			 break;

			 		//SET GOAL - HOURS TO SLEEP
			  		case 6:
			  			int value4 = -1;
			  			while(value4 < 0){
			  				System.out.println("ENTER YOUR NEW SLEEP GOAL(preferably not less than 8 hours): ");
			  				value4 = Integer.parseInt(input.nextLine());
			  				if(value4 < 0 || value4 > 10000){
			  					System.out.println("VALUE NOT ACCEPTED. Please, try again.!");
			  				}
			  			}

			 	//Change the goal SLEEP
			    	//ENDPOINT = "http://10.218.200.214:5901/introsde/processCentric/updateGoal";
			    	ENDPOINT = "https://processcentric.herokuapp.com/introsde/processCentric/updateGoal";
			    	ClientConfig clientConfig5 = new ClientConfig();
			 	Client client5 = ClientBuilder.newClient(clientConfig5);

			 	WebTarget service5 = client5.target(ENDPOINT);

			    	Response res5 = null;
			 	String putResp5 = null;

			    	String updateGoal5 ="{" + "\"value\": " + value4 + "," + "\"type\": \"" + "sleep" + "\"" + ","
                            + "\"idGoal\": \"" + "3" + "\""
                            + "}";

			    	res5 = service5.request(MediaType.APPLICATION_JSON_TYPE).put(Entity.json(updateGoal5));
			    	putResp5 = res5.readEntity(String.class);

			    	if(res5.getStatus() != 200){
			    		System.out.println("UPDATE ERROR. Please, try again!");
			    	}else{
			    		System.out.println("GOAL UPDATED!");
			    	}

			   	 break;
			   	 	//SET GOAL LITRES OF WATER TO DRINK
			  		case 7:
			  			int newvalue4 = -1;
			  			while(newvalue4 < 0){
			  				System.out.println("ENTER YOUR NEW WATER GOAL (preferably not less than 3 litres): ");
			  				newvalue4 = Integer.parseInt(input.nextLine());
			  				if(newvalue4 < 0 || newvalue4 > 10000){
			  					System.out.println("VALUE NOT ACCEPTED. Please, try again.!");
			  				}
			  			}

			 	//Change the WATER goal

			  	//ENDPOINT = "http://10.218.200.214:5901/introsde/processCentric/updateGoal";
			  	ENDPOINT = "https://processcentric.herokuapp.com/introsde/processCentric/updateGoal";
			    ClientConfig newclientConfig5 = new ClientConfig();
			 	Client newclient5 = ClientBuilder.newClient(newclientConfig5);

			 	WebTarget newservice5 = newclient5.target(ENDPOINT);

			    	Response newres5 = null;
			 	String newputResp5 = null;

			    	String newupdateGoal5 ="{" + "\"value\": " + newvalue4 + "," + "\"type\": \"" + "water" + "\"" + ","
                            + "\"idGoal\": \"" + "2" + "\""
                            + "}";

			    	newres5 = newservice5.request(MediaType.APPLICATION_JSON_TYPE).put(Entity.json(newupdateGoal5));
			    	newputResp5 = newres5.readEntity(String.class);

			    	if(newres5.getStatus() != 200){
			    		System.out.println("UPDATE ERROR. Please, try again!");
			    	}else{
			    		System.out.println("GOAL UPDATED!");
			    	}
			 break;
			 	//DISPLAY GOAL
                case 8:
                    //ENDPOINT = "http://10.218.200.214:5500/introsde/businessLogic/getGoals";
                    ENDPOINT = "https://businesslogic.herokuapp.com/introsde/businessLogic/getGoals";
                    DefaultHttpClient client6 = new DefaultHttpClient();
                    HttpGet request6 = new HttpGet(ENDPOINT);
                    HttpResponse response6 = client6.execute(request6);

                    BufferedReader rd6 = new BufferedReader(new InputStreamReader(response6.getEntity().getContent()));

                    StringBuffer result6 = new StringBuffer();
                    String line6 = "";
                    while ((line6 = rd6.readLine()) != null) {
                        result6.append(line6);
                    }

                    JSONArray o6 = new JSONArray(result6.toString());

                    if (response6.getStatusLine().getStatusCode() == 200) {

                    	System.out.println("                              ___________\n");
						System.out.println("                              YOUR GOALS");
						System.out.println("                              ___________\n");
						System.out.format("%15s%15s%15s%20s",
									"GOAL:", "STEPS", "WATER(L)", "SLEEP(hrs)\n");
						System.out.format("%30s%11s%15s", o6.getJSONObject(0).getInt("value"),
						 			o6.getJSONObject(1).getInt("value"),
						 			o6.getJSONObject(2).getInt("value"));

						System.out.println("\n\n");

                    }
                    break;

                case 0:
                	System.out.println("\n"
    	        			+ "                         Thanks you for using LifESaVER APPLicaTioN!!!!");
    	        	System.out.println(""
    	        			+ "	                         Remember, HEALTH is WEALTH!");
			     		}
			    	}
	        }
	        else {
	        	System.out.println("\n"
	        			+ "                         Thanks you for using LifESaVER APPLicaTioN!!!!");
	        	System.out.println(""
	        			+ "	                         Remember, HEALTH is WEALTH!");
	        }
			 }
		}