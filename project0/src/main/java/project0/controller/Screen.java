package project0.controller;

import java.io.BufferedReader;

public interface Screen {
		/**
		 * 
		 * @param br
		 * @return	displays data received from User and/or account services depending on the screen's functionality.
		 */
		Screen start(BufferedReader br);
}
