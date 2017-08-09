package com.hfad.workout;

/**
 * Created by sirth on 10/07/2017.
 */
public class WorkoutsHelperClass {
    private int _id;
    private String _workoutname;
    private String  workoutdescription;

    public void setWorkoutdescription(String workoutdescription) {
        this.workoutdescription = workoutdescription;
    }


    public String getWorkoutdescription() {
        return workoutdescription;
    }

    //Added this empty constructor in lesson 50 in case we ever want to create the object and assign it later.
    public WorkoutsHelperClass(){

    }
    public WorkoutsHelperClass(String workoutName ) {
        this._workoutname = workoutName;

    }

    public WorkoutsHelperClass(String workoutName, String workoutdescription ) {
        this._workoutname = workoutName;
        this.workoutdescription=workoutdescription;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_workoutname() {
        return _workoutname;
    }

    public void set_workoutname(String _workoutname) {
        this._workoutname = _workoutname;
    }
}
