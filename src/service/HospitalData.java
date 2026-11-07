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

    private void createSampleData() {

        addDepartment("D01", "心血管内科", "3F", "302", "诊治高血压、冠心病、心律失常",
                new String[]{"胸痛", "胸闷", "心悸", "气短"}, new int[]{9, 9, 7, 6});
        addDepartment("D02", "呼吸内科", "3F", "305", "诊治咳嗽、肺炎、哮喘",
                new String[]{"咳嗽", "咳痰", "气短", "发热"}, new int[]{9, 8, 7, 5});
        addDepartment("D03", "消化内科", "3F", "308", "诊治胃炎、腹痛、腹泻",
                new String[]{"腹痛", "腹泻", "恶心", "反酸"}, new int[]{9, 8, 7, 8});
        addDepartment("D04", "神经内科", "4F", "402", "诊治头痛、眩晕、失眠",
                new String[]{"头痛", "头晕", "失眠", "肢体麻木"}, new int[]{9, 8, 7, 7});
        addDepartment("D05", "儿科", "2F", "201", "诊治儿童发热、咳嗽、腹泻",
                new String[]{"发热", "咳嗽", "腹泻", "食欲差"}, new int[]{8, 7, 7, 6});
        addDepartment("D06", "骨科", "4F", "408", "诊治骨折、关节疼痛、腰背痛",
                new String[]{"关节疼痛", "腰背痛", "骨折", "肿胀"}, new int[]{9, 9, 8, 6});
        addDoctor("DR001", "张医生", "D01", "主任医师", "擅长冠心病、高血压诊治", "胸痛,胸闷");
        addDoctor("DR002", "李医生", "D01", "主治医师", "擅长心律失常诊治", "心悸,气短");
        addDoctor("DR003", "王医生", "D02", "主任医师", "擅长慢阻肺、哮喘诊治", "咳嗽,咳痰");
        addDoctor("DR004", "赵医生", "D02", "主治医师", "擅长肺部感染诊治", "发热,咳嗽");
        addDoctor("DR005", "刘医生", "D03", "副主任医师", "擅长胃病、肠炎诊治", "腹痛,腹泻");
        addDoctor("DR006", "陈医生", "D03", "主治医师", "擅长肝胆疾病诊治", "恶心,反酸");
        addDoctor("DR007", "杨医生", "D04", "主任医师", "擅长头痛、眩晕诊治", "头痛,头晕");
        addDoctor("DR008", "周医生", "D04", "主治医师", "擅长睡眠障碍诊治", "失眠");
        addDoctor("DR009", "吴医生", "D05", "副主任医师", "擅长小儿呼吸道感染", "发热,咳嗽");
        addDoctor("DR010", "徐医生", "D05", "主治医师", "擅长小儿消化不良", "腹泻,食欲差");
        addDoctor("DR011", "孙医生", "D06", "主任医师", "擅长骨折、关节疾病", "骨折,关节疼痛");
        addDoctor("DR012", "马医生", "D06", "主治医师", "擅长腰颈痛诊治", "腰背痛");
        addPatient("P001", "患者甲", "13800000001");
        addPatient("P002", "患者乙", "13800000002");
        addPatient("P003", "患者丙", "13800000003");
        addSchedule("DR001", "2026-10-12", Time.MORNING, 20);
        addSchedule("DR001", "2026-10-12", Time.AFTERNOON, 15);
        addSchedule("DR001", "2026-10-13", Time.MORNING, 20);
        addSchedule("DR002", "2026-10-12", Time.MORNING, 15);
        addSchedule("DR003", "2026-10-12", Time.MORNING, 18);
        addSchedule("DR007", "2026-10-12", Time.AFTERNOON, 16);
        
        createRouteGraph();
    }

    private void createRouteGraph() {
        routeGraph.addVertex("门诊大厅入口", "1F");
        routeGraph.addVertex("总服务台", "1F");
        routeGraph.addVertex("1F电梯厅", "1F");
        routeGraph.addEdge("门诊大厅入口", "总服务台", 15);
        routeGraph.addEdge("总服务台", "1F电梯厅", 18);
        //电梯
        String[] halls = {"1F电梯厅", "2F电梯厅", "3F电梯厅", "4F电梯厅"};
        for (int i = 0; i < halls.length; i++) {
            routeGraph.addVertex(halls[i], halls[i].substring(0, 2));
            if (i > 0) {
                routeGraph.addEdge(halls[i - 1], halls[i], 12);
            }
        }
        // 各层走廊 + 科室房间
        addFloorRooms("2F", "儿科", "201");
        addFloorRooms("3F", "心血管内科", "302");
        routeGraph.addEdge("3F走廊东", "呼吸内科-3F305", 9);
        routeGraph.addEdge("3F走廊东", "消化内科-3F308", 7);
        addFloorRooms("4F", "神经内科", "402");
        routeGraph.addEdge("4F走廊东", "骨科-4F408", 7);
        for (int i = 0; i < doctors.size(); i++) {
            Doctor doctor = doctors.get(i);
            Department d = departmentMap.get(doctor.getDepId());
            if (d != null) {
                String node = d.getName() + "-" + d.getFloor() + d.getRoom();
                if (routeGraph.getVertex(node) != null) {
                    doctorRoomMap.put(doctor.getId(), node);
                }
            }
        }
    }

    private void addFloorRooms(String floor, String firstDeptName, String firstRoom) {
        routeGraph.addVertex(floor + "电梯厅", floor);
        routeGraph.addVertex(floor + "走廊", floor);
        routeGraph.addVertex(floor + "走廊东", floor);
        routeGraph.addVertex(firstDeptName + "-" + floor + firstRoom, floor);
        routeGraph.addEdge(floor + "电梯厅", floor + "走廊", 8);
        routeGraph.addEdge(floor + "走廊", floor + "走廊东", 14);
        routeGraph.addEdge(floor + "走廊东", firstDeptName + "-" + floor + firstRoom, 6);
    }

    private void addDepartment(String id, String name, String floor, String room,
                               String description, String[] symptoms, int[] weights) {
        Department d = new Department(id, name, "门诊", floor, room, description);
        for(int i = 0; i < symptoms.length; i++){
            d.addSymptom(symptoms[i], weights[i]);
        }
        departments.add(d);
        departmentMap.put(id, d);

        symptomGraph.addDepartment(id);
        for(int i = 0; i < symptoms.length; i++){
            symptomGraph.addEdge(symptoms[i], id, weights[i]);
        }

    }

    private void addDoctor(String id, String name, String depId, String title,
                           String goodAt, String keywords){
        Doctor d = new Doctor(id, name, depId, title, goodAt);
        String[] ks = keywords.split(",");
        for(int i = 0; i < ks.length; i++){
            d.getKeywords().add(ks[i]);
        }
        Department de = departmentMap.get(depId);
        if(d != null){
            d.setDepName(de.getName());
        }
        doctors.add(d);
        doctorMap.put(id, d);

    }

    private void addPatient(String id, String name, String phone) {patients.add(new Patient(id, name, phone));}

    private void addSchedule(String doctorId, String date, Time time, int maxCount) {
        Schedule s = new Schedule(doctorId, date, time, maxCount);
        schedules.add(s);
        scheduleMap.put(s.getKey(),s);

    }

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
