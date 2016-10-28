/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.tel.rassus.multithreadserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author Vedran Serenćeš <vedran.serences@fer.hr>
 */
public class Starter {
    
    public static ArrayList<String> measurementFile;
    
    public static void main(String[] args) throws IOException {
        
        measurementFile = new ArrayList<String>();
        readFile(); // read file with measurements
        System.out.println("Successfull measurements insertion!");
        
        SensorTCPServer server = new SensorTCPServer();
        Thread t = new Thread(server);
        t.start();
        
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String userInput = input.readLine();
        
        if (userInput.toLowerCase().contains("start")) {
            SensorTCPClient client = new SensorTCPClient(server.getSensorID());
            client.run();
        }
    }
    
    /*
    *   Read file with measurements and save its value to list
    */
    private static void readFile() {
        try {
            File file = new File("mjerenja.csv");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
   
            reader.readLine(); // read first line -> ignore it
            while ((line = reader.readLine()) != null) {
                measurementFile.add(line);
            }
        } catch (NullPointerException e) {
            System.out.println("File not found");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Error");
            System.exit(1);
        }
    }
}
