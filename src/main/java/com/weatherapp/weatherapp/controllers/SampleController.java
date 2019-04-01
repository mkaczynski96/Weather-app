package com.weatherapp.weatherapp.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.springframework.http.ResponseEntity;
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

    private final String URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private final String URL_APPKEY = "&appid=3662fc666bf0b718e1cd5b3fa49a9a08";
    private RestTemplate restTemplate = new RestTemplate();
    private ResponseEntity<String> response;
    private boolean errorResponse = false;

    public void searchWeather() throws Exception {

        Alert alertErr = new Alert(Alert.AlertType.ERROR);
        Alert alertInf = new Alert(Alert.AlertType.INFORMATION);

        try {
            response = restTemplate.getForEntity(URL + cityTextField.getText() + URL_APPKEY, String.class);
        } catch (HttpClientErrorException e) {
            errorResponse = true;
        }

        ObjectMapper mapper = new ObjectMapper();
        if (errorResponse) {
            alertErr.setTitle("Error");
            alertErr.setHeaderText(null);
            alertErr.setContentText("Failed to loaded data for your city! Please make sure you entered correct city!");
            alertErr.showAndWait();
            errorResponse = false;
        } else {
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode city = root.path("name");
            if (cityTextField.getText().matches(".*\\d.*")) {
                alertInf.setTitle("City");
                alertInf.setHeaderText(null);
                alertInf.setContentText("City: " + city);

                alertInf.showAndWait();
            } else {
                JsonNode temp = root.path("main").path("temp");
                JsonNode hum = root.path("main").path("humidity");
                JsonNode press = root.path("main").path("pressure");
                JsonNode wind = root.path("wind").path("speed");
                JsonNode clouds = root.path("clouds").path("all");

                int tempCelc = (int) (temp.asDouble() - 273.15);
                temperatureField.setText(tempCelc+"°C");
                humidityField.setText(hum.toString() + "%");
                pressureField.setText(press.toString());
                windSpeedField.setText(wind.toString() + "m/s");
                cloudsField.setText(clouds.toString() + "%");
            }
        }


    }


}
