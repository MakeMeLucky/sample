package org.example.microservice2.sql;

import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.Instant;

@Component
public class PostgreComponent {

    private static final String url = "jdbc:postgresql://postgres:5432/micro";
    private static final String user = "admin";
    private static final String password = "admin";
    private static final String SQL = "INSERT INTO processedmessages(this_service, next_service) "
            + "VALUES(?,?)";
    private static final String INVOKED_SQL = "INSERT INTO invokedservices(name, invoke_time) "
            + "VALUES(?,?)";


    public void saveToDb(String thisService, String nextService) {

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement();

             PreparedStatement pstmt = con.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, thisService);
            pstmt.setString(2, nextService);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void saveToDb(String thisService) {

        Instant instant = Instant.now();
        Timestamp timestamp = Timestamp.from(instant);

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement();

             PreparedStatement pstmt = con.prepareStatement(INVOKED_SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, thisService);
            pstmt.setTimestamp(2, timestamp);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
