/*
 * This code has been developed at Departement of Telecommunications,
 * Faculty of Electrical Eengineering and Computing, University of Zagreb.
 */
package hr.fer.tel.rassus.multithreadserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Krešimir Pripužić <kresimir.pripuzic@fer.hr>
 */
public class SensorTCPServer extends Thread implements ServerIf, Runnable {

    private final String IP = "127.0.0.1";
    private final int PORT = 10002; // server port
    private double latitude;
    private double longitude;
    private String sensorID;
    private double startTime;
    private static final int NUMBER_OF_THREADS = 4;
    private static final int MAX_CLIENTS = 10;

    private final AtomicInteger activeConnections;
    private ServerSocket serverSocket;
    private final ExecutorService executor;
    private final AtomicBoolean runningFlag;

    public SensorTCPServer() {
        activeConnections = new AtomicInteger(0);
        executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        runningFlag = new AtomicBoolean(true);

        calculateLocation();    // Generate sensor location
        generateID();   // generate sensor unique ID
        register(sensorID, latitude, longitude, IP, PORT);
    }

    public void run() {
        //start all required services and run the mail loop for accepting client requests
        startup();
    }

    // Starts all required server services.
    @Override
    public void startup() {
        this.startTime = System.currentTimeMillis();
        // create a server socket, bind it to the specified port on the local host
        // and set the max queue length for client requests
        try (ServerSocket serverSocket = new ServerSocket(PORT, MAX_CLIENTS);/*SOCKET->BIND->LISTEN*/) {
            this.serverSocket = serverSocket;

            // set timeout to avoid blocking
            serverSocket.setSoTimeout(50);

            System.out.println("Waiting for clients");

            //start the main loop for accepting client requests 
            loop();

        } catch (IOException ex) {
            System.err.println("Exception caught when opening or setting the socket: " + ex);
        } finally {
            executor.shutdown();
        } //CLOSE
    }

    // The main loop for accepting client requests.
    @Override
    public void loop() {
        while (runningFlag.get()) {
            try {
                // create a new socket, accept and listen for a connection to be
                // made to this socket
                Socket clientSocket = serverSocket.accept();/*ACCEPT*/

                // execute a tcp request handler in a new thread
                Runnable worker = new ConnectionHandler(clientSocket, runningFlag, activeConnections, this.IP, this.PORT, this.startTime);
                executor.execute(worker);
                activeConnections.set(activeConnections.get() + 1);
            } catch (SocketTimeoutException ste) {
                // do nothing, check runningFlag flag
            } catch (IOException ex) {
                System.err.println("Exception caught when waiting for a connection: " + ex);
            }
        }
    }

    @Override
    public void shutdown() {
        while (activeConnections.get() > 0) {
            System.out.println("WARNING: There are still active connections");
            try {
                Thread.sleep(5000);
            } catch (java.lang.InterruptedException e) {
            }
        }
        if (activeConnections.get() == 0) {
            System.out.println("Server shutdown.");
        }
    }

    private void generateID() {
        this.sensorID = UUID.randomUUID().toString();
        System.out.println("Sensor ID: " + sensorID);
    }

    private void calculateLocation() {
        Random r = new Random();
        this.longitude = 15.87 + (16 - 15.87) * r.nextDouble();
        this.latitude = 45.75 + (45.85 - 45.75) * r.nextDouble();
        System.out.println("Sensor location: [" + latitude + ", " + longitude + "]");
    }
    
     private static Boolean register(java.lang.String username, double latitude, double longitude, java.lang.String iPaddress, int serverPort) {
        server.Server_Service service = new server.Server_Service();
        server.Server port = service.getServerPort();
        return port.register(username, latitude, longitude, iPaddress, serverPort);
    }

    @Override
    public void setRunningFlag(boolean running) {
        this.runningFlag.set(running);
    }

    @Override
    public boolean getRunningFlag() {
        return runningFlag.get();
    }

    public String getIP() {
        return this.IP;
    }

    public int getPort() {
        return this.PORT;
    }
    
    public String getSensorID(){
        return this.sensorID;
    }
}
