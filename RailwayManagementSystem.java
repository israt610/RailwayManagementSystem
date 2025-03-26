import java.sql.Connection;
import java.util.Scanner;

public class RailwayManagementSystem {

    public static void main(String[] args) {
        RailwayManagementSystem system = new RailwayManagementSystem();
        system.run();
    }

    public void run() {
        try (Scanner scanner = new Scanner(System.in); Connection connection = DatabaseConnection.connect()) {
            System.out.println();
            System.out.println();
            System.out.println("__________Welcome to Railway Management System!__________");

            while (true) {
                displayMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> new EmployeeManager().manage(connection, scanner);
                    case 2 -> new PassengerManager().manage(connection, scanner);
                    case 3 -> new TrainManager().manage(connection, scanner);
                    case 4 -> new TripManager().manage(connection, scanner);
                    case 5 -> new TicketManager().manageTickets(connection, scanner);
                    case 6 -> {
                        System.out.println("Exiting system. Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Manage Employees");
        System.out.println("2. Manage Passengers");
        System.out.println("3. Manage Trains");
        System.out.println("4. Manage Trips");
        System.out.println("5. Check Ticket Availability");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");
    }
}
