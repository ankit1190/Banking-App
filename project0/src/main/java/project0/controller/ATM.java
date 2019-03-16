package project0.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import project0.util.AppState;

/**
 * 
 * @author Ankit Patel
 *
 */
public class ATM {
	
	private static Logger log = Logger.getLogger(ATM.class);

	public static void main(String... args) {
							
		
			runApp();
		
	}
	
	public static void runApp() {
		log.info("Initializing system...");
		
		
//		AppState.setAppRunning(true);
//		
//		AppState.setRestartingApp(false);
		
		Screen screen = new Home();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
//		while(AppState.isAppRunning()) {
			
			screen.start(br);
			
//		}
		
//		try {
//			
//			if(AppState.isRestartingApp()) {
//				
//				AppState.setCurrentUser(null);
//				
//				log.info("Restarting application... \n\n\n\n\n");
//				
//				main();
//			}else {
//				br.close();
//				log.info("Shutting down application...");
//			}
//		}catch (IOException e) {
//			log.error(e.getMessage());
//			log.warn("Error in ATM Screen");
//			log.warn("Error closing input stream");
//		}catch(NullPointerException npe) {
//			log.warn("Received null pointer exception");
//		}
	}
}
