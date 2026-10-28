package model;

public class Schedule {

    private String doctorId;
    private String date;
    private Time time;
    private int maxCount;
    private int booked;
    private boolean canceled;

    public Schedule(String doctorId, String date, Time time, int maxCount) {
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
        this.maxCount = maxCount;
        this.booked = 0;
        this.canceled = false;
    }

    public int getRemaining() {
        return maxCount - booked;
    }

    public boolean canBook() {
        return (!canceled) && getRemaining() > 0;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public int getBooked() {
        return booked;
    }

    public void setBooked(int booked) {
        this.booked = booked;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public String toString() {
        return doctorId + " " + date + " " + time.getLabel() + " " + booked + "/" + maxCount;
    }
}
