package edu.lab4.web;

import edu.lab4.db.CarDao;
import edu.lab4.model.Car;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class CarBean implements Serializable {

    private final CarDao dao = new CarDao();
    private List<Car> cars = new ArrayList<>();
    private Car car = new Car();

    @PostConstruct
    public void init() {
        reload();
    }

    private void reload() {
        try {
            cars = dao.findAll();
        } catch (SQLException e) {
            Messages.dbError(e);
        }
    }

    public void save() {
        try {
            if (car.getId() == null) {
                dao.insert(car);
                Messages.info("Автомобіль додано");
            } else {
                dao.update(car);
                Messages.info("Зміни збережено");
            }
            car = new Car();
        } catch (SQLException e) {
            Messages.dbError(e);
        }
        reload();
    }

    public void edit(Car selected) {
        car = new Car(selected);
    }

    public void cancel() {
        car = new Car();
    }

    public void delete(Car selected) {
        try {
            dao.delete(selected.getId());
            Messages.info("Автомобіль видалено");
            if (car.getId() != null && car.getId().equals(selected.getId())) {
                car = new Car();
            }
        } catch (SQLException e) {
            Messages.dbError(e);
        }
        reload();
    }

    public List<Car> getCars() { return cars; }
    public Car getCar() { return car; }
}
