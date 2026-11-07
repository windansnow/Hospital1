package service;

import datastruct.WeightedGraph;
import model.Department;
import model.Doctor;

import java.util.ArrayList;

public class RouteService {

    private HospitalData data;
    public RouteService(HospitalData data){this.data = data;}

    public String getRoute(String from, Doctor doctor){
        String node = data.getDoctorRoom(doctor.getId());
        if(node == null){
            Department d = data.getDepartment(doctor.getDepId());
            if(d != null)
                node = d.getName() + "-" + d.getFloor() + d.getRoom();

        }
        if(node == null || data.getRouteGraph().getVertex(node) == null)
            return "没有找到 " + doctor.getName() + " 对应的房间节点";
        WeightedGraph.Path p = data.getRouteGraph().Dijkstra(from, node);
        if(!p.ifFound){
            return "没有找到从【" + from + "】到【" + doctor.getName() + "】的路线";
        }
        String text = "起点：" + from + "\n";
        Department d = data.getDepartment(doctor.getDepId());
        if(d != null){
            text = text + "终点：" + d.getName()+ "\n";
        }
        text = text + "路线：";
        for(int i = 0; i < p.nodes.size(); i++){
            if(i > 0){
                text = text + "->";

            }
            text = text + p.nodes.get(i);
        }
        text = text + "\n总距离：" + p.totalWeight + "米";
        return text;
    }


}
