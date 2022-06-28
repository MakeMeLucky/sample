package org.example.application.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.application.microservices.model.DbRecord;
import org.example.application.microservices.model.Record;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class PostgreService {

    @Value("${POSTGRES_DB}")
    private String POSTGRES_DB;

    @Value("${POSTGRES_PASSWORD}")
    private String POSTGRES_PASSWORD;

    @Value("${POSTGRES_USER}")
    private String POSTGRES_USER;

    private final Logger LOGGER = LogManager.getLogger(PostgreService.class);

    private static final String SQL = "SELECT * FROM invokedservices WHERE id = ?";

    public List<DbRecord> getHistoryById(String id) {

        Record microService = new Record();

        List<DbRecord> microServices = new ArrayList<>();

        String url = "jdbc:postgresql://postgres-service:5432/" + POSTGRES_DB;

        try (Connection con = DriverManager.getConnection(url, POSTGRES_USER, POSTGRES_PASSWORD);

             PreparedStatement pstmt = con.prepareStatement(SQL)) {

            pstmt.setString(1, id);
            LOGGER.info("Invoked sql {}",pstmt.toString());
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                DbRecord dbRecord = new DbRecord();
                dbRecord.setSourceMicroserviceId(resultSet.getString("source_microservice_id"));
                dbRecord.setSourceMicroserviceName(resultSet.getString("source_microservice_name"));
                dbRecord.setTargetMicroserviceId(resultSet.getString("target_microservice_id"));
                dbRecord.setTargetMicroserviceName(resultSet.getString("target_microservice_name"));
                dbRecord.setTimestamp(resultSet.getTimestamp("invoke_time"));
                LOGGER.info("Created Record {}", dbRecord);
                microServices.add(dbRecord);
            }

            microServices.sort(Comparator.comparing(DbRecord::getTimestamp));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return microServices;
    }

}
