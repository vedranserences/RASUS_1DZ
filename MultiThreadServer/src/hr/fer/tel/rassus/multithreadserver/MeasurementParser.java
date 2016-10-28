package hr.fer.tel.rassus.multithreadserver;


import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Vedran Serences
 */
public class MeasurementParser {

    private Measurement measurement;
    
    public MeasurementParser(){
        
    }
    
    public MeasurementParser(String line) {
        this.measurement = new Measurement();
        splitLine(line);
    }

    private void splitLine(String line) {
        String[] measurement = line.split(",", -1);
        System.out.println(Arrays.toString(measurement));
        for (int i = 0; i < 6; i++) {
            parseMeasure(measurement[i], i);
        }
        printRandMeasure();
    }

    private void parseMeasure(String value, int counter) {
        if (!value.trim().equals("")) {
            saveValue(Integer.parseInt(value.trim()), counter);

        } else {
            saveValue(0, counter);
        }
    }

    private void saveValue(int value, int counter) {
        System.out.println("Value: " + value + " | Counter: " + counter);
        switch (counter) {
            case 0:
                this.measurement.setTemperature(value);
                break;
            case 1:
                this.measurement.setPressure(value);
                break;
            case 2:
                this.measurement.setHumidity(value);
                break;
            case 3:
                this.measurement.setCO2(value);
                break;
            case 4:
                this.measurement.setNO2(value);
                break;
            case 5:
                this.measurement.setSO2(value);
                break;
        }
    }

    private void printRandMeasure() {
        System.out.println("Temperature: " + this.measurement.getTemperature());
        System.out.println("Pressure: " + this.measurement.getPressure());
        System.out.println("Humidity: " + this.measurement.getHumidity());
        System.out.println("CO2: " + this.measurement.getCO2());
        System.out.println("NO2: " + this.measurement.getNO2());
        System.out.println("SO2: " + this.measurement.getSO2());
        System.out.println("-------------------");
    }

    public Measurement getMeasurement() {
        return this.measurement;
    }

    public String getParameter(int counter) {
        switch (counter) {
            case 0:
                return "Temperature";
            case 1:
                return "Pressure";
            case 2:
                return "Humidity";

            case 3:
                return "CO2";
            case 4:
                return "N02";
            case 5:
                return "SO2";
        }
        return null;
    }
}
