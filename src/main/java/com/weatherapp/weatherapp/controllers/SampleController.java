package com.weatherapp.weatherapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.springframework.stereotype.Controller;

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

    @FXML
    Text precipitationField;

    public void searchWeather(){

        temperatureField.setText("WORKS!");
    }
}
