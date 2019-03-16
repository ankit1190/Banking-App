package project0.controller;

import java.io.BufferedReader;

import org.apache.log4j.Logger;

import project0.util.AppState;

public class Home implements Screen{
	
	private static Logger log = Logger.getLogger(Home.class);

	@Override
	public Screen start(BufferedReader br) {
		
		System.out.println("\n\n\n\n[TEST] - Current User: " + AppState.getCurrentUser());
		
		if(AppState.getCurrentUser() == null) {
			
			System.out.println("+---------------------------------------+");
			System.out.println("|					|");
			System.out.println("|		Gimme Bank		|");
			System.out.println("|					|");
			System.out.println("+---------------------------------------+");
			
			System.out.println("<1> Login");
			System.out.println("<2> Register");
//			System.out.println("<3> Exit");
			
			try {
				System.out.print("Selection: ");
				String selection = br.readLine();
				
				switch(selection) {
				
				case "1": 
					return new Login().start(br);
				
				case "2":
					return new RegisterScreen().start(br);
					
//				case "3":
//					
//					AppState.setAppRunning(false);
//					
//					break;
				
				default:
					System.out.println("Invalid Selection!");
					this.start(br);
				}
			} catch(Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
				log.warn("Error reading input from console");
				log.info("Restarting application...");
//				AppState.setRestartingApp(true);
//				AppState.setAppRunning(false);
				return null;
			
			}
		} else {
			return new Dashboard().start(br);
		}
		return this;
	}

}
