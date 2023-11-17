package tn.tym.esprit.interfaces;



import java.util.List;
import java.util.Optional;

import tn.tym.esprit.entities.Workshop;

public interface IWorkshop {
    Workshop createWorkshop(Workshop workshop);

    Optional<Workshop> getWorkshopById(Integer id);

    List<Workshop> getAllWorkshops();

    Workshop updateWorkshop(Workshop workshop);

    void deleteWorkshopById(Integer id);

}
