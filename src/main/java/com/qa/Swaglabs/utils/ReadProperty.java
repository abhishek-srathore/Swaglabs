// Utility class to read properties from configuration file
package com.qa.Swaglabs.utils;


import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ReadProperty {
	
	// Properties object to hold key-value pairs from config file
	private Properties property;

	// Constructor loads properties from the config file
	public ReadProperty() throws IOException {
		FileReader fileread = new FileReader(System.getProperty("user.dir")+"\\src\\test\\resources\\config\\qaconfig.properties");
		property = new Properties();
		property.load(fileread);
	}

	// Returns the value for a given property key
	public String getProperty(String key) {
		return property.getProperty(key);
	}
}


