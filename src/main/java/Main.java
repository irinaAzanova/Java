import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Database.connect();
        Database.addTable();
        //Database.addDataToTable(getData());
        System.out.println(Database.getDeltaTicket());
        System.out.println(Database.getListTickets());

        EventQueue.invokeLater(() -> {
            BarChart barChart = null;
            try {
                barChart = new BarChart(Database.getTicketsCost());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            barChart.setVisible(true);
        });
    }

    private static ArrayList<Passenger> getData() {
        ArrayList<Passenger> passengers = new ArrayList<>();
        String csvPath = "src/main/resources/titanic.csv";
        try (BufferedReader br = Files.newBufferedReader(Paths.get(csvPath))) {
            br.readLine();
            while (br.ready()) {
                String line = br.readLine();
                int from = line.indexOf("\"") + 1;
                int to = line.substring(from).indexOf("\"");
                String[] data = String.join("", line.substring(0, from), line.substring(to + 1)).split(",");
                double age = 0;
                if (!data[5].equals(""))
                    age = Double.parseDouble(data[5]);

                passengers.add(new Passenger(data[11], data[4], data[8],
                        Integer.parseInt(data[1]),
                        age,
                        Double.parseDouble(data[9])
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return passengers;
    }
}
