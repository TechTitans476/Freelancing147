package com.example.freelancingapplication;

import java.util.Map;

public class Jobb {

    private String job_id;
    private String Location;
    private String Field;
    private String Mode;
    private String job_name;
    private String oname;
    private String recruiter_id;
    private String Shift;

    // Other attributes...

    private Map<String, Boolean> applicants;

    // Getter and Setter for applicants
    public Map<String, Boolean> getApplicants() {
        return applicants;
    }

    // Other getters and setters for other fields if needed


    // Getter and Setter for job_id
    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    // Getter and Setter for Location
    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        this.Location = location;
    }

    // Getter and Setter for field
    public String getField() {
        return Field;
    }

    public void setField(String field) {
        this.Field = field;
    }

    // Getter and Setter for mode
    public String getMode() {
        return Mode;
    }

    public void setMode(String mode) {
        this.Mode = mode;
    }

    // Getter and Setter for job_name
    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }

    // Getter and Setter for oname
    public String getOname() {
        return oname;
    }

    public void setOname(String oname) {
        this.oname = oname;
    }

    // Getter and Setter for recruiter_id
    public String getRecruiter_id() {
        return recruiter_id;
    }

    public void setRecruiter_id(String recruiter_id) {
        this.recruiter_id = recruiter_id;
    }

    // Getter and Setter for shift
    public String getShift() {
        return Shift;
    }

    public void setShift(String shift) {
        this.Shift = shift;
    }

    // Add other getters and setters for other fields if needed
}
