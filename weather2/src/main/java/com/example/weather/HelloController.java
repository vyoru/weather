package com.example.weather;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;


public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField city;

    @FXML
    private Button getData;

    @FXML
    private Text pressure;

    @FXML
    private Text temp_feels;

    @FXML
    private Text temp_info;

    @FXML
    private Text temp_max;

    @FXML
    private Text temp_min;

    @FXML
    void initialize() throws ParseException {
        getData.setOnAction(event->{
            String getUserCity = city.getText().trim();
            if (!getUserCity.equals("")) {
                String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q="+ getUserCity + "&appid=5a0972d52fa6d27cdd29d2fc7d478082&units=metric");
                    Object obj = null;
                    try {
                        obj = new JSONParser().parse(output);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    JSONObject jo = (JSONObject) obj;
                    JSONObject main = (JSONObject) jo.get("main");
                    double temp = (double) main.get("temp");
                    double feels_like = (double) main.get("feels_like");
                    double tempMax = (double) main.get("temp_max");
                    double tempMin = (double) main.get("temp_min");
                    Long Pressure = (Long) main.get("pressure");
                temp_info.setText("Температура: " + temp);
                temp_feels.setText("Ощущается: " + feels_like);
                temp_max.setText("Максимум: " + tempMax);
                temp_min.setText("Минимум: " + tempMin);
                pressure.setText("Давление: " + Pressure);
            }
        });


    }
    private static String getUrlContent(String urlAdress){
        StringBuffer content = new StringBuffer();
        try {
            URL url = new URL(urlAdress);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null){
                content.append(line+"\n");
            }
            bufferedReader.close();
        }catch (IOException e){
            System.out.println("Такой город был не найден");
        }
        return content.toString();
    }

}
