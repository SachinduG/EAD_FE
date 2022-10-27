/**
 * @author Gimhana P.S.
 * @IT_number IT19143682
 */

package com.example.ead_fuel_app.models;

public class Queue {
    private String id;
    private String regNo;
    private String nic;
    private String fuelType;
    private String arrivedTime;
    private String departTime;
    private boolean leftEarly = false;

    public Queue(String id, String regNo, String nic, String fuelType, String arrivedTime, String departTime, boolean leftEarly) {
        this.id = id;
        this.regNo = regNo;
        this.nic = nic;
        this.fuelType = fuelType;
        this.arrivedTime = arrivedTime;
        this.departTime = departTime;
        this.leftEarly = leftEarly;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getArrivedTime() {
        return arrivedTime;
    }

    public void setArrivedTime(String arrivedTime) {
        this.arrivedTime = arrivedTime;
    }

    public String getDepartTime() {
        return departTime;
    }

    public void setDepartTime(String departTime) {
        this.departTime = departTime;
    }

    public boolean isLeftEarly() {
        return leftEarly;
    }

    public void setLeftEarly(boolean leftEarly) {
        this.leftEarly = leftEarly;
    }
}
