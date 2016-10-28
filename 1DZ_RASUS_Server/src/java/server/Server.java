/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.ArrayList;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author vedra
 */
@WebService(serviceName = "Server")
public class Server {

    private final int R = 6371;
    private fakeDB DB;

    public Server() {
        this.DB = new fakeDB();
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "register")
    public Boolean register(@WebParam(name = "username") String username, @WebParam(name = "latitude") double latitude, @WebParam(name = "longitude") double longitude, @WebParam(name = "IPaddress") String IPaddress, @WebParam(name = "port") int port) {
        UserAddress sensor = new UserAddress();
        sensor.setSensorID(username);
        sensor.setLatitude(latitude);
        sensor.setLongitude(longitude);
        sensor.setIP(IPaddress);
        sensor.setPort(port);
        this.DB.saveSensor(sensor);
        System.out.println("Sensor: " + username + " | " + IPaddress + " | " + port + " registered!");
        return true;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "searchNeighbour")
    public UserAddress searchNeighbour(@WebParam(name = "username") String username) {
        /*
        * Get sensor with ID = username
        */
        UserAddress sensor = new UserAddress();
        sensor = this.DB.getSensor(username);
        double lon1 = sensor.getLongitude();
        double lat1 = sensor.getLatitude();

        /*
        
        */
        ArrayList<UserAddress> neighbourSensors = new ArrayList<UserAddress>();
        neighbourSensors = this.DB.getNeighbourSensor(username);
        /*
        *Print ID of all neighbour sensors
        */
        System.out.println("There are: " + neighbourSensors.toArray().length + " neighbour sensors for sensor with ID: " + username);
        for(UserAddress user: neighbourSensors){
            System.out.println("Sensor ID: " + user.getSensorID());
        }
        
        UserAddress min = new UserAddress();
        min = neighbourSensors.get(0);
        double distanceMin = calculateDistance(min, lat1, lon1);
        if (!neighbourSensors.isEmpty()) {
            for (UserAddress user : neighbourSensors) {
                if (!user.getSensorID().equals(min.getSensorID())) {
                    double tempDistanceMin = calculateDistance(user, lat1, lon1);
                    
                    if(tempDistanceMin <= distanceMin){
                        distanceMin = tempDistanceMin;
                        min = user;
                    } else{
                        
                    }
                }
            }
            System.out.println("Nearest sensor for: " + username + " is :" + min.getSensorID() + " | " + min.getIP() + " | " + min.getPort());
            System.out.println("Distance: " + distanceMin + "km");
            return min;
        }
        System.out.println("There are no neighbour sensors....");
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "storeMeasurement")
    public Boolean storeMeasurement(@WebParam(name = "username") String username,
            @WebParam(name = "parameter") String parameter,
            @WebParam(name = "averageValue") float averageValue
    ) {
        Measurement measure = new Measurement();
        measure.setSensorID(username);
        measure.setParamter(parameter);
        measure.setValue(averageValue);
        this.DB.saveMeasurement(measure);
        System.out.println("------ Measurement saved ------");
        System.out.println("SensorID: " + username);
        System.out.println("Parameter: " + parameter);
        System.out.println("Value: " + averageValue);
        return true;
    }

    public double calculateDistance(UserAddress sensor, double latitude1, double longitude1) {
        double lat2 = sensor.getLatitude();
        double lon2 = sensor.getLongitude();
        double dlon = Math.toRadians(lon2 - longitude1);
        double dlat = Math.toRadians(lat2 - latitude1);
        
        double a = Math.pow(Math.sin(dlat/2), 2) + Math.cos(Math.toRadians(latitude1))*Math.cos(Math.toRadians(lat2))*Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;
        return d;
    }
}
