/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author vedra
 */
public class Measurement {
    private String sensorID;
    private String parameter;
    private float averageValue;
    
    public Measurement(){
        
    }
    
    public void setSensorID(String sensorID){
        this.sensorID = sensorID;
    }
    
    public void setParamter(String parameter){
        this.parameter = parameter;
    }
    
    public void setValue(float averageValue){
        this.averageValue = averageValue;
    }
    
    public String getSensorID(){
        return this.sensorID;
    }
    
    public String getParameter(){
        return this.parameter;
    }
    
    public float getValue(){
        return this.averageValue;
    }
}
