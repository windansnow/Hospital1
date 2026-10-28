package model;

import java.util.ArrayList;

public class Doctor {

    private String id;
    private String name;
    private String depId;
    private String depName;
    private String title;//主任/副主任/主治
    private String goodAt;//擅长领域
    private ArrayList<String> keywords = new ArrayList<String>();

    public Doctor(String id, String name, String depId, String title, String goodAt) {
        this.id = id;
        this.name = name;
        this.depId = depId;
        this.title = title;
        this.goodAt = goodAt;
    }

    public int getTitleRank() {
        if (title.indexOf("主任") >= 0) {
            return 4;
        }
        if (title.indexOf("副主任") >= 0) {
            return 3;
        }
        if (title.indexOf("主治") >= 0) {
            return 2;
        }
        return 1;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepId() {
        return depId;
    }

    public String getDepName() {
        return depName;
    }

    public String getTitle() {
        return title;
    }

    public String getGoodAt() {
        return goodAt;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGoodAt(String goodAt) {
        this.goodAt = goodAt;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }
    public String toString() {
        return id + "姓名：" + name + " " + title;
    }
}
