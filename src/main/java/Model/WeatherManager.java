package Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@SuppressWarnings("All")
public class WeatherManager {
    private String city;
    private String country;
    private String sunrise;
    private String sunset;
    private Integer temperature;
    private String icon;
    private String description;
    private String windSpeed;
    private String cloudiness;
    private String pressure;
    private String humidity;

    public WeatherManager(String city) {
        this.city = city;
    }

    //Build a String from the read Json file
    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    //Reads and returns the JsonObject
    public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            if (is!=null) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                String jsonText = readAll(rd);
                JSONObject json = new JSONObject(jsonText);
                return json;
            }
            else return null;
        }
    }

    //method to get the weather of the selected city
    public boolean getWeather() throws JSONException, IOException {

        JSONObject json;
        JSONObject json_specific; //get specific data in json object variable

        //connects and asks the api to send the json file

            System.out.println("[API] Now calling the OPEN WEATHER API");
            json = readJsonFromUrl(
                    "http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+ PersonalDataField.API_TOKEN +"&lang=eng&units=metric");


        //receives the particular data in the read Json File
        if (json!=null) {
            json_specific = json.getJSONObject("main");
            this.temperature = json_specific.getInt("temp");
            this.pressure = json_specific.get("pressure").toString();
            this.humidity = json_specific.get("humidity").toString();
            json_specific = json.getJSONObject("wind");
            this.windSpeed = json_specific.get("speed").toString();
            json_specific = json.getJSONObject("clouds");
            this.cloudiness = json_specific.get("all").toString();

            json_specific = json.getJSONArray("weather").getJSONObject(0);
            this.description = json_specific.get("description").toString();
            this.icon = json_specific.get("icon").toString();
            json_specific = json.getJSONObject("sys");
            this.country = json_specific.get("country").toString();
            this.sunrise = json_specific.get("sunrise").toString();
            this.sunset = json_specific.get("sunset").toString();
            return true;
        }
        else throw new IOException();
    }


    //Getters for all the private fields

    public String getCity() {
        return city;
    }
    public void setCity(String city){
        this.city = city;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public String getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getCloudiness() {
        return cloudiness;
    }

    public String getPressure() {
        return pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getCountry() {
        return country;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }
}