package com.pluralsight.dealership;

public class SalesContract extends Contract {
    private double salesTaxAmount;
    private double recordingFee;
    private double processingFee;
    private boolean financeOption;

    // Constructor
    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicle, boolean financeOption) {
        super(date, customerName, customerEmail, vehicle);
        this.salesTaxAmount = vehicle.getPrice() * 0.05;
        this.recordingFee = 100;
        this.processingFee = vehicle.getPrice() < 10000 ? 295.00 : 495.00; // if the vehicle price is under 10k the fee is 295, if not it's 495
        this.financeOption = financeOption;
    }

    // Getters and one Setter
    public double getSalesTaxAmount() {
        return salesTaxAmount;
    }

    public double getRecordingFee() {
        return recordingFee;
    }

    public double getProcessingFee() {
        return processingFee;
    }

    public boolean isFinanceOption() {
        return financeOption;
    }

    public void setFinanceOption(boolean financeOption) {
        this.financeOption = financeOption;
    }

    // Override Methods
    @Override
    public double getTotalPrice() {
       return getVehicle().getPrice() + salesTaxAmount + recordingFee + processingFee;
    } // returns total costs including tax and fees

    @Override
    public double getMonthlyPayment() {
        if (!financeOption) return 0; // if there is no loan, the customer has nothing to pay

        double totalPrice = getTotalPrice(); // storing total price
        double monthlyRate;
        int months;

        if (getVehicle().getPrice() >= 10000) { // if price is under 10k
            monthlyRate = 0.0425 / 12;          // converts interest rate monthly
            months = 48;                        // how long the loan lasts
        } else {
            monthlyRate = 0.0525 / 12;
            months = 24;
        }

        double totalInterest = totalPrice * monthlyRate; // full amount of interest
        double loanRate = 1 - Math.pow(1 + monthlyRate, -months); // how much the loan decreases over time
        return totalInterest / loanRate; // divides to get monthly payment
    }
}

