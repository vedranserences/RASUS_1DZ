/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.Serializable;

/**
 *
 * @author vedra
 */
public class UserAddress implements Serializable{
    private String IP;
    private int port;
    private double latitude;
    private double longitude;
    private String sensorID;
    
    public UserAddress(){
        
    }
    public UserAddress(String IP, int port, double latitude, double longitude, String sensorID){
        this.IP = IP;
        this.port = port;
        this.latitude = latitude;
        this.longitude = longitude;
        this.sensorID = sensorID;
    }
    
    public void setIP(String IP) {
        this.IP = IP;
    }
    
    public void setPort(int port){
        this.port = port;
    }
    
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    
    public void setLongitude(double longitude){
        this.longitude = longitude;
    }
    
    public void setSensorID(String sensorID){
        this.sensorID = sensorID;
    }
    
    public String getIP(){
        return this.IP;
    }
    
    public int getPort(){
        return this.port;
    }
    
    public double getLatitude(){
        return this.latitude;
    }
    
    public double getLongitude(){
        return this.longitude;
    }
    
    public String getSensorID(){
        return this.sensorID;
    }
}
