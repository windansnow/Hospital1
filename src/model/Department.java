package model;

import java.util.ArrayList;

public class Department {

    private String id;//科室编号
    private String name;
    private String building;
    private String floor;
    private String room;//房间号
    private String description;

    private ArrayList<String> symptoms = new ArrayList<String>();   // 常见症状
    private ArrayList<Integer> weights = new ArrayList<Integer>();  // 每个症状对应的权重

    public Department() {
    }

    public Department(String id, String name, String building, String floor, String room, String description) {
        this.id = id;
        this.name = name;
        this.building = building;
        this.floor = floor;
        this.room = room;
        this.description = description;
    }

    public void addSymptom(String symptom, int weight){
        symptoms.add(symptom);
        weights.add(weight);
    }

    public String getLocation(){return building + " " + floor + " " + room;}

    public int getWeightOf(String symptom){
        for(int i = 0; i < symptoms.size(); i++){
            if(symptoms.get(i).equals(symptom)){
                return weights.get(i);
            }
        }
        return 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBuilding() {
        return building;
    }

    public String getFloor() {
        return floor;
    }

    public String getRoom() {
        return room;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getSymptoms() {
        return symptoms;
    }

    public ArrayList<Integer> getWeights() {
        return weights;
    }

    public String toString(){return id + " " + name + " (" + getLocation() + ")";}
}