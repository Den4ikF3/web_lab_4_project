package edu.lab4.model;

import java.io.Serializable;

public class Car implements Serializable {
    private Integer id;
    private String plate;
    private String brand;
    private String model;
    private Integer year;
    private String ownerName;

    public Car() {
    }

    public Car(Car other) {
        this.id = other.id;
        this.plate = other.plate;
        this.brand = other.brand;
        this.model = other.model;
        this.year = other.year;
        this.ownerName = other.ownerName;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getPlate() { return plate; }
    public void setPlate(String plate) { this.plate = plate; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
}
