package sample.Model;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import jssc.SerialPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import sample.AppStart;
import sample.Model.CallDetailRecord;
import sample.Model.Subscriber;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class Controller {

    @Value("${logger.outputFilePath}")
    private String outputLoggerFile;

    @Value("${url.link}")
    private String linkToWeb;

    private SerialPort serialPort;

    @Autowired
    public void setSerialPort(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    @FXML
    private TableColumn<CallDetailRecord, Long> numberACol;

    @FXML
    private TableColumn<CallDetailRecord, Integer> counterCol;

    @FXML
    private TableColumn<CallDetailRecord, Subscriber> subACol;

    @FXML
    private TableColumn<CallDetailRecord, Subscriber> subBCol;

    @FXML
    private TableColumn<CallDetailRecord, String> startTimeCol;

    @FXML
    private TableColumn<CallDetailRecord, String> stopTimeCol;

    @FXML
    private TableColumn<CallDetailRecord, String> rescodeCol;

    @FXML
    private TableView<CallDetailRecord> table;

    @FXML
    private TableColumn<CallDetailRecord, Long> numberBCol;

    @FXML
    private Menu webLink;

    @FXML
    private Menu logsLink;

    @FXML
    private Label dateTime;

    @FXML
    private Circle circlePortStatus;

    @FXML
    private Label portLabel;

    private static ObservableList<CallDetailRecord> list = FXCollections.observableArrayList();


    @FXML
    void initialize(){
        Thread clockThread = new Thread(() -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Samara"));
            while(true){
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
                final String time = simpleDateFormat.format(new Date());
                Platform.runLater(()->{
                    dateTime.setText(time);
                });
            }
        });
        clockThread.start();

        Timer scheduler = new Timer();
        scheduler.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(()->{
                    if (serialPort.isOpened()) {
                        circlePortStatus.setFill(Color.GREEN);
                        portLabel.setText(serialPort.getPortName() + " (открыт)");
                    }
                    else {
                        circlePortStatus.setFill(Color.RED);
                        portLabel.setText(serialPort.getPortName() + " (закрыт)");
                    }
                });
            }
        }, 5_000, 600_000);


        counterCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        stopTimeCol.setCellValueFactory(new PropertyValueFactory<>("stopTime"));
        numberACol.setCellValueFactory(new PropertyValueFactory<>("numberB"));
        numberBCol.setCellValueFactory(new PropertyValueFactory<>("numberA"));
        rescodeCol.setCellValueFactory(new PropertyValueFactory<>("resultCode"));
        subACol.setCellValueFactory(new PropertyValueFactory<>("subscriberB"));
        subBCol.setCellValueFactory(new PropertyValueFactory<>("subscriberA"));
        table.setItems(list);

        Label webLinkLabel = new Label("Веб ресурс");
        AppStart appStart = new AppStart();
        webLinkLabel.setOnMouseClicked(event -> appStart.getHostServices().showDocument(linkToWeb));
        webLink.setGraphic(webLinkLabel);

        Label logsLinkLabel = new Label("Логи");
        logsLinkLabel.setOnMouseClicked(event -> appStart.getHostServices().showDocument(outputLoggerFile));
        logsLink.setGraphic(logsLinkLabel);

    }

    public void addCdr(CallDetailRecord cdr){
        list.add(cdr);
        list.sort(Comparator.comparingInt(CallDetailRecord::getId).reversed());
    }

}
