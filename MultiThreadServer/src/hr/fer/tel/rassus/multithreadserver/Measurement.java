package hr.fer.tel.rassus.multithreadserver;


import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vedra
 */
public class Measurement implements Serializable{

    private int temperature;
    private int pressure;
    private int humidity;
    private int CO2;
    private int NO2;
    private int SO2;

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getTemperature() {
        return this.temperature;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getPressure() {
        return this.pressure;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getHumidity() {
        return this.humidity;
    }

    public void setCO2(int CO2) {
        this.CO2 = CO2;
    }

    public int getCO2() {
        return this.CO2;
    }

    public void setNO2(int NO2) {
        this.NO2 = NO2;
    }

    public int getNO2() {
        return this.NO2;
    }

    public void setSO2(int SO2) {
        this.SO2 = SO2;
    }

    public int getSO2() {
        return this.SO2;
    }
}
