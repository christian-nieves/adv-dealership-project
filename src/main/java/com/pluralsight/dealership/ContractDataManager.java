package com.pluralsight.dealership;


import java.io.BufferedWriter;
import java.io.FileWriter;

public class ContractDataManager {
    private String fileName = "contracts.csv"; // saves fileName in string

    public void saveContract(Contract contract) {
        try { // BufferedWriter that writes new contracts in file
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));

            Vehicle vehicle = contract.getVehicle(); // get vehicle from contract

            String vehicleInfo = vehicle.getVin() + "|" + vehicle.getYear() + "|" + vehicle.getMake() + "|" +
                    vehicle.getModel() + "|" + vehicle.getVehicleType() + "|" + vehicle.getColor() + "|" +
                    vehicle.getOdometer() + "|" + vehicle.getPrice();

            if (contract instanceof SalesContract) { // check if its a sale
                SalesContract salesContract = (SalesContract) contract; // writes sale in file
                bufferedWriter.write("SALE|" + salesContract.getDate() + "|" + salesContract.getCustomerName() + "|" + salesContract.getCustomerEmail() + "|" + vehicleInfo + "|" +
                        salesContract.getSalesTaxAmount() + "|" + salesContract.getRecordingFee() + "|" + salesContract.getProcessingFee() + "|" + salesContract.getTotalPrice() + "|" +
                        (salesContract.isFinanceOption() ? "YES" : "NO") + "|" + salesContract.getMonthlyPayment());
                        bufferedWriter.newLine();

            } else if (contract instanceof LeaseContract) { // check if its a lease
                LeaseContract leaseContract = (LeaseContract) contract; // writes lease in file
                bufferedWriter.write("LEASE|" + leaseContract.getDate() + "|" + leaseContract.getCustomerName() + "|" + leaseContract.getCustomerEmail() + "|" + vehicleInfo + "|" +
                        leaseContract.getExpectedEndingValue() + "|" + leaseContract.getLeaseFee() + "|" + leaseContract.getTotalPrice() + "|" + leaseContract.getMonthlyPayment());
                        bufferedWriter.newLine();
            }

            bufferedWriter.close(); // this closes the write
            System.out.println("Contract saved successfully!"); // tells user that the contract was saved

        } catch (Exception e) {
            System.err.println("Error saving contract: " + e.getMessage()); // prints error in red to alert user that something went wrong
        }
        }
    }

