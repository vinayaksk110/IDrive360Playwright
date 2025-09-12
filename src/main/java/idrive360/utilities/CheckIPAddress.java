package idrive360.utilities;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import idrive360.testbase.Constants;

public class CheckIPAddress {
	
	public static Logger log = LogManager.getLogger(CheckIPAddress.class);

	public static String hostIPAddress = null;

	/**
	 * This method identify the execution environment where test scripts has to run
	 * 
	 * @param executionEnvironment It can be Test server or production server
	 * @return true if execution environment is matching with the provided parameter
	 *         and false when it is not matching
	 */
	public Boolean verifyTheExecutionEnvironment(String executionEnvironment) {
		try {
			String url = Constants.IDrive360URL;
			log.info("IDrive360URL: " + url);
			InetAddress inetAddress = InetAddress.getByName(url);
			hostIPAddress = inetAddress.getHostAddress();
			log.info("Resolved IP Address: " + inetAddress.getHostAddress());

			// Set the present environment as per the IP address
			if ((inetAddress.getHostAddress().equalsIgnoreCase(Constants.TEST_SERVER_IP))
					&& (executionEnvironment.equalsIgnoreCase(Constants.TEST_SERVER_25))) {
				log.info("Execution environment: Test Server ending with 25");
				return true;
			} else if ((!inetAddress.getHostAddress().equalsIgnoreCase(Constants.TEST_SERVER_IP))
					&& (executionEnvironment.equalsIgnoreCase(Constants.TEST_SERVER_25))) {
				log.info("Execution environment: Invalid");
				return false;
			} else if ((inetAddress.getHostAddress().equalsIgnoreCase(Constants.TEST_SERVER_IP))
					&& (!executionEnvironment.equalsIgnoreCase(Constants.TEST_SERVER_25))) {
				log.info("Execution environment: Invalid");
				return false;
			} else {
				log.info("Execution environment: Production Server");
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * This method get the execution environment name when URL is launched as test
	 * server or production server
	 * 
	 * @return the environment that is passed in while executing
	 */

	public String getExecutionEnvironmentName() {
		String presentEnvironment = null;
		try {
			String url = Constants.IDrive360URL;
			InetAddress inetAddress = InetAddress.getByName(url);
			hostIPAddress = inetAddress.getHostAddress();
			if (inetAddress.getHostAddress().equalsIgnoreCase(Constants.TEST_SERVER_IP)) {
				presentEnvironment = Constants.TEST_SERVER_25;
			} else {
				presentEnvironment = "Production Server";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return presentEnvironment;
		}
		return presentEnvironment;
	}

	/**
	 * This method provides the current system name in which scripts are running
	 * 
	 * @return the system name
	 */
	public String getSystemName() {
		String systemName = null;
		try {
			systemName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		if (systemName.equalsIgnoreCase(Constants.AUTOMATION_SERVER)) {
			return "Automation Machine";
		} else {
			return systemName.toString();
		}

	}

}
