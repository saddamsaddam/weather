package Kia.Kosto.model;

import jakarta.persistence.*;

import java.io.*;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;

    @Column(name = "user_name")
    private String userName;


    @Column(name = "password")
    private String password;

    // Default constructor
    public User() {
    }

    // Parameterized constructor
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Override toString method
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
    public void saveToTextFile(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("login_details.txt", false))) {
            writer.write("Username: " + username + ", Password: " + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public User retrieveUserData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("login_details.txt"))) {
            String line = reader.readLine();
            if (line != null) {
                String[] parts = line.split(", ");
                if (parts.length == 2) {
                    String username = parts[0].split(": ")[1];
                    String password = parts[1].split(": ")[1];
                    return new User(username, password);// only one user data will retrieve
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
