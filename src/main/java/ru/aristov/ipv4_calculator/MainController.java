package ru.aristov.ipv4_calculator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MainController {
    private int[] ipAdressBuff;
    private int[] normalMaskBuff;
    private int[] broadcastAddressBuff;
    private int prefixMask;
    private long countHostBuff;

    @FXML
    private TextField IP_address;

    @FXML
    private Label broadcastAddress;

    @FXML
    private Button btnCalculate;

    @FXML
    private Button btnClear;

    @FXML
    private Label hosts;

    @FXML
    private Label incorrectMassage;

    @FXML
    private Label inputData;

    @FXML
    private Label networkAddress;

    @FXML
    private Label normMask;

    @FXML
    private ComboBox<String> prefixMaskBox;

    @FXML
    void initialize() {
        ObservableList<String> prefmask = FXCollections.observableArrayList("/32", "/31", "/30", "/29", "/28", "/27", "/26",
                "/25", "/24", "/23", "/22", "/21", "/20", "/19", "/18", "/17",
                "/16", "/15", "/14", "/13", "/12", "/11", "/10", "/9", "/8", "/7",
                "/6", "/5", "/4", "/3", "/2", "/1", "/0");
        prefixMaskBox.setItems(prefmask);
        prefixMaskBox.setValue("/32");

        btnCalculate.setOnAction(event -> {
            if (IP_address.getText() == null || IP_address.getText().trim().isEmpty()) {
                prefixMaskBox.setValue("/32");
                incorrectMassage.setText("Введите IP адрес!");
            } else {
                String ipAdrStr = IP_address.getText().replace(",", ".");
                String[] ipAdrArrStr = ipAdrStr.split("\\.");
                int[] ipAdrArrInt = new int[4];
                boolean validation = true;
                for (int i = 0; i < ipAdrArrStr.length; i++) {
                    if (Integer.parseInt(ipAdrArrStr[i]) > 255 || Integer.parseInt(ipAdrArrStr[i]) < 0 || ipAdrArrStr.length != 4)
                        validation = false;
                    else ipAdrArrInt[i] = Integer.parseInt(ipAdrArrStr[i]);
                }

                if (validation) {
                    prefixMask = Integer.parseInt(prefixMaskBox.getValue().replaceAll("\\D+", ""));
                    normalMaskBuff = IPv4_Calculation.normalMask(prefixMask);
                    ipAdressBuff = IPv4_Calculation.getNetAddress(ipAdrArrInt, normalMaskBuff);
                    broadcastAddressBuff = IPv4_Calculation.getBroadcastAddress(normalMaskBuff, ipAdressBuff);
                    countHostBuff = IPv4_Calculation.countHosts(prefixMask);

                    incorrectMassage.setText("");
                    inputData.setText("Входные данные: " + IP_address.getText() + prefixMaskBox.getValue());
                    networkAddress.setText("Адрес сети: " + MainApplication.arrayToString(ipAdressBuff));
                    normMask.setText("Маска сети: " + MainApplication.arrayToString(normalMaskBuff));
                    broadcastAddress.setText("Широковещательный адрес: " + MainApplication.arrayToString(broadcastAddressBuff));
                    hosts.setText("Количество хостов: " + countHostBuff);
                } else {
                    prefixMaskBox.setValue("/32");
                    incorrectMassage.setText("Введите корректный адрес!");
                    inputData.setText("Входные данные: ");
                    networkAddress.setText("Адрес сети: ");
                    normMask.setText("Маска сети: ");
                    broadcastAddress.setText("Широковещательный адрес: ");
                    hosts.setText("Количество хостов: ");
                }
            }
        });

        btnClear.setOnAction(actionEvent -> {
            ipAdressBuff = null;
            normalMaskBuff = null;
            broadcastAddressBuff = null;
            prefixMask = 0;
            countHostBuff = 0;

            IP_address.clear();
            prefixMaskBox.setValue("/32");

            incorrectMassage.setText("");
            inputData.setText("??????? ??????: ");
            networkAddress.setText("????? ????: ");
            normMask.setText("????? ????: ");
            broadcastAddress.setText("????????????????? ?????: ");
            hosts.setText("?????????? ??????: ");
        });
    }
}