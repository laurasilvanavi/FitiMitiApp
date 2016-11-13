package com.example.laurute.fitimitiapp.Object;

/**
 * Created by Kristaliukas on 13/11/2016.
 */

public class Task {

    int _id;
    String _description;
    String _type;
    boolean _partner;

    public Task() {

    }

    public Task(int _id, String _description, String _type, boolean _partner) {
        this._id = _id;
        this._description = _description;
        this._type = _type;
        this._partner = _partner;
    }

    public Task(int _id, String _description, String _type) {
        this._id = _id;
        this._description = _description;
        this._type = _type;
        this._partner = false;
    }

    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public boolean is_partner() {
        return _partner;
    }

    public void set_partner(boolean _partner) {
        this._partner = _partner;
    }
}