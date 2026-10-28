package model;

import java.util.ArrayList;

public class Patient {

    private String id;
    private String name;
    private String phone;
    private ArrayList<Appointment> appointments = new ArrayList<Appointment>();

    public Patient(String id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public void addAppointment(Appointment a) {
        appointments.add(a);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public String toString() {return id + " " + name + " " + phone;}
}
