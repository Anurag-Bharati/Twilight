package main.test;

import Model.WeatherManager;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * <h2>Unit Test | WeatherManager</h2>
 * <p>Unit tests for WeatherManager</p>
 *
 * @author Anurag-Bharati
 * @since 2021
 * @version 1.0
 *
 */
public class UnitTest_WEATHERMANAGER {

    WeatherManager weatherManager;

    /**
     * <h2>Setting Up</h2>
     * <p>Initializing objects before running the tests</p>
     */
    @Before
    public void init() throws JSONException, IOException {
        String city = "Kathmandu";
        weatherManager = new WeatherManager(city);
        weatherManager.getWeather();
    }


    @Test
    public void checkCountry(){

        // given
        String country = "NP";

        assertEquals(country,weatherManager.getCountry());
    }


    @Test
    public void checkTemp(){

        // given // Check the temp from other source and put the value // note: temp may vary by ~1 degree C
        String temp = "20";

        assertEquals(temp, weatherManager.getTemperature().toString());
    }

}
