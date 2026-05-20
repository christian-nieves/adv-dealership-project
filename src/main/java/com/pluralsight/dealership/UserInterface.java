package com.pluralsight.dealership;

import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private Dealership dealership;
    private Scanner scanner;

    public UserInterface() {
        scanner = new Scanner(System.in);
    }

    public void display() {
        init();
        boolean quit = false;
        while (!quit) {
            System.out.println("---------- Menu ----------");
            System.out.println("1. Get vehicles by price");
            System.out.println("2. Get vehicles by make and model");
            System.out.println("3. Get vehicles by year");
            System.out.println("4. Get vehicles by color");
            System.out.println("5. Get vehicles by mileage");
            System.out.println("6. Get vehicles by type");
            System.out.println("7. Get all vehicles");
            System.out.println("8. Add vehicle");
            System.out.println("9. Remove vehicle");
            System.out.println("10. Sell/Lease Vehicle"); // added new option
            System.out.println("99. Quit");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    processGetByPriceRequest();
                    break;
                case "2":
                    processGetByMakeModelRequest();
                    break;
                case "3":
                    processGetByYearRequest();
                    break;
                case "4":
                    processGetByColorRequest();
                    break;
                case "5":
                    processGetByMileageRequest();
                    break;
                case "6":
                    processGetByVehicleTypeRequest();
                    break;
                case "7":
                    processGetAllVehiclesRequest();
                    break;
                case "8":
                    processAddVehicleRequest();
                    break;
                case "9":
                    processRemoveVehicleRequest();
                    break;
                case "10":
                    processSellLeaseRequest();
                    break;
                case "99":
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void processSellLeaseRequest() {
        System.out.print("Enter vehicle vin: ");
        int vin = scanner.nextInt();
        scanner.nextLine();

        // find the vehicle in inventory
        Vehicle vehicle = null;
        for (Vehicle vehicle1 : dealership.getAllVehicles()) {
            if (vehicle1.getVin() == vin) {
                vehicle = vehicle1; // save the found vehicle
                break;
            }
        }

        if (vehicle == null) { // vehicle not found
            System.err.println("Vehicle not found."); // print error message in red
            return;
        }

        // store customer info
        System.out.print("Enter date (YYYYMMDD): ");
        String date = scanner.nextLine(); // store date

        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine(); // store name

        System.out.print("Enter customer email: ");
        String customerEmail = scanner.nextLine(); // store email

        // ask user if sale or lease
        System.out.print("Sale or Lease? (S/L): ");
        String saleOrLease = scanner.nextLine();

        Contract contract = null;

        if (saleOrLease.equalsIgnoreCase("S")) { // sale
            System.out.print("Do you want to finance? (yes/no): ");
            String financeInput = scanner.nextLine();
            boolean financeOption = financeInput.equalsIgnoreCase("yes"); // if user inputs "yes" then they will finance the vehicle
            contract = new SalesContract(date, customerName, customerEmail, vehicle, financeOption); // creates new sale contract

        } else if (saleOrLease.equalsIgnoreCase("L")) { // lease
            int currentYear = 2026;
            if (currentYear - vehicle.getYear() > 3) { // check if vehicle isnt over 3 years old
                System.err.println("Sorry, you cannot lease a vehicle over 3 years old."); // message in red alerting user they cant lease a car thats over 3 years old
                return;
            }
            contract = new LeaseContract(date, customerName, customerEmail, vehicle); // creates new lease contract
        }

        if (contract != null) {
            // Contract summary / receipt
            System.out.println("\n===== Contract Summary =====");
            System.out.println("Customer: " + customerName);
            System.out.println("Email: " + customerEmail);
            System.out.println("Vehicle: " + vehicle.getYear() + " " + vehicle.getMake() + " " + vehicle.getModel());
            System.out.printf("Total Price: $%,.2f%n", contract.getTotalPrice());

            if (contract.getMonthlyPayment() > 0) { // only show if financing or leasing
                System.out.printf("Monthly Payment: $%,.2f%n", contract.getMonthlyPayment());
            } else {
                System.out.println("Car was paid in full!");
            }
            System.out.println("============================\n");

            ContractDataManager contractDataManager = new ContractDataManager();
            contractDataManager.saveContract(contract); // saves contract to file
            dealership.removeVehicle(vehicle); // removes vehicle from inventory
            new DealershipFileManager().saveDealership(dealership); // saves new inventory
            System.out.println("Contract completed successfully!"); // alerts user all went well
        }
    }

    public void processGetByPriceRequest() {
        System.out.print("Enter minimum price: ");
        double min = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter maximum price: ");
        double max = scanner.nextDouble();
        scanner.nextLine();
        List<Vehicle> vehicles = dealership.getVehiclesByPrice(min, max);
        displayVehicles(vehicles);
    }

    public void processGetByMakeModelRequest() {
        System.out.print("Enter make: ");
        String make = scanner.nextLine();

        System.out.print("Enter model: ");
        String model = scanner.nextLine();
        List<Vehicle> vehicles = dealership.getVehiclesByMakeModel(make, model);
        displayVehicles(vehicles);
    }

    public void processGetByYearRequest() {
        System.out.print("Enter minimum year: ");
        int min = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter maximum year: ");
        int max = scanner.nextInt();
        scanner.nextLine();

        List<Vehicle> vehicles = dealership.getVehiclesByYear(min, max);
        displayVehicles(vehicles);
    }

    public void processGetByColorRequest() {
        System.out.print("Enter color: ");
        String color = scanner.nextLine();

        List<Vehicle> vehicles = dealership.getVehiclesByColor(color);
        displayVehicles(vehicles);
    }

    public void processGetByMileageRequest() {
        System.out.print("Enter minimum mileage: ");
        int min = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter maximum mileage: ");
        int max = scanner.nextInt();
        scanner.nextLine();

        List<Vehicle> vehicles = dealership.getVehiclesByMileage(min, max);
        displayVehicles(vehicles);
    }

    public void processGetByVehicleTypeRequest() {
        System.out.print("Enter vehicle type: ");
        String vehicleType = scanner.nextLine();

        List<Vehicle> vehicles = dealership.getVehiclesByType(vehicleType);
        displayVehicles(vehicles);
    }

    public void processGetAllVehiclesRequest() {
        List<Vehicle> vehicles = dealership.getAllVehicles();
        displayVehicles(vehicles);
    }

    public void processAddVehicleRequest() {
        System.out.print("Enter vehicle vin: ");
        int vin = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter vehicle make: ");
        String make = scanner.nextLine();

        System.out.print("Enter vehicle model: ");
        String model = scanner.nextLine();

        System.out.print("Enter vehicle year: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter vehicle price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter vehicle color: ");
        String color = scanner.nextLine();

        System.out.print("Enter vehicle mileage: ");
        int mileage = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter vehicle type (Car, Truck, SUV, Motorcycle): ");
        String type = scanner.nextLine();

        Vehicle vehicle = new Vehicle(vin, year, make, model, type, color, mileage, price);

        dealership.addVehicle(vehicle);
        System.out.println("Vehicle added successfully!");
        DealershipFileManager manager = new DealershipFileManager();
        manager.saveDealership(dealership);
    }

    public void processRemoveVehicleRequest() {
        System.out.print("Enter the VIN of the vehicle you wish to remove: ");
        int vin = scanner.nextInt();
        scanner.nextLine();

        boolean vehicleRemoved = false;
        for (Vehicle vehicle : dealership.getAllVehicles()) {
            if (vehicle.getVin() == vin) {
                dealership.removeVehicle(vehicle);
                System.out.println("Vehicle removed successfully!");
                vehicleRemoved = true;
                break;
            }
        }

        if (!vehicleRemoved) {
            System.out.println("Vehicle not found. Please try again.");
            return;
        }

        DealershipFileManager manager = new DealershipFileManager();
        manager.saveDealership(dealership);
    }

    private void init() {
        DealershipFileManager manager = new DealershipFileManager();
        dealership = manager.getDealership();
    }

    private void displayVehicles(List<Vehicle> vehicles) {
        if (vehicles == null || vehicles.isEmpty()) {
            System.out.println("No vehicle found. Please try again.");
            return;
        }
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle.toString());
        }
    }

}
