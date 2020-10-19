package edu.txstate.e_e106.rentalcarappajax;

import org.json.JSONObject;

public class RentalCar {

    private int id;
    private String name;
    private String brand;
    private String color;
    private float rentCost;

    public RentalCar(JSONObject carObject){

        try{
            this.id = carObject.getInt("id");
            this.name = carObject.getString("name");
            this.brand = carObject.getString("brand");
            this.color = carObject.getString("color");
            this.rentCost = (float)carObject.getDouble("rent");
        }
        catch(Exception ex){ex.printStackTrace();}

    }

    public RentalCar(int id, String name, String brand, String color, float rentCost) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.color = color;
        this.rentCost = rentCost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getRentCost() {
        return rentCost;
    }

    public void setRentCost(float rentCost) {
        this.rentCost = rentCost;
    }

    @Override
    public String toString(){return "ID: " + this.id + "\nCar: " + this.brand + " " + this.name;}
}
