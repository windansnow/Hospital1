package model;




public class Appointment {

    public static final int Status_Booked = 0;//已预约
    public static final int Status_Canceled = 1;//已取消
    public static final int Status_Visited = 2;//已就诊

    private static int counter = 1000;

    private String appId;
    private String patientId;
    private String patientName;
    private String doctorId;
    private String doctorName;
    private String depName;
    private String date;
    private Time time;
    private int status = Status_Booked;

    public Appointment(String patientId, String patientName, String doctorId, String doctorName, String departmentName,
                       String date, Time time){
        this.appId = "A" + counter;
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.depName = departmentName;
        this.date = date;
        this.time = time;
    }

    public boolean isActive(){return status == Status_Booked;}

    public String getStatus(){
        if(status == 0) return "已预约";
        if(status == 1) return "已取消";
        return "已就诊";
    }

    public String getAppId() {
        return appId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }
    public void setStatus(int status){this.status = status;}
    public String toString(){
        return "预约单号：" + appId + " | 患者：" + patientName + " | 科室：" + depName + " | 医生：" + doctorName +
                " | 时间：" + date + " " + time.getLabel() + " | 状态：" + getStatus();
    }
}
