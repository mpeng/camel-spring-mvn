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
@RequestMapping("/api")
public class PostgresController {

  // JDBC URL, username, and password of PostgreSQL server
  private static final String JDBC_URL = "jdbc:postgresql://your_database_host:your_database_port/your_database_name";
  private static final String USERNAME = "your_database_username";
  private static final String PASSWORD = "your_database_password";
  @GetMapping("/postgres")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody String postgres() {

      try {
        // Load the PostgreSQL JDBC driver
        Class.forName("org.postgresql.Driver");

        // Establish a connection
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
          if (connection != null) {
            System.out.println("Connected to the PostgreSQL server.");

            // Query to retrieve data from the coordinates table
            String query = "SELECT * FROM coordinates";

            // Prepare and execute the query
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

              // Process the result set
              while (resultSet.next()) {
                int id = resultSet.getInt("id");
                double x = resultSet.getDouble("location.x");
                double y = resultSet.getDouble("location.y");

                System.out.println("ID: " + id + ", X: " + x + ", Y: " + y);
              }
            }
          } else {
            System.out.println("Failed to connect to the PostgreSQL server.");
          }
        }
        return "Ok";
      } catch (ClassNotFoundException | SQLException e) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
      }
    }
  }
}
