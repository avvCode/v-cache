package com.vv.server.domain;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class Config {
    public static int port;
    public static String commandFilePath;
    public static String commandScanPath;
    public static String storePath;
    static{
        try {
            Properties properties = new Properties();
            properties.load(new BufferedReader(new InputStreamReader(new FileInputStream("src/resource/config.properties"))));
            port = Integer.parseInt(properties.getProperty("port"));
            commandFilePath = properties.getProperty("commandFilePath");
            commandScanPath = properties.getProperty("commandScanPath");
            storePath = properties.getProperty("storePath");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
