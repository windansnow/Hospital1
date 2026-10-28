package service;

import model.Doctor;
import model.Appointment;
import model.Patient;
import model.Schedule;
import model.Time;
import java.util.ArrayList;

public class AppointmentService {
    private HospitalData data;

    public AppointmentService(HospitalData data){
        this.data = data;
    }

    public static class bookResult{
        public boolean success;
        public String message;
        public Appointment appointment;
    }

    public String takeSlot(String doctorId, String date, Time time){
        Schedule s = data.getSchedule(doctorId, date, time);
        if (s == null) {
            return "该医生这个时段没有排班";
        }
        if (s.isCanceled()) {
            return "该时段医生停诊了";
        }
        if (s.getRemaining() <= 0) {
            return "该时段号源已满（" + s.getBooked() + "/" + s.getMaxCount() + "）";
        }
        s.setBooked(s.getBooked() + 1);
        return null;
    }

    public void returnSlot(String doctorId, String date, Time time){
        Schedule s = data.getSchedule(doctorId, date, time);
        if(s != null && s.getBooked() > 0){
            s.setBooked(s.getBooked() - 1);
        }
    }

    public bookResult book(String patientId, String doctorId, String date, Time time){
        bookResult result = new bookResult();

        Patient p = data.getPatient(patientId);
        Doctor d = data.getDoctor(doctorId);
        if(p == null){
            result.message = "患者不存在 " + p.getName();
            return result;
        }
        if(d == null){
            result.message = "医生不存在 "+ d.getName();
            return result;
        }
        ArrayList<Appointment> mine = p.getAppointments();
        for(int i = 0; i < mine.size(); i++){
            Appointment a = mine.get(i);
            if(a.isActive() && a.getDate().equals(date) && a.getTime() == time){
                result.message = "您已有预约";
                return result;
            }
        }

        String e = takeSlot(doctorId, date, time);
        if(e != null){
            result.message = e;
            return result;
        }
        Appointment ap = new Appointment(patientId, p.getName(), doctorId, d.getName(), d.getDepName(), date, time);
        data.addAppointment(ap);

        result.success = true;
        result.message = "预约成功";
        result.appointment = ap;
        return result;

    }

    public String getRemain(String doctorId, String date, Time time){
        Schedule s = data.getSchedule(doctorId, date, time);
        if(s == null){
            return "没有排班了";
        }
        return "剩余 " + s.getRemaining() + "/" + s.getMaxCount();
    }
}
