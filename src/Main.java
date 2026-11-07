import model.Appointment;
import model.Department;
import model.Doctor;
import model.Patient;
import model.Schedule;
import model.Time;
import model.TriageResult;
import service.AppointmentService;
import service.DoctorService;
import service.HospitalData;
import service.ReferralService;
import service.RouteService;
import service.TriageService;
import java.util.ArrayList;

public class Main{
    private static HospitalData data;
    private static TriageService triageService;
    private static DoctorService doctorService;
    private static AppointmentService appointmentService;
    private static RouteService routeService;
    private static ReferralService referralService;
    public static void main(String[] args){
        init();
        printDataSummary();
        printDepartments();
        printDoctors();
        printTriage();
        printSchedules();
        printBooking();
        printAppointments();
        printRoutes();
        printReferral();
        System.out.println();
        System.out.println("演示结束。");

    }
    private static void init(){
        data = new HospitalData();
        triageService = new TriageService(data);
        doctorService = new DoctorService(data);
        appointmentService = new AppointmentService(data);
        routeService = new RouteService(data);
        referralService = new ReferralService(data);

    }
    private static void section(String title) {
        System.out.println();
        System.out.println(title + "\n");
    }
    private static void printDataSummary(){
        section("1.数据概览");
        System.out.println(data);
    }
    private static void printDepartments() {
        section("2. 科室列表");
        ArrayList<Department> departments = data.getDepartments();
        for (int i = 0; i < departments.size(); i++) {
            Department d = departments.get(i);
            System.out.println(d.getId() + " " + d.getName()
                    + "　位置：" + d.getLocation()
                    + "　医生 " + data.getDoctorsByDepartment(d.getId()).size() + " 人");
            System.out.println("     简介：" + d.getDescription());
            StringBuilder symptoms = new StringBuilder("     常见症状：");
            for (int k = 0; k < d.getSymptoms().size(); k++) {
                if (k > 0) {
                    symptoms.append("、");
                }
                symptoms.append(d.getSymptoms().get(k));
                symptoms.append("(").append(d.getWeights().get(k)).append(")");
            }
            System.out.println(symptoms.toString());
        }
    }
    // ==================== 3. 医生查询 ====================
    private static void printDoctors() {
        section("3. 医生查询");
        System.out.println("-- 按科室查：D01 心血管内科");
        printDoctorList(doctorService.query("D01", null, null));
        System.out.println("-- 按职称查：主治医师");
        printDoctorList(doctorService.query(null, "主治医师", null));
        System.out.println("-- 按擅长领域查：头痛");
        printDoctorList(doctorService.query(null, null, "头痛"));
    }
    private static void printDoctorList(ArrayList<Doctor> list) {
        if (list.size() == 0) {
            System.out.println("   没有符合条件的医生");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            Doctor d = list.get(i);
            System.out.println("   " + d.getId() + " " + d.getName()
                    + "　" + d.getTitle()
                    + "　科室：" + d.getDepName()
                    + "　擅长：" + d.getGoodAt());
        }
    }
    // ==================== 4. 智能导诊 ====================
    private static void printTriage() {
        section("4. 智能导诊");
        String[] descriptions = {
                "胸痛胸闷，活动后心悸气短",
                "发热咳嗽，孩子食欲差",
                "腹痛腹泻，还有点恶心"
        };
        for (int i = 0; i < descriptions.length; i++) {
            String text = descriptions[i];
            System.out.println();
            System.out.println("症状描述：" + text);
            ArrayList<TriageResult> results = triageService.triage(text);
            if (results.size() == 0) {
                System.out.println("   没有匹配到科室");
                continue;
            }
            int limit = Math.min(3, results.size());
            for (int k = 0; k < limit; k++) {
                TriageResult r = results.get(k);
                System.out.println("   " + (k + 1) + ". " + r.department.getName()
                        + "　匹配度 " + r.matchPercent()
                        + "　综合分 " + String.format("%.1f", r.score)
                        + "　有号医生 " + r.freeDoctors + " 人");
                System.out.println("      " + r.getReason());
            }
        }
    }
    // ==================== 5. 排班与号源 ====================
    private static void printSchedules() {
        section("5. 医生排班与号源");
        ArrayList<Schedule> schedules = data.getSchedules();
        for (int i = 0; i < schedules.size(); i++) {
            Schedule s = schedules.get(i);
            Doctor doctor = data.getDoctor(s.getDoctorId());
            String doctorName = doctor == null ? s.getDoctorId() : doctor.getName();
            System.out.println(doctorName + "（" + s.getDoctorId() + "）　"
                    + s.getDate() + " " + s.getTime().getLabel()
                    + " " + s.getTime().getRange()
                    + "　已约 " + s.getBooked() + "/" + s.getMaxCount()
                    + "　剩余 " + s.getRemaining()
                    + "　" + (s.canBook() ? "可预约" : "不可预约"));
        }
    }
    // ==================== 6. 在线预约 ====================
    private static void printBooking() {
        section("6. 在线预约");
        String date = "2026-10-12";
        Time time = Time.MORNING;
        System.out.println("-- 患者 P001 预约 DR001 张医生，" + date + " " + time.getLabel());
        AppointmentService.bookResult first = appointmentService.book("P001", "DR001", date, time);
        System.out.println("   结果：" + first.message);
        if (first.success) {
            System.out.println("   预约凭证：");
            System.out.println("     " + first.appointment);
            Doctor doctor = data.getDoctor("DR001");
            Department department = data.getDepartment(doctor.getDepId());
            System.out.println("     就诊地点：" + department.getLocation());
            System.out.println("     请提前 15 分钟到科室分诊台取号");
        }
        System.out.println("   该时段剩余号源：" + appointmentService.getRemain("DR001", date, time));
        System.out.println();
        System.out.println("-- 同一患者再约同一时段（应该被拒绝）");
        AppointmentService.bookResult twice = appointmentService.book("P001", "DR001", date, time);
        System.out.println("   结果：" + twice.message);
        System.out.println();
        System.out.println("-- 患者 P002 预约 DR003 王医生，同一时段");
        AppointmentService.bookResult second = appointmentService.book("P002", "DR003", date, time);
        System.out.println("   结果：" + second.message);
        if (second.success) {
            System.out.println("     " + second.appointment);
        }
        System.out.println();
        System.out.println("-- 患者 P003 预约 DR001 张医生，下午");
        AppointmentService.bookResult third = appointmentService.book("P003", "DR001", date, Time.AFTERNOON);
        System.out.println("   结果：" + third.message);
        if (third.success) {
            System.out.println("     " + third.appointment);
        }
    }
    // ==================== 7. 预约记录 ====================
    private static void printAppointments() {
        section("7. 患者的预约记录");
        ArrayList<Patient> patients = data.getPatients();
        for (int i = 0; i < patients.size(); i++) {
            Patient p = patients.get(i);
            System.out.println(p.getId() + " " + p.getName()
                    + "　共 " + p.getAppointments().size() + " 条预约");
            for (int k = 0; k < p.getAppointments().size(); k++) {
                System.out.println("     " + p.getAppointments().get(k));
            }
        }
    }
    // ==================== 8. 就诊路线 ====================
    private static void printRoutes() {
        section("8. 就诊路线");
        String from = "门诊大厅入口";
        String[] doctorIds = {"DR001", "DR011"};
        for (int i = 0; i < doctorIds.length; i++) {
            Doctor doctor = data.getDoctor(doctorIds[i]);
            System.out.println("-- " + from + " → " + doctor.getName()
                    + "（" + doctor.getDepName() + "）");
            System.out.println(routeService.getRoute(from, doctor));
        }
    }
    // ==================== 9. 转诊推荐 ====================
    private static void printReferral() {
        section("9. 转诊推荐");
        String[] depIds = {"D01", "D03"};
        for (int i = 0; i < depIds.length; i++) {
            Department d = data.getDepartment(depIds[i]);
            boolean busy = referralService.isBusy(depIds[i]);
            System.out.println("-- " + d.getName() + "：" + (busy ? "号源紧张" : "还有号"));
            ArrayList<ReferralService.Suggestion> suggestions = referralService.suggest(d);
            if (suggestions.size() == 0) {
                System.out.println("   暂无相近且有号的科室");
            } else {
                for (int k = 0; k < suggestions.size(); k++) {
                    System.out.println("   " + suggestions.get(k).getText());
                }
            }
        }
    }



}

