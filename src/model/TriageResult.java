package model;

import model.Department;
import java.util.*;

public class TriageResult {

    public Department department;
    public double match;
    public double score;
    public ArrayList<String> hitSymptoms = new ArrayList<String>();
    public int freeDoctors;

    public String matchPercent(){return String.format("%.1f", match *100) + "%";}

    public String getReason(){
        String text = "命中症状：";
        for(int i = 0; i < hitSymptoms.size(); i++){
            if(i > 0){
                text = text +"、";

            }
            text = text + hitSymptoms.get(i);
        }
        text = text +  "；匹配度 " + matchPercent() + "；有号医生 " + freeDoctors + " 人";
        return text;
    }


}
