import java.sql.*;
import java.util.Scanner;

class TicketManager {

    public void manageTickets(Connection connection, Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("--- Manage Tickets ---");
            System.out.println("1. Book Ticket");
            System.out.println("2. View Tickets");
            System.out.println("3. Cancel Ticket");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1 -> bookTicket(connection, scanner);
                    case 2 -> viewTickets(connection);
                    case 3 -> cancelTicket(connection, scanner);
                    case 4 -> exit = true;
                    default -> System.out.println("Invalid option.");
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    private void bookTicket(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Train ID: ");
        int trainId = scanner.nextInt();
        System.out.print("Enter Passenger ID: ");
        int passengerId = scanner.nextInt();

        Ticket ticket = new Ticket(trainId, passengerId);

        String checkQuery = "SELECT available_tickets FROM trains WHERE train_id = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, trainId);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt("available_tickets") > 0) {
                    String insertQuery = "INSERT INTO tickets (train_id, passenger_id) VALUES (?, ?)";
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                        insertStmt.setInt(1, ticket.getTrainId());
                        insertStmt.setInt(2, ticket.getPassengerId());
                        insertStmt.executeUpdate();

                        String updateQuery = "UPDATE trains SET available_tickets = available_tickets - 1 WHERE train_id = ?";
                        try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                            updateStmt.setInt(1, ticket.getTrainId());
                            updateStmt.executeUpdate();
                        }

                        System.out.println("Ticket booked successfully.");
                    }
                } else {
                    System.out.println("No tickets available or train not found.");
                }
            }
        }
    }

    private void viewTickets(Connection connection) throws SQLException {
        String query = "SELECT * FROM tickets";
        try (PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Ticket ticket = new Ticket(
                        rs.getInt("ticket_id"),
                        rs.getInt("train_id"),
                        rs.getInt("passenger_id")
                );
                System.out.println("Ticket ID: " + ticket.getTicketId() + ", Train ID: " + ticket.getTrainId() + ", Passenger ID: " + ticket.getPassengerId());
            }
        }
    }

    private void cancelTicket(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Ticket ID to cancel: ");
        int ticketId = scanner.nextInt();

        String findTrainQuery = "SELECT train_id FROM tickets WHERE ticket_id = ?";
        try (PreparedStatement findStmt = connection.prepareStatement(findTrainQuery)) {
            findStmt.setInt(1, ticketId);
            try (ResultSet rs = findStmt.executeQuery()) {
                if (rs.next()) {
                    int trainId = rs.getInt("train_id");

                    String deleteQuery = "DELETE FROM tickets WHERE ticket_id = ?";
                    try (PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
                        deleteStmt.setInt(1, ticketId);
                        int rowsDeleted = deleteStmt.executeUpdate();

                        if (rowsDeleted > 0) {

                            String updateQuery = "UPDATE trains SET available_tickets = available_tickets + 1 WHERE train_id = ?";
                            try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                                updateStmt.setInt(1, trainId);
                                updateStmt.executeUpdate();
                            }

                            System.out.println("Ticket canceled successfully.");
                        } else {
                            System.out.println("Ticket not found.");
                        }
                    }
                } else {
                    System.out.println("Ticket not found.");
                }
            }
        }
    }
}

