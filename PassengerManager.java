import java.sql.*;
import java.util.Scanner;

class PassengerManager implements CrudOperations {

    @Override
    public void manage(Connection connection, Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("--- Manage Passengers ---");
            System.out.println("1. Add Passenger");
            System.out.println("2. View Passengers");
            System.out.println("3. Update Passenger");
            System.out.println("4. Delete Passenger");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1 -> addPassenger(connection, scanner);
                    case 2 -> viewPassengers(connection);
                    case 3 -> updatePassenger(connection, scanner);
                    case 4 -> deletePassenger(connection, scanner);
                    case 5 -> {
                        System.out.println("Returning to Main Menu...");
                        exit = true;
                    }
                    default -> System.out.println("Invalid option.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void addPassenger(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Passenger Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Passenger Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Passenger Gender: ");
        String gender = scanner.nextLine();

        Passenger passenger = new Passenger(name, age, gender);

        String query = "INSERT INTO passengers (name, age, gender) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, passenger.getName());
            stmt.setInt(2, passenger.getAge());
            stmt.setString(3, passenger.getGender());
            stmt.executeUpdate();
            System.out.println("Passenger added successfully.");
        }
    }

    private void viewPassengers(Connection connection) throws SQLException {
        String query = "SELECT * FROM passengers";
        try (PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Passenger passenger = new Passenger(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender")
                );
                System.out.println("ID: " + passenger.getId() + ", Name: " + passenger.getName() + ", Age: " + passenger.getAge() + ", Gender: " + passenger.getGender());
            }
        }
    }

    private void updatePassenger(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Passenger ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new Gender: ");
        String gender = scanner.nextLine();

        String query = "UPDATE passengers SET name = ?, age = ?, gender = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setString(3, gender);
            stmt.setInt(4, id);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Passenger updated successfully.");
            } else {
                System.out.println("Passenger not found.");
            }
        }
    }

    private void deletePassenger(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Passenger ID to delete: ");
        int id = scanner.nextInt();
        String query = "DELETE FROM passengers WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Passenger deleted successfully.");
            } else {
                System.out.println("Passenger not found.");
            }
        }
    }
}
