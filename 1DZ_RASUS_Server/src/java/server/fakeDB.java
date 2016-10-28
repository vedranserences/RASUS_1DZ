/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.ArrayList;

/**
 *
 * @author vedra
 */
public class fakeDB {

    private ArrayList<UserAddress> sensorList;
    private ArrayList<Measurement> measurementList;

    public fakeDB() {
        this.sensorList = new ArrayList<UserAddress>();
        this.measurementList = new ArrayList<Measurement>();
    }

    public void saveSensor(UserAddress sensore) {
        this.sensorList.add(sensore);
    }

    public void saveMeasurement(Measurement measure) {
        this.measurementList.add(measure);
    }

    public UserAddress getSensor(String sensorID) {
        for (UserAddress sensor : this.sensorList) {
            if (sensor.getSensorID().equals(sensorID)) {
                return sensor;
            } 
        }
        return null;
    }
    
    public ArrayList<UserAddress> getNeighbourSensor(String sensorID){
        ArrayList<UserAddress> neighbourSensors = new ArrayList<UserAddress>();
        for(UserAddress sensor: this.sensorList){
            if(!sensor.getSensorID().equals(sensorID)){
                neighbourSensors.add(sensor);
            }
        }
        return neighbourSensors;
    }
}
