package project0.util;

import project0.pojo.User;

public class AppState {
	
	private static User currentUser;
//	private static boolean appRunning;
//	private static boolean restartingApp;
	
	private AppState() {
		super();
	}

	public static User getCurrentUser() {
		return currentUser;
	}

	public static void setCurrentUser(User currentUser) {
		AppState.currentUser = currentUser;
	}

//	public static boolean isAppRunning() {
//		return appRunning;
//	}
//
//	public static void setAppRunning(boolean appRunning) {
//		AppState.appRunning = appRunning;
//	}
//
//	public static boolean isRestartingApp() {
//		return restartingApp;
//	}
//
//	public static void setRestartingApp(boolean restartingApp) {
//		AppState.restartingApp = restartingApp;
//	}
	
	
	
}
