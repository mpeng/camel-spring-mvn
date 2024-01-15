package com.example.demo.controller;

import com.example.demo.model.Tutorial;
import com.example.demo.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api2")
public class PostgresController {

  // JDBC URL, username, and password of PostgreSQL server
  private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/testdb";
  private static final String USERNAME = "postgres";
  private static final String PASSWORD = "123";

  @GetMapping("/postgres")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody String postgres() {

    String response = "";

      try {
        // Load the PostgreSQL JDBC driver
        Class.forName("org.postgresql.Driver");

        // Establish a connection
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
          if (connection != null) {
            System.out.println("Connected to the PostgreSQL server.");

            // Query to retrieve data from the coordinates table
            String query = "SELECT * FROM coordiantes";

            // Prepare and execute the query
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

              // Process the result set
              while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Object x = resultSet.getObject("location");
                Object y = resultSet.getObject("location");
                response += String.format( "[ID: %d, X: %s, Y: %s]\n", id, x, y );
                System.out.println( response );
              }
            }
          } else {
            System.out.println("Failed to connect to the PostgreSQL server.");
          }
        }
        return response;
      } catch (ClassNotFoundException | SQLException e) {
        System.out.println( "Here: " + e );
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
      }
  }
}
