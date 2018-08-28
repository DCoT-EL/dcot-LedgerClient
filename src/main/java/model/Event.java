package model;

public class Event {

    private String caller;
    private String role;
    private String operation;
    private String moment;


    public Event() {
    }

    public Event(String caller, String role, String operation, String moment) {
        this.caller = caller;
        this.role = role;
        this.operation = operation;
        this.moment = moment;
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getMoment() {
        return moment;
    }

    public void setMoment(String moment) {
        this.moment = moment;
    }
}
