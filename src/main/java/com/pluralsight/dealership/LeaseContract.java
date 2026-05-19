package com.pluralsight.dealership;

public class LeaseContract extends Contract {
    private double expectedEndingValue;
    private double leaseFee;

    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicle) {
        super(date, customerName, customerEmail, vehicle);
        this.expectedEndingValue = expectedEndingValue;
        this.leaseFee = leaseFee;
    }
}
