package com.example.freelancingapplication;

import com.google.firebase.database.IgnoreExtraProperties;
//this is modl clss
@IgnoreExtraProperties
public class User {
    String job_id, job_name, Location, Field, oname, Shift, recruiter_id, Mode;

    public User() {
        // Default constructor required for Firebase
    }

    public User(String job_id, String job_name, String location, String oname, String Shift, String field, String recruiter_id, String Mode) {
        this.job_id = job_id;
        this.job_name = job_name;
        this.Location = location;
        this.oname = oname;
        this.Shift = Shift;
        this.Field = field;
        this.recruiter_id = recruiter_id;
        this.Mode = Mode;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getOname() {
        return oname;
    }

    public void setOname(String oname) {
        this.oname = oname;
    }

    public String getShift() {
        return Shift;
    }

    public void setShift(String shift) {
        this.Shift = shift;
    }

    public String getField() {
        return Field;
    }

    public void setField(String field) {
        this.Field = field;
    }

    public String getRecruiter_id() {
        return recruiter_id;
    }

    public void setRecruiter_id(String recruiter_id) {
        this.recruiter_id = recruiter_id;
    }

    public String getMode() {
        return Mode;
    }

    public void setMode(String mode) {
        this.Mode = mode;
    }
}
