package model;

public class QueueTicket {

    public static final int WAITING = 0;
    public static final int CALLING = 1;
    public static final int FINISHED = 2;
    public static final int PASSED = 3;

    private int number;
    private String patientId;
    private String patientName;
    private String doctorId;
    private int state = WAITING;
    private int missedCount = 0;

    public QueueTicket(int number, String patientId, String patientName, String doctorId) {
        this.number = number;
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctorId = doctorId;
    }
    public String getStateName() {
        if (state == WAITING) {
            return "等待中";
        }
        if (state == CALLING) {
            return "叫号中";
        }
        if (state == FINISHED) {
            return "已就诊";
        }
        return "已过号";
    }

    public int getNumber() {
        return number;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getMissedCount() {
        return missedCount;
    }

    public void addMissed() {
        missedCount = missedCount + 1;
    }

    public String toString() {
        return number + " 号 " + patientName + " [" + getStateName() + "]";
    }

}
