package datastruct;

import java.util.ArrayList;

public class SymptomGraph {
    public static class Edge {
        public String symptom;//症状
        public String id;//科室编号
        public int weight;

        public Edge(String symptom, String id, int weight) {
            this.symptom = symptom;
            this.id = id;
            this.weight = weight;
        }
    }

    private ArrayList<String> symptoms = new ArrayList<String>();
    private ArrayList<String> ids = new ArrayList<String>();
    private ArrayList<Edge> symptomEdges = new ArrayList<Edge>();
    private ArrayList<Edge> departmentEdges = new ArrayList<Edge>();

    public void addSymptom(String symptom){
        if(symptom == null || symptom.length() == 0)
            return;
        if(!symptoms.contains(symptom)) symptoms.add(symptom);
    }

    public void addDepartment(String id){
        if (id == null || id.length() == 0)
            return;
        if (!ids.contains(id)) ids.add(id);
    }

    public void addEdge(String symptom, String id, int weight){
        addSymptom(symptom);
        addDepartment(id);
        symptomEdges.add(new Edge(symptom, id, weight));
        departmentEdges.add(new Edge(symptom, id, weight));
    }

    public int getWeight(String symptom, String id) {
        for (int i = 0; i < symptomEdges.size(); i++) {
            Edge e = symptomEdges.get(i);
            if (e.symptom.equals(symptom) && e.id.equals(id))
                return e.weight;

        }
        return 0;
    }

    public ArrayList<Edge> getSymptomEdges(String id) {
        ArrayList<Edge> result = new ArrayList<Edge>();
        for (int i = 0; i < departmentEdges.size(); i++) {
            if (departmentEdges.get(i).id.equals(id))
                result.add(departmentEdges.get(i));

        }
        return result;
    }

    public ArrayList<Edge> getDepartmentEdges(String symptom) {
        ArrayList<Edge> result = new ArrayList<Edge>();
        for (int i = 0; i < symptomEdges.size(); i++) {
            if (symptomEdges.get(i).symptom.equals(symptom))
                result.add(symptomEdges.get(i));

        }
        return result;
    }

    public double similarity(String depA, String depB){
        ArrayList<Edge> listA = getSymptomEdges(depA);
        ArrayList<Edge> listB = getSymptomEdges(depB);

        if(listA.size() == 0 || listB.size() == 0)
            return 0;
        double dot = 0;
        double lenA = 0;
        double lenB = 0;
        for(int i = 0; i < listA.size(); i++){
            Edge e = listA.get(i);
            lenA = lenA + e.weight * e.weight;
            dot = dot + e.weight * getWeight(e.symptom, depB);
        }
        for(int i = 0; i< listB.size(); i++){
            Edge e = listB.get(i);
            lenB = lenB + e.weight * e.weight;
        }
        if (lenA == 0 || lenB == 0)
            return 0;
        return dot / (Math.sqrt(lenA) * Math.sqrt(lenB));
    }

    public String toString(){
        return "症状：" + symptoms.size() + "，科室：" + ids.size() + "，关联边" + symptoms.size();
    }

}
