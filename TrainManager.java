import java.sql.*;
import java.util.Scanner;

class TrainManager implements CrudOperations {

    @Override
    public void manage(Connection connection, Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("--- Manage Trains ---");
            System.out.println("1. Add Train");
            System.out.println("2. View Trains");
            System.out.println("3. Update Train");
            System.out.println("4. Delete Train");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1 -> addTrain(connection, scanner);
                    case 2 -> viewTrains(connection);
                    case 3 -> updateTrain(connection, scanner);
                    case 4 -> deleteTrain(connection, scanner);
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

    private void addTrain(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Train Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Source: ");
        String source = scanner.nextLine();
        System.out.print("Enter Destination: ");
        String destination = scanner.nextLine();
        System.out.print("Enter Available Tickets: ");
        int availableTickets = scanner.nextInt();

        Train train = new Train(name, source, destination, availableTickets);

        String query = "INSERT INTO trains (name, source, destination, available_tickets) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, train.getName());
            stmt.setString(2, train.getSource());
            stmt.setString(3, train.getDestination());
            stmt.setInt(4, train.getAvailableTickets());
            stmt.executeUpdate();
            System.out.println("Train added successfully.");
        }
    }

    private void viewTrains(Connection connection) throws SQLException {
        String query = "SELECT * FROM trains";
        try (PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Train train = new Train(
                        rs.getInt("train_id"),
                        rs.getString("name"),
                        rs.getString("source"),
                        rs.getString("destination"),
                        rs.getInt("available_tickets")
                );
                System.out.println("ID: " + train.getTrainId() + ", Name: " + train.getName() + ", Source: "
                        + train.getSource() + ", Destination: " + train.getDestination()
                        + ", Available Tickets: " + train.getAvailableTickets());
            }
        }
    }

    private void updateTrain(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Train ID to update: ");
        int trainId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new Train Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new Source: ");
        String source = scanner.nextLine();
        System.out.print("Enter new Destination: ");
        String destination = scanner.nextLine();
        System.out.print("Enter new Available Tickets: ");
        int availableTickets = scanner.nextInt();

        Train train = new Train(trainId, name, source, destination, availableTickets);

        String query = "UPDATE trains SET name = ?, source = ?, destination = ?, available_tickets = ? WHERE train_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, train.getName());
            stmt.setString(2, train.getSource());
            stmt.setString(3, train.getDestination());
            stmt.setInt(4, train.getAvailableTickets());
            stmt.setInt(5, train.getTrainId());
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Train updated successfully.");
            } else {
                System.out.println("Train not found.");
            }
        }
    }

    private void deleteTrain(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Train ID to delete: ");
        int trainId = scanner.nextInt();

        String query = "DELETE FROM trains WHERE train_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, trainId);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Train deleted successfully.");
            } else {
                System.out.println("Train not found.");
            }
        }
    }
}
