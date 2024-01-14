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

    String response = "OK";

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
            System.out.println( "Here1" );
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

              System.out.println( "Here2" );

              // Process the result set
              while (resultSet.next()) {
                System.out.println( "Here3" );
                int id = resultSet.getInt("id");
                System.out.println( "Here4" );
                Object x = resultSet.getObject("location");
                System.out.println( "Here5" );
                Object y = resultSet.getObject("location");
                System.out.println( "Here6" );

                response = String.format( "ID: %d, X: %s, Y: %s", id, x, y );
                System.out.println( "Here7" );
                System.out.println( response );
                System.out.println( "Here8" );
              }
            }
          } else {
            System.out.println( "Here9" );
            System.out.println("Failed to connect to the PostgreSQL server.");
          }
        }
        System.out.println( "Here10" );
        return response;
      } catch (ClassNotFoundException | SQLException e) {
        System.out.println( "Here11: " + e );
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
      }
  }
}
