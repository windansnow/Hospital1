package datastruct;

import java.util.ArrayList;
import java.util.Stack;

public class WeightedGraph {

    public static class Edge {
        public int to;
        public float weight;//距离

        public Edge(int to, float weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    public static class Vertex {
        public int id;//编号
        public String name;
        public String floor;
        public ArrayList<Edge> edges = new ArrayList<Edge>();

        public Vertex(int id, String name) {
            this(id, name, "");
        }

        public Vertex(int id, String name, String floor) {
            this.id = id;
            this.name = name;
            this.floor = floor;
        }
    }

    private ArrayList<Vertex> vertices = new ArrayList<Vertex>();

    public class Path {
        public ArrayList<String> nodes = new ArrayList<String>();
        public float totalWeight;
        public boolean ifFound;
    }



    public int addVertex(String name, String floor){
        int index = findVertexIndex(name);
        if(index >= 0){
            updateVertexInfo(index, floor);
            return index;
        }
        int id = vertices.size();
        vertices.add(new Vertex(id, name, floor));
        return id;
    }

    private void updateVertexInfo(int index, String floor){
        Vertex v = vertices.get(index);
        if(floor != null && floor.length() != 0){
            if(v.floor == null || v.floor.length() == 0){
                v.floor = floor;
            }
        }
    }

    public Vertex getVertex(String name){
        int index = findVertexIndex(name);
        if(index < 0){
            return null;
        }
        return vertices.get(index);
    }
    private int findVertexIndex(String name){
        for(int i = 0; i < vertices.size(); i++){
            if(vertices.get(i).name.equals(name)){
                return i;
            }
        }
        return -1;
    }

    public boolean hasEdge(String nameA, String nameB) {
        int a = findVertexIndex(nameA);
        int b = findVertexIndex(nameB);
        if (a < 0 || b < 0) {
            return false;
        }
        ArrayList<Edge> edges = vertices.get(a).edges;
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).to == b) {
                return true;
            }
        }
        return false;
    }

    public void addEdge(String nameA, String nameB, float weight){
        if (hasEdge(nameA, nameB)) {
            return;
        }
        int a = addVertex(nameA, "");
        if (a < 0) {
            a = vertices.size();
            vertices.add(new Vertex(a, nameA));
        }
        int b = addVertex(nameB, "");
        if (b < 0) {
            b = vertices.size();
            vertices.add(new Vertex(b, nameB));
        }
        vertices.get(a).edges.add(new Edge(b, weight));
        vertices.get(b).edges.add(new Edge(a, weight));
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public int vertexCount() {
        return vertices.size();
    }

    public Path Dijkstra(String startNmae, String endName){
        Path path = new Path();

        int start = findVertexIndex(startNmae);
        int end = findVertexIndex(endName);
        if(start < 0 || end < 0){
            return path;
        }

        int n = vertices.size();
        float[] dist = new float[n];
        int[] prev = new int[n];
        boolean[] visited = new boolean[n];

        for(int i = 0; i < n; i++){
            dist[i] = Float.MAX_VALUE;
            prev[i] = -1;
        }
        dist[start] = 0;

        for(int i = 0; i < n; i++){
            float minDist = Float.MAX_VALUE;
            int minIndex = -1;
            for(int j = 0; j < n; j++){
                if(!visited[j] && dist[j] < minDist){
                    minDist = dist[j];
                    minIndex = j;
                }
            }
            if(minIndex == -1) break;
            visited[minIndex] = true;
            ArrayList<Edge> edges = vertices.get(minIndex).edges;
            for(int j = 0; j < edges.size(); j++){
                Edge e = edges.get(j);
                if(visited[e.to]){
                    continue;
                }
                float newDist = dist[minIndex] + e.weight;
                if(newDist < dist[e.to]){
                    dist[e.to] = newDist;
                    prev[e.to] = minIndex;
                }
            }
        }
        if(dist[end] == Float.MAX_VALUE){
            path.ifFound = false;
            return path;
        }
        Stack<String> stack = new Stack<String>();
        int p = end;
        while(p != -1){
            stack.push(vertices.get(p).name);
            if(p == start) break;
            p = prev[p];
        }
        while(!stack.isEmpty()){
            path.nodes.add(stack.pop());
        }
        path.totalWeight = dist[end];
        path.ifFound = true;
        return path;
    }


}
