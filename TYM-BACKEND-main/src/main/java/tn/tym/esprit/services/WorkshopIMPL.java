package tn.tym.esprit.services;


import lombok.extern.slf4j.Slf4j;
import tn.tym.esprit.entities.Workshop;
import tn.tym.esprit.interfaces.IWorkshop;
import tn.tym.esprit.repositories.WorkshopRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class WorkshopIMPL implements IWorkshop {
    @Autowired
    WorkshopRepository wu;




    @Override

    public Workshop createWorkshop(Workshop workshop) {
        return wu.save(workshop);
    }
    @Override

    public Optional<Workshop> getWorkshopById(Integer id) {
        return wu.findById(id);
    }
    @Override

    public List<Workshop> getAllWorkshops() {
        return (List<Workshop>) wu.findAll();
    }
    @Override

    public Workshop updateWorkshop(Workshop workshop) {
        return wu.save(workshop);
    }
    @Override

    public void deleteWorkshopById(Integer id) {
        wu.deleteById(id);

}
}
