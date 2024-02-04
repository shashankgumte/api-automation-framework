package com.example.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * to handle and manage config.properties
 *
 */
public class ConfigManager
{
private static ConfigManager manager;
private static final Properties prop = new Properties();

private ConfigManager() throws IOException {
    InputStream inputStream = ConfigManager.class.getResourceAsStream("/config.properties");
    prop.load(inputStream);
}

public static ConfigManager getInstance(){
    if(manager== null){
        synchronized (ConfigManager.class){
            try{
                manager= new ConfigManager();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    return manager;
}

public String getString (String key){
    return System.getProperty(key,prop.getProperty(key));
}
}
