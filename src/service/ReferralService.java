package service;

import datastruct.SymptomGraph;
import model.Department;
import model.Doctor;
import model.Schedule;

import javax.xml.transform.stax.StAXResult;
import java.util.*;

public class ReferralService {
    private HospitalData data;

    public ReferralService(HospitalData data) {
        this.data = data;
    }

    public static class Suggestion {
        public Department department;
        public double similarity;
        public int freeDoctors;

        public String getText() {
            return department.getName() + "（相近度 " + String.format("%.0f", similarity * 100)
                    + "%，有号医生 " + freeDoctors + " 人）";
        }


    }

    public boolean isBusy(String depId) {
        ArrayList<Doctor> doctors = data.getDoctorsByDepartment(depId);
        for (int i = 0; i < doctors.size(); i++) {
            if (hasFreeSlot(doctors.get(i).getId())) {
                return false;
            }

        }
        return true;
    }

    private int countFreeDoctors(String deptId) {
        ArrayList<Doctor> doctors = data.getDoctorsByDepartment(deptId);
        int count = 0;
        for (int i = 0; i < doctors.size(); i++) {
            if (hasFreeSlot(doctors.get(i).getId())) {
                count = count + 1;
            }
        }
        return count;
    }

    public boolean hasFreeSlot(String doctorId) {
        ArrayList<Schedule> list = data.getSchedules();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDoctorId().equals(doctorId) && list.get(i).canBook()) {
                return true;
            }

        }
        return false;
    }

    public ArrayList<Suggestion> suggest(Department dep){
        ArrayList<Suggestion> result = new ArrayList<Suggestion>();
        if(dep == null){
            return result;
        }
        SymptomGraph graph = data.getSymptomGraph();
        ArrayList<Department> all = data.getDepartments();
        for(int i = 0; i< all.size(); i++){
            Department d = all.get(i);
            if(d.getId().equals(dep.getId())) continue;;

            double sim = graph.similarity(dep.getId(), d.getId());
            if(sim <= 0.5) continue;
            int free = countFreeDoctors(d.getId());
            if(free == 0) continue;
            Suggestion s = new Suggestion();
            s.department = d;
            s.similarity = sim;
            s.freeDoctors = free;
            result.add(s);
        }
        Collections.sort(result, new Comparator<Suggestion>() {
            @Override
            public int compare(Suggestion o1, Suggestion o2) {
                return Double.compare(o2.similarity, o1.similarity);
            }
        });
        return result;
    }

}
