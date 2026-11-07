package service;
import java.util.*;
import model.Doctor;

public class DoctorService {

    private HospitalData data;
    public DoctorService(HospitalData data){this.data = data;}

    public ArrayList<Doctor> query(String depId, String title, String keyword){
        ArrayList<Doctor> result = new ArrayList<Doctor>();
        ArrayList<Doctor> all = data.getDoctors();
        for(int i = 0; i < all.size(); i++){
            Doctor d = all.get(i);
            if(depId != null && depId.length() > 0 && !d.getDepId().equals(depId)) continue;
            if(title != null && title.length() > 0 && (d.getTitle() == null || d.getTitle().indexOf(title) < 0)) continue;
            if(keyword != null && keyword.length() > 0 && !matchKeyword(d, keyword)) continue;
            result.add(d);
        }
        return result;
    }

    private boolean matchKeyword(Doctor d, String keyword) {
        if(d.getGoodAt() != null && d.getGoodAt().indexOf(keyword) >= 0) return true;
        for(int i = 0; i < d.getKeywords().size(); i++){
            if(d.getKeywords().get(i).indexOf(keyword) >= 0){
                return true;
            }
        }
        return false;
    }

}
