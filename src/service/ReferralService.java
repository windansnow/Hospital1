package service;

import datastruct.SymptomGraph;
import model.Department;
import model.Doctor;
import model.Schedule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

public class ReferralService {
    private HospitalData data;
    public ReferralService(HospitalData data){this.data = data;}

    public static class Suggestion{
        public Department department;
        public double similarity;
        public int freeDoctors;

        public String getText() {
            return department.getName() + "（相近度 " + String.format("%.0f", similarity * 100)
                    + "%，有号医生 " + freeDoctors + " 人）";
        }


    }
    public boolean isBusy(String depId){
        ArrayList<Doctor> doctors = data.getDoctorsByDepartment(depId);
        for(int i = 0; i < doctors.size(); i++){
            if(hasFreeSlot(doctors.get(i).getId())){
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

    public boolean hasFreeSlot(String doctorId){
        ArrayList<Schedule> list = data.getSchedules();
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getDoctorId().equals(doctorId) && list.get(i).canBook()){
                return true;
            }

        }
 
    }
}
