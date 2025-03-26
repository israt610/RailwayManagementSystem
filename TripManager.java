import java.sql.*;
import java.util.Scanner;

class TripManager {

    public void manage(Connection connection, Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("--- Manage Trips ---");
            System.out.println("1. Add Trip");
            System.out.println("2. View Trips");
            System.out.println("3. Update Trip");
            System.out.println("4. Delete Trip");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1 -> addTrip(connection, scanner);
                    case 2 -> viewTrips(connection);
                    case 3 -> updateTrip(connection, scanner);
                    case 4 -> deleteTrip(connection, scanner);
                    case 5 -> exit = true;
                    default -> System.out.println("Invalid option.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void addTrip(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Trip Date (YYYY-MM-DD): ");
        String tripDate = scanner.nextLine();
        System.out.print("Enter Train ID: ");
        int trainId = scanner.nextInt();

        Trip trip = new Trip(tripDate, trainId);

        String query = "INSERT INTO trips (trip_date, train_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, trip.getTripDate());
            stmt.setInt(2, trip.getTrainId());
            stmt.executeUpdate();
            System.out.println("Trip added successfully.");
        }
    }

    private void viewTrips(Connection connection) throws SQLException {
        String query = "SELECT * FROM trips";
        try (PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Trip trip = new Trip(
                        rs.getInt("trip_id"),
                        rs.getString("trip_date"),
                        rs.getInt("train_id")
                );
                System.out.println("Trip ID: " + trip.getTripId() + ", Date: " + trip.getTripDate() + ", Train ID: " + trip.getTrainId());
            }
        }
    }

    private void updateTrip(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Trip ID to update: ");
        int tripId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new Trip Date (YYYY-MM-DD): ");
        String tripDate = scanner.nextLine();
        System.out.print("Enter new Train ID: ");
        int trainId = scanner.nextInt();

        Trip trip = new Trip(tripId, tripDate, trainId);

        String query = "UPDATE trips SET trip_date = ?, train_id = ? WHERE trip_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, trip.getTripDate());
            stmt.setInt(2, trip.getTrainId());
            stmt.setInt(3, trip.getTripId());
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Trip updated successfully.");
            } else {
                System.out.println("Trip not found.");
            }
        }
    }

    private void deleteTrip(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Trip ID to delete: ");
        int tripId = scanner.nextInt();

        String query = "DELETE FROM trips WHERE trip_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, tripId);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Trip deleted successfully.");
            } else {
                System.out.println("Trip not found.");
            }
        }
    }
}
