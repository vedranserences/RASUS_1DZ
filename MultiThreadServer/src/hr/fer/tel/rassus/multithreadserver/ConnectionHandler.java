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
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Vedran Serenčeš <vedran.serences@fer.hr>
 */
public class ConnectionHandler implements Runnable {

    private final Socket clientSocket;
    private final AtomicBoolean isRunning;
    private final AtomicInteger activeConnections;
    private final String serverIP;
    private final int serverPort;
    private double startTime;

    public ConnectionHandler(Socket clientSocket, AtomicBoolean isRunning, AtomicInteger activeConnections, String IP, int PORT, double startTime) {
        this.clientSocket = clientSocket;
        this.isRunning = isRunning;
        this.activeConnections = activeConnections;
        this.serverIP = IP;
        this.serverPort = PORT;
        this.startTime = startTime;
    }

    @Override
    public void run() {
        try (// create a new BufferedReader from an existing InputStream
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(
                        clientSocket.getInputStream()));
                // create a PrintWriter from an existing OutputStream
                PrintWriter outToClient = new PrintWriter(new OutputStreamWriter(
                        clientSocket.getOutputStream()), true);) {

            String receivedString;
            System.out.println("Sensor: " + this.serverIP + " |" + this.serverPort + " accepted connection.");
            // Read text received from client
            while ((receivedString = inFromClient.readLine())!= null) {
                System.out.println("Server on port: " + this.serverPort + " received: " + receivedString);

                // Shutdown the server if requested
                if (receivedString.contains("shutdown")) {
                    isRunning.set(false);
                    activeConnections.set(activeConnections.get() - 1);
                    return;
                }

                String stringToSend = getMeasurement();

                // send a String then terminate the line and flush
                outToClient.println(stringToSend);//WRITE
                System.out.println("Server sent: " + stringToSend);
            }
            activeConnections.set(activeConnections.get() - 1);
        } catch (IOException ex) {
            System.err.println("Exception caught when trying to read or send data: " + ex);
        }
    }
    
    /*
    * Get measurement associated with sensor life
    */
    private String getMeasurement() {
        int meauserementNumber = (int) ((System.currentTimeMillis() - this.startTime) % 100) + 2;
        String measurement = Starter.measurementFile.get(meauserementNumber);
        System.out.println("Server is sending measurement number: " + meauserementNumber + " | " + measurement);
        return measurement;
    }
}
