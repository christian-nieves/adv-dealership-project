package com.pluralsight.dealership;

public class LeaseContract extends Contract {
    private double expectedEndingValue;
    private double leaseFee;

    // Constructor
    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicle) {
        super(date, customerName, customerEmail, vehicle); // Calls contract constructor
        this.expectedEndingValue = vehicle.getPrice() * 0.50; // Half of price
        this.leaseFee = vehicle.getPrice() * 0.07; // 7 percent of price
    }

    // Getters
    public double getExpectedEndingValue() {
        return expectedEndingValue;
    }

    public double getLeaseFee() {
        return leaseFee;
    }

    // Override Methods
    @Override
    public double getTotalPrice() {
        return getVehicle().getPrice() + leaseFee; // returns total costs including the fee
    }

    @Override
    public double getMonthlyPayment() {
        double monthlyRate = 0.04 / 36; // converts 4 percent rate to monthly
        int months = 36; // lease month amount

        double totalInterest = getTotalPrice() * monthlyRate; // interest on the full amount
        double loanRate = 1 - Math.pow(1 + monthlyRate, -months); // how much the loan decreases over time
        return totalInterest / loanRate; // divide to get monthly payment
    }
}
