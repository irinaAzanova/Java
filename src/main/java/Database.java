import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Database {
    private static Connection connection;
    private static Statement statement;

    public static void addTable() {
        try {
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS Passenger (" +
                    "survived INTEGER, " +
                    "sex TEXT, " +
                    "age FLOAT, " +
                    "ticket TEXT, " +
                    "fare FLOAT, " +
                    "embarked TEXT);"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addDataToTable(ArrayList<Passenger> passengers) {
        String sqlQuery;
        for (Passenger p : passengers) {
            sqlQuery = String.format("INSERT INTO Passenger VALUES ('%s', '%s', '%s', '%s', '%s', '%s');",
                    p.getSurvived(),
                    p.getSex(),
                    p.getAge(),
                    p.getTicket(),
                    p.getFare(),
                    p.getEmbarked()
            );

            try {
                statement.execute(sqlQuery);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void connect() throws ClassNotFoundException, SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/titanic.db");
        statement = connection.createStatement();
    }

    public static void disconnect() throws SQLException {
        statement.close();
        connection.close();
    }

    public static Map<String, Double> getTicketsCost() throws SQLException {
        Map<String, Double> ticketsCost = new HashMap<>();
        ResultSet rs = statement.executeQuery("SELECT sex, embarked, AVG(fare) FROM Passenger GROUP BY sex, embarked;");

        while (rs.next()) {
            String column = rs.getString("embarked") + " " + rs.getString("sex");
            double amount = Double.parseDouble(rs.getString("avg(fare)"));
            ticketsCost.put(column, amount);
        }
        return ticketsCost;
    }

    public static String getDeltaTicket() throws SQLException {
        ResultSet rs = statement.executeQuery(
                "SELECT (MAX(fare) - MIN(fare)) AS delta " +
                "FROM Passenger WHERE sex = 'female' AND (age >= 15 AND age <= 30)");
        return "Разница между самым дорогим и дешевым билетом у девушек:\n" + rs.getString("delta") + "\n";
    }

    public static String getListTickets() throws SQLException {
        return getWomans() + getMens();
    }

    private static String getWomans() throws SQLException {
        StringBuilder sb = new StringBuilder();
        ResultSet rs = statement.executeQuery(
                "SELECT ticket " +
                "FROM Passenger " +
                "WHERE sex = 'female' AND age >= 20 AND age <= 25;"
        );

        sb.append("Билеты девушек от 20 до 25 лет:\n");
        while (rs.next())
            sb.append(rs.getString("ticket") + "\n");
        return sb.toString();
    }

    private static String getMens() throws SQLException {
        StringBuilder sb = new StringBuilder();
        ResultSet rs = statement.executeQuery(
                "SELECT ticket " +
                "FROM Passenger " +
                "WHERE sex = 'male' AND age >= 45 AND age <= 60;"
        );

        sb.append("\n\n\nБилеты мужчин от 45 до 60 лет:\n");
        while (rs.next())
            sb.append(rs.getString("ticket") + "\n");
        return sb.toString();
    }
}
