package com.weatherapp.weatherapp.controllers;

import com.weatherapp.weatherapp.model.Response;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@Controller
public class SampleController {
    @FXML
    TextField cityTextField;

    @FXML
    Text temperatureField;

    @FXML
    Text humidityField;

    @FXML
    Text pressureField;

    @FXML
    Text windSpeedField;

    @FXML
    Text cloudsField;

    private final static String URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private final static String URL_APPKEY = "&appid=3662fc666bf0b718e1cd5b3fa49a9a08";
    private boolean errorResponse = false;
    private Response response;
    private RestTemplate restTemplate = new RestTemplate();

    public void searchWeather() throws Exception {

        Alert alertErr = new Alert(Alert.AlertType.ERROR);
        Alert alertInf = new Alert(Alert.AlertType.INFORMATION);

        try {
            response = restTemplate.getForObject(URL + cityTextField.getText() + URL_APPKEY, Response.class);
        } catch (HttpClientErrorException e) {
            errorResponse = true;
        }

        if (errorResponse) {
            alertErr.setTitle("Error");
            alertErr.setHeaderText(null);
            alertErr.setContentText("Failed to load data! Please make sure you entered correct city!");
            alertErr.showAndWait();
            errorResponse = false;
        } else {
            /* If city was entered as number then program shows city name */
            if (cityTextField.getText().matches(".*\\d.*")) {
                alertInf.setTitle("City");
                alertInf.setHeaderText(null);
                alertInf.setContentText("City: " + response.getName());
                alertInf.showAndWait();
            }
            /* Kelvin to Celsius */
            int tempCelc = (int) (response.getMain().getTemp() - 273.15);
            /* Sets texts to fields */
            temperatureField.setText(tempCelc + "°C");
            humidityField.setText(response.getMain().getHumidity() + "%");
            pressureField.setText(response.getMain().getPressure() + " hPa");
            windSpeedField.setText(response.getWind().getSpeed() + " m/s");
            cloudsField.setText(response.getClouds().getAll() + "%");

        }


    }
}
