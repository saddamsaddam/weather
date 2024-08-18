package Kia.Kosto.controller;

import Kia.Kosto.model.LikedCity;
import Kia.Kosto.model.User;
import Kia.Kosto.model.Weather;
import Kia.Kosto.repository.UserRepository;
import Kia.Kosto.repository.WeatherRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class WeatherController {
    private final String apiKey = "bab221b4a0c72d3bb1c0c48d966621e8";
    @Autowired
    private WeatherRepository weatherRepository;


    private final String imageResourcePath = "/static/img/";


    @Autowired
    private UserRepository userRepository;


    @GetMapping("/home")
    public String Home() {
        return "HomePage";
    }


    @PostMapping("/searchCity")
    public String serachWeather(@RequestParam("userName") String userName,
                                                 @RequestParam("location") String location,Model model) {
        Weather weatherInfo = new Weather();
        String request = getResponse(
                "http://api.openweathermap.org/data/2.5/weather?q=" + location + "&units=metric&appid=" + apiKey);
        if (!(request.startsWith("Error"))) {
            JSONObject ob =getDataObject(request);
            if (ob != null) {
                JSONObject main = (JSONObject) ob.get("main");
                JSONObject coord = (JSONObject) ob.get("coord");
                JSONArray weatherArray = (JSONArray) ob.get("weather");
                JSONObject weatherObj = (JSONObject) weatherArray.get(0);
                JSONObject windObj = (JSONObject) ob.get("wind");

                weatherInfo.setTemp(Double.parseDouble(main.get("temp").toString()));
                weatherInfo.setFeels_like(Double.parseDouble(main.get("feels_like").toString()));
                weatherInfo.setTemp_min(Double.parseDouble(main.get("temp_min").toString()));
                weatherInfo.setTemp_max(Double.parseDouble(main.get("temp_max").toString()));
                weatherInfo.setPressure(Double.parseDouble(main.get("pressure").toString()));
                weatherInfo.setHumidity(Double.parseDouble(main.get("humidity").toString()));
//                weatherInfo.setLocation(ob.get("name").toString());
                weatherInfo.setLocation(location.substring(0, 1).toUpperCase() + location.substring(1));
                weatherInfo.setLongitude(Double.parseDouble(coord.get("lon").toString()));
                weatherInfo.setLatitude(Double.parseDouble(coord.get("lat").toString()));
                weatherInfo.setWeather(weatherObj.get("main").toString());
                weatherInfo.setWind(Double.parseDouble(windObj.get("speed").toString()));

                String weatherIconLocation = imageResourcePath + weatherInfo.getWeather().toLowerCase() + ".png";
                InputStream inputStream = getClass().getResourceAsStream(weatherIconLocation);
                String icon = inputStream != null ? weatherInfo.getWeather().toLowerCase() + ".png" : "weather_default.png";
                weatherIconLocation = imageResourcePath.replaceFirst("/static", "");
                weatherInfo.setWeatherIcon(weatherIconLocation + icon);

            } else {

                weatherInfo.setError("Error Occured!");
            }
        } else {
            weatherInfo.setError("Error Occured!");
        }


        if(weatherInfo.getLocation()==null){// check weather exist or not
            model.addAttribute("errorMessage", "Sorry, "+location+" is not exist in the world");
            return "error";
        }
        else{
            model.addAttribute("weatherData",weatherInfo);
            return "success";
        }

    }
    public static JSONObject getDataObject(String response) {
        String error = "";
        JSONObject ob = null;
        try {
            ob = (JSONObject) new JSONParser().parse(response);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            if (!ob.get("cod").toString().equals("200")) {
                ob = null;
                System.out.println("COD is:" + ob.get("cod").toString());
            }
        } catch (Exception e) {
            error = "Error in Fetching the Request";
            System.out.println(error);
        }
        return ob;
    }
    //This method takes a String URL, makes an HTTP GET request to the URL, and returns the response as a String.
    public static String getResponse(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            return content.toString();
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
            return "Error: " + e.toString();
        }
    }
    @PostMapping("/submitWeatherData")
    public String submitWeatherData(
            @RequestParam("location") String location,
            @RequestParam("temp") double temp,
            @RequestParam("feels_like") double feelsLike,
            @RequestParam("temp_min") double tempMin,
            @RequestParam("temp_max") double tempMax,
            @RequestParam("pressure") double pressure,
            @RequestParam("humidity") double humidity,
            @RequestParam("weather") String weather,
            @RequestParam("wind") double wind,
            Model model) {

        String username=new User().retrieveUserData().getUserName();

        LocalDateTime currentDateTime = LocalDateTime.now();
        // Define the format you want for the date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // Format the current date and time as a string
        String formattedDateTime = currentDateTime.format(formatter);

        weatherRepository.save(new LikedCity(location,weather,temp,humidity,wind,username,formattedDateTime));

        // Redirect to a  Homepage
        return "HomePage";
    }

    @RequestMapping("/likeCitiesData")
    public  String likeCitiesData(Model model){
        String username=new User().retrieveUserData().getUserName();
        List<LikedCity> data=weatherRepository.findByUserName(username);
        model.addAttribute("weatherData",data);
        return "LikedCities.html";
    }

}