package com.pluralsight.dealership;

public class SalesContract extends Contract {
    private double salesTaxAmount;
    private double recordingFee;
    private double processingFee;
    private boolean financeOption;

    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicle, double salesTaxAmount, double recordingFee, double processingFee, boolean financeOption) {
        super(date, customerName, customerEmail, vehicle);
        this.salesTaxAmount = vehicle.getPrice() * 0.05;
        this.recordingFee = 100;
        this.processingFee = vehicle.getPrice() < 10000 ? 295.00 : 495.00; // if the vehicle price is under 10k the fee is 295, if not it's 495
        this.financeOption = financeOption;
    }
}
