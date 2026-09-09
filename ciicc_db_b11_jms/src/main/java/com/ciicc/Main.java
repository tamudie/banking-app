package com.ciicc;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/cicc_db_b11";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);


        boolean isProceed = true;
        int choice;

        while (isProceed) {
            options();
            System.out.print("Enter Choice (1-5): ");
            choice = input.nextInt();
            switch (choice) {
                case 1 -> {


                    System.out.print("Enter your first name : \n");
                    String firstName = input.next();
                    System.out.print("Enter your middle name : \n");
                    String middleName = input.next();
                    System.out.print("Enter your last name : \n");
                    String lastName = input.next();


                    insertData(firstName, middleName, lastName);
                }
                case 2 -> {

                    System.out.println("Enter id number: ");
                    int id = input.nextInt();

                    System.out.println("Enter First Name : ");
                    String firstName = input.next();

                    System.out.println("Enter Middle Name: ");
                    String middleName = input.next();

                    System.out.println("Enter Last Name: ");
                    String lastName = input.next();

                    updateData(id, firstName, middleName, lastName);


                }
                case 3 -> {


                    System.out.print("Enter ID you want to DELETE! : ");
                    int id = input.nextInt();
                    fetchSpecificData(id);


                    System.out.print("Are you sure you want to delete this record? (yes/no): ");
                    String confirm = input.next();
                    switch (confirm) {
                        case "yes" -> {
                            deleteData(id);
                        }
                        case "no" -> {
                            System.out.println("Cancelled Delete");

                        }
                        default -> {
                            System.out.println("Invalid Response. Delete Cancelled");
                        }
                    }


//
                }
                case 4 -> {
                    System.out.println("****************************");
                    System.out.println("Display All Data");
                    fetchData();
                }
                case 5 -> {
                    System.out.println("Thank you! Good Byeee.");
                    isProceed = false;
                }
                default -> {
                    System.out.println("Invalid Response!. choose again (1-5): ");
                }

            }


        }


    }

    public static void options() {
        System.out.println("=======================================");
        System.out.println("      Welcome to Store Data's Program  ");
        System.out.println("=======================================");

        System.out.println("1. INSERT INFORMATION ");
        System.out.println("2. UPDATE INFORMATION ");
        System.out.println("3. DELETE INFORMATION ");
        System.out.println("4. DISPLAY STORED INFORMATION ");
        System.out.println("5. EXIT");
    }

    public static void deleteData(int id) {
        String query = "DELETE FROM users WHERE id = ? ";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Delete data successfully");
            } else {
                System.out.println("No data found with the given id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void updateData(int id, String firstName, String middleName, String lastName) {
        String query = "UPDATE users SET first_name = ?, middle_name = ?, last_name = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {


            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, firstName);
            statement.setString(2, middleName);
            statement.setString(3, lastName);
            statement.setInt(4, id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Update Successfully.");
            } else {
                System.out.println("Failed to insert data.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertData(String firstname, String middleName, String lastName) {
        String query = "INSERT users (first_name, middle_name, last_name) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, firstname);
            statement.setString(2, middleName);
            statement.setString(3, lastName);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Data inserted successfully");
            } else {
                System.out.println("Failed to insert Data");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void fetchSpecificData(int id) {

        // 1
        String query = "SELECT * FROM users WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);) {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            // Use the statement to execute SQL queries
            ResultSet results = statement.executeQuery();

            if (results.next()) {
                int userId = results.getInt("id");
                String firstName = results.getString("first_name");
                String middleName = results.getString("middle_name");
                String lastName = results.getString("last_name");
                System.out.println("ID: " + userId + ", firstName: " + firstName + ", middleName: " +
                        middleName + ", lastName: " + lastName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void fetchData() {
        String query = "SELECT * FROM users";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);) {
            // statements
            // 1. statement
            // 2. prepared statement
            // 3. callable statement

            Statement statement = connection.createStatement();
            // Use the statement to execute SQL queries
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                int id = results.getInt("id");
                String firstName = results.getString("first_name");
                String middleName = results.getString("middle_name");
                String lastName = results.getString("last_name");
                System.out.println("ID: " + id + ", firstName: " + firstName + ", middleName: " +
                        middleName + ", lastName: " + lastName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}

