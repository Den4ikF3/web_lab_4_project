package edu.lab4.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Repair implements Serializable {
    private Integer id;
    private Integer carId;
    private String carPlate;
    private String description;
    private BigDecimal price;
    private LocalDate repairDate;
    private String status;

    public Repair() {
        this.repairDate = LocalDate.now();
        this.status = "Нове";
    }

    public Repair(Repair other) {
        this.id = other.id;
        this.carId = other.carId;
        this.carPlate = other.carPlate;
        this.description = other.description;
        this.price = other.price;
        this.repairDate = other.repairDate;
        this.status = other.status;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getCarId() { return carId; }
    public void setCarId(Integer carId) { this.carId = carId; }
    public String getCarPlate() { return carPlate; }
    public void setCarPlate(String carPlate) { this.carPlate = carPlate; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public LocalDate getRepairDate() { return repairDate; }
    public void setRepairDate(LocalDate repairDate) { this.repairDate = repairDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
