package Kia.Kosto.model;

import jakarta.persistence.*;

@Entity
@Table(name = "liked_city")
public class LikedCity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int id;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "weather")
    private String weather;

    @Column(name = "temperature")
    private double temperature;

    @Column(name = "humidity")
    private double humidity;

    @Column(name = "wind_speed")
    private double windSpeed;

    @Column(name = "user_name")
    private String userName;

    public String timeOfWeather;



    // Default constructor
    public LikedCity() {}

    // Constructor with parameters
    public LikedCity(String cityName, String weather, double temperature, double humidity, double windSpeed, String userName,String timeOfWeather) {
        this.cityName = cityName;
        this.weather = weather;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.userName = userName;
        this.timeOfWeather=timeOfWeather;
    }
    public String getTimeOfWeather() {
        return timeOfWeather;
    }

    public void setTimeOfWeather(String timeOfWeather) {
        this.timeOfWeather = timeOfWeather;
    }
    // Getters
    public int getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }

    public String getWeather() {
        return weather;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public String getUserName() {
        return userName;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
