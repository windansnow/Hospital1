package service;
import datastruct.SymptomGraph;
import datastruct.WeightedGraph;
import model.Appointment;
import model.Department;
import model.Doctor;
import model.Patient;
import model.Schedule;
import model.Time;

import java.util.ArrayList;
import java.util.HashMap;

public class HospitalData {
    private ArrayList<Department> departments = new ArrayList<Department>();
    private ArrayList<Doctor> doctors = new ArrayList<Doctor>();
    private ArrayList<Patient> patients = new ArrayList<Patient>();
    private ArrayList<Schedule> schedules = new ArrayList<Schedule>();
    private ArrayList<Appointment> appointments = new ArrayList<Appointment>();

    private HashMap<String, Department> departmentMap = new HashMap<String, Department>();
    private HashMap<String, Doctor> doctorMap = new HashMap<String, Doctor>();
    private HashMap<String, Schedule> scheduleMap = new HashMap<String, Schedule>();

    private SymptomGraph symptomGraph = new SymptomGraph();
    private WeightedGraph routeGraph = new WeightedGraph();
    private HashMap<String, String> doctorRoomMap = new HashMap<String, String>();

    public HospitalData() {
        createSampleData();
    }

    private void createSampleData() {}
    private void addDepartment() {}

    private void addDoctor(){}

    private void addPatient() {}

    private void addSchedule() {}

    public ArrayList<Department> getDepartments() {
        return departments;
    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public Department getDepartment(String id) {
        return departmentMap.get(id);
    }

    public Department getDepartmentByName(String name) {
        for (int i = 0; i < departments.size(); i++) {
            if (departments.get(i).getName().equals(name)) {
                return departments.get(i);
            }
        }
        return null;
    }

    public Doctor getDoctor(String id) {
        return doctorMap.get(id);
    }

    public Patient getPatient(String id) {
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getId().equals(id)) {
                return patients.get(i);
            }
        }
        return null;
    }


    public ArrayList<Doctor> getDoctorsByDepartment(String depId) {
        ArrayList<Doctor> result = new ArrayList<Doctor>();
        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).getDepId().equals(depId)) {
                result.add(doctors.get(i));
            }
        }
        return result;
    }


    public Schedule getSchedule(String doctorId, String date, Time time) {
        return scheduleMap.get(doctorId + "|" + date + "|" + time.name());
    }

    public void addAppointment(Appointment a) {
        appointments.add(a);
        Patient p = getPatient(a.getPatientId());
        if (p != null) {
            p.addAppointment(a);
        }
    }

    public SymptomGraph getSymptomGraph() {
        return symptomGraph;
    }

    public WeightedGraph getRouteGraph() {
        return routeGraph;
    }

    public String getDoctorRoom(String doctorId) {
        return doctorRoomMap.get(doctorId);
    }

    public String toString() {
        return "科室 " + departments.size() + " 个，医生 " + doctors.size() + " 人，排班 " + schedules.size() + " 条，患者 "
                + patients.size() + " 人；" + symptomGraph.toString();
    }
}
