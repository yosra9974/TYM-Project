package tn.tym.esprit.interfaces;



import java.time.LocalDateTime;
import java.util.List;

import tn.tym.esprit.entities.BanUser;
import tn.tym.esprit.entities.User;

public interface IBanUserService {
    public BanUser createBan(User user, String reason, String startDate, String endDate);
    public List<BanUser> getBansByUser(User user);
    public void deleteBan(BanUser ban) ;
    BanUser getBanById(Integer banId);
}
