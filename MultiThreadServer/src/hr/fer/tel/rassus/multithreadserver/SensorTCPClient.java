/*
 * This code has been developed at Departement of Telecommunications,
 * Faculty of Electrical Eengineering and Computing, University of Zagreb.
 */
package hr.fer.tel.rassus.multithreadserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.UserAddress;

/**
 *
 * @author Vedran Serenčeš <vedran.serences@fer.hr>
 */
public class SensorTCPClient implements Runnable {

    private final String IP;
    private final int PORT;
    private UserAddress neighbourSensor;
    private double startTime;
    private Measurement sensorMeasurement;
    private Measurement receivedMeasurement;
    private String sensorID;

    public SensorTCPClient(String sensorID) {
        this.sensorID = sensorID;
        this.neighbourSensor = searchNeighbour(sensorID);   // search for closest neigbour sensor
        System.out.println("Neigbour sensor found: " + neighbourSensor.getSensorID() + " | " + neighbourSensor.getIP() + " | " + neighbourSensor.getPort());
        this.IP = neighbourSensor.getIP();
        this.PORT = neighbourSensor.getPort();
    }

    /**
     * @param args the command line arguments
     */
    public void run() {
        this.startTime = System.currentTimeMillis();
        // create a stream socket and connect it to the specified port number
        // on the named host
        try (Socket clientSocket = new Socket(InetAddress.getByName(this.IP), this.PORT);/*SOCKET->CONNECT*/) {
            while (true) {
                String sndString = "Measurement request";

                // get the socket's output stream and open a PrintWriter on it
                PrintWriter outToServer = new PrintWriter(new OutputStreamWriter(
                        clientSocket.getOutputStream()), true);

                // get the socket's input stream and open a BufferedReader on it
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(
                        clientSocket.getInputStream()));

                // send a String then terminate the line and flush
                outToServer.println(sndString);//WRITE
                System.out.println("Client sent: " + sndString);

                // read a line of text
                String rcvString = inFromServer.readLine();//READ
                System.out.println("Client received: " + rcvString);
                String sensorReading = getMeasurement();
                System.out.println("Clients reading: " + sensorReading);
                
                this.receivedMeasurement = new MeasurementParser(rcvString).getMeasurement();
                this.sensorMeasurement = new MeasurementParser(sensorReading).getMeasurement();
                MeasurementParser parser = new MeasurementParser();
                
                for (int i = 0; i <6; i++) {
                    float average = getAverage(receivedMeasurement, sensorMeasurement, i);
                    storeMeasurement(this.sensorID, parser.getParameter(i), average);
                }
                System.out.println("Saved average measurement!");
                
                Thread.sleep(2000); // Sleep for 2 seconds & repeat
            }

        } catch (IOException ex) {
            System.err.println("Exception caught when opening the socket or trying to read data: " + ex);
            System.exit(1);
        } catch (InterruptedException ex) {
            Logger.getLogger(SensorTCPClient.class.getName()).log(Level.SEVERE, null, ex);
        }//CLOSE//CLOSE//CLOSE//CLOSE//CLOSE//CLOSE//CLOSE//CLOSE
    }
    
    /*
    *   Find neighbour sensor (closest) -> self ID passed for filtering
    */
    private static UserAddress searchNeighbour(java.lang.String username) {
        System.out.println("Searching for neigbour sensor....");
        server.Server_Service service = new server.Server_Service();
        server.Server port = service.getServerPort();
        return port.searchNeighbour(username);
    }

    /*
    *   Calculate average value from measurements
    */
    public float getAverage(Measurement severMeasurement, Measurement clientMeasurement, int counter) {
        int serverValue = getValue(counter, severMeasurement);
        int clientValue = getValue(counter, clientMeasurement);
        float average = 0;
        if (serverValue == 0 && clientValue == 0) {
            return average;
        } else {
            average = (clientValue + serverValue) / 2;
            return average;
        }
    }
    
    /*
    *   Return specific measurement value associated to counter  
    */
    public int getValue(int counter, Measurement measurement) {
        switch (counter) {
            case 0:
                return measurement.getTemperature();
            case 1:
                return measurement.getPressure();
            case 2:
                return measurement.getHumidity();
            case 3:
                return measurement.getCO2();
            case 4:
                return measurement.getNO2();
            case 5:
                return measurement.getSO2();
        }
        return 0;
    }
    
    /*
    *   Get measurement associated with sensor life
    */
    private String getMeasurement() {
        int meauserementNumber = (int) ((System.currentTimeMillis() - this.startTime) % 100) + 2;
        if(meauserementNumber > 100) {
            meauserementNumber = meauserementNumber - 100;  // avoiding reading past 100 lines
        }
        String measurement = Starter.measurementFile.get(meauserementNumber);
        return measurement;
    }
    
    /*
    *   Save measurement to web service server
    */
    private static Boolean storeMeasurement(java.lang.String username, java.lang.String parameter, float averageValue) {
        server.Server_Service service = new server.Server_Service();
        server.Server port = service.getServerPort();
        return port.storeMeasurement(username, parameter, averageValue);
    }
}
