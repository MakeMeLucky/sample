package org.example.microservice2.sql;

import org.example.microservice2.kafka.model.Record;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.Instant;

@Component
public class PostgreComponent {

    @Value("${POSTGRES_DB}")
    private String POSTGRES_DB;

    @Value("${POSTGRES_PASSWORD}")
    private String POSTGRES_PASSWORD;

    @Value("${POSTGRES_USER}")
    private String POSTGRES_USER;

    private static final String SQL = "INSERT INTO invokedservices(id, source_microservice_id, source_microservice_name," +
            " target_microservice_id, target_microservice_name, invoke_time) "
            + "VALUES(?,?,?,?,?,?)";


    public void saveToDb(Record record, Record nextRecord) {

        Instant instant = Instant.now();
        Timestamp timestamp = Timestamp.from(instant);

        String url = "jdbc:postgresql://postgres-service:5432/" + POSTGRES_DB;

        try (Connection con = DriverManager.getConnection(url, POSTGRES_USER, POSTGRES_PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, record.getId());
            pstmt.setString(2, record.getMicroserviceId());
            pstmt.setString(3, record.getMicroserviceName());
            pstmt.setString(4, nextRecord.getMicroserviceId());
            pstmt.setString(5, nextRecord.getMicroserviceName());
            pstmt.setTimestamp(6, timestamp);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
