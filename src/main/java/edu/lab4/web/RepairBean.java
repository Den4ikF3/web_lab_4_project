package edu.lab4.web;

import edu.lab4.db.CarDao;
import edu.lab4.db.RepairDao;
import edu.lab4.model.Car;
import edu.lab4.model.Repair;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class RepairBean implements Serializable {

    private final RepairDao dao = new RepairDao();
    private final CarDao carDao = new CarDao();
    private List<Repair> repairs = new ArrayList<>();
    private List<Car> cars = new ArrayList<>();
    private Repair repair = new Repair();

    @PostConstruct
    public void init() {
        reload();
    }

    private void reload() {
        try {
            cars = carDao.findAll();
            repairs = dao.findAll();
        } catch (SQLException e) {
            Messages.dbError(e);
        }
    }

    public void save() {
        try {
            if (repair.getId() == null) {
                dao.insert(repair);
                Messages.info("Ремонт додано");
            } else {
                dao.update(repair);
                Messages.info("Зміни збережено");
            }
            repair = new Repair();
        } catch (SQLException e) {
            Messages.dbError(e);
        }
        reload();
    }

    public void edit(Repair selected) {
        repair = new Repair(selected);
    }

    public void cancel() {
        repair = new Repair();
    }

    public void delete(Repair selected) {
        try {
            dao.delete(selected.getId());
            Messages.info("Ремонт видалено");
            if (repair.getId() != null && repair.getId().equals(selected.getId())) {
                repair = new Repair();
            }
        } catch (SQLException e) {
            Messages.dbError(e);
        }
        reload();
    }

    public List<Repair> getRepairs() { return repairs; }
    public List<Car> getCars() { return cars; }
    public Repair getRepair() { return repair; }
}
