/**
 * @author S.M. Jayasekara
 * @IT_number IT19161648
 */

package com.example.ead_fuel_app.models;

public class Shed {
    private String regNo;
    private String name;
    private String address;
    private String password;
    private String dieselArrivalTime;
    private String dieselFinishTime;
    private boolean dieselAvailable;
    private int dieselQueueLength;
    private String petrolArrivalTime;
    private String petrolFinishTime;
    private boolean petrolAvailable;
    private int petrolQueueLength;

    public Shed(String regNo, String name, String address, String password, String dieselArrivalTime, String dieselFinishTime, boolean dieselAvailable, int dieselQueueLength, String petrolArrivalTime, String petrolFinishTime, boolean petrolAvailable, int petrolQueueLength) {
        this.regNo = regNo;
        this.name = name;
        this.address = address;
        this.password = password;
        this.dieselArrivalTime = dieselArrivalTime;
        this.dieselFinishTime = dieselFinishTime;
        this.dieselAvailable = dieselAvailable;
        this.dieselQueueLength = dieselQueueLength;
        this.petrolArrivalTime = petrolArrivalTime;
        this.petrolFinishTime = petrolFinishTime;
        this.petrolAvailable = petrolAvailable;
        this.petrolQueueLength = petrolQueueLength;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDieselArrivalTime() {
        return dieselArrivalTime;
    }

    public void setDieselArrivalTime(String dieselArrivalTime) {
        this.dieselArrivalTime = dieselArrivalTime;
    }

    public String getDieselFinishTime() {
        return dieselFinishTime;
    }

    public void setDieselFinishTime(String dieselFinishTime) {
        this.dieselFinishTime = dieselFinishTime;
    }

    public boolean isDieselAvailable() {
        return dieselAvailable;
    }

    public void setDieselAvailable(boolean dieselAvailable) {
        this.dieselAvailable = dieselAvailable;
    }

    public int getDieselQueueLength() {
        return dieselQueueLength;
    }

    public void setDieselQueueLength(int dieselQueueLength) {
        this.dieselQueueLength = dieselQueueLength;
    }

    public String getPetrolArrivalTime() {
        return petrolArrivalTime;
    }

    public void setPetrolArrivalTime(String petrolArrivalTime) {
        this.petrolArrivalTime = petrolArrivalTime;
    }

    public String getPetrolFinishTime() {
        return petrolFinishTime;
    }

    public void setPetrolFinishTime(String petrolFinishTime) {
        this.petrolFinishTime = petrolFinishTime;
    }

    public boolean isPetrolAvailable() {
        return petrolAvailable;
    }

    public void setPetrolAvailable(boolean petrolAvailable) {
        this.petrolAvailable = petrolAvailable;
    }

    public int getPetrolQueueLength() {
        return petrolQueueLength;
    }

    public void setPetrolQueueLength(int petrolQueueLength) {
        this.petrolQueueLength = petrolQueueLength;
    }
}


