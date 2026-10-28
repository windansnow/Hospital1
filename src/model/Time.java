package model;

public enum Time {

    MORNING("上午", "08:00-12:00"),
    AFTERNOON("下午", "13:30-17:30"),
    EVENING("晚间", "18:00-21:00");

    private String label;
    private String range;
    Time(String label, String range){
        this.label = label;
        this.range = range;
    }

    public String getLabel() {
        return label;
    }

    public String getRange() {
        return range;
    }
}
