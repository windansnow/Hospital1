package service;
import datastruct.SymptomGraph;
import model.Department;
import model.Doctor;
import model.Schedule;
import model.TriageResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class TriageService {

    private HospitalData data;
    public TriageService(HospitalData data){this.data=data;}

    public ArrayList<TriageResult> triage(String description){
        ArrayList<TriageResult> results = new ArrayList<TriageResult>();
        ArrayList<String> hit = findSymptoms(description);
        if(hit.size() == 0) return results;
        SymptomGraph graph = data.getSymptomGraph();
        ArrayList<Department> departments = data.getDepartments();

        for(int i =0; i < departments.size(); i++){
            Department d = departments.get(i);
            double weightSum = 0;
            double maxSum = 0;//分母，症状在所有科室中的最大权重之和
            ArrayList<String> matched = new ArrayList<String>();

            for(int k = 0; k < hit.size(); k++){
                String symptom = hit.get(k);
                int w = graph.getWeight(symptom, d.getId());
                maxSum += maxWeightOfSymptom(symptom);
                if(w > 0){
                    weightSum = weightSum + w;
                    matched.add(symptom + "(" + w + ")");
                }
            }
            if(matched.size() == 0){
                continue;
            }
            TriageResult r = new TriageResult();
            r.department = d;
            r.hitSymptoms = matched;
            //匹配度=权重占比*0.7+命中比例*0.3
            double weightRate = weightSum / maxSum;
            double hitRate = (double)matched.size()/hit.size();
            r.match = weightRate * 0.7 + hitRate * 0.3;
            int free = 0;
            ArrayList<Doctor> ds = data.getDoctorsByDepartment(d.getId());
            for(int k = 0; k < ds.size(); k++){
                if(hasFreeTime(ds.get(i).getId())){
                    free ++;
                }
            }
            r.freeDoctors = free;

            double freeRate = free / 3.0;
            if (freeRate > 1) freeRate = 1;
            r.score = r.match * 70 + freeRate * 15;

            results.add(r);



        }
        Collections.sort(results, new Comparator<TriageResult>() {
            public int compare(TriageResult a, TriageResult b) {
                return Double.compare(b.score, a.score);
            }
        });
        return results;
    }
    private boolean hasFreeTime(String doctorId) {
        ArrayList<Schedule> list = data.getSchedules();
        for (int i = 0; i < list.size(); i++) {
            Schedule s = list.get(i);
            if (s.getDoctorId().equals(doctorId) && s.canBook()) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> findSymptoms(String description){
        ArrayList<String> hit = new ArrayList<String>();
        ArrayList<String> all = data.getSymptomGraph().getAllSymptoms();
        for(int i = 0; i < all.size(); i++){
            String symptom = all.get(i);
            if(description.indexOf(symptom) >= 0 && !hit.contains(symptom))hit.add(symptom);
        }
        return hit;
    }

    private int maxWeightOfSymptom(String symptom){
        ArrayList<SymptomGraph.Edge> edges = data.getSymptomGraph().getDepartmentEdges(symptom);
        int max = 1;
        for(int i = 0; i < edges.size(); i++){
            if(edges.get(i).weight > max){
                max = edges.get(i).weight;
            }
        }
        return max;
    }



}
