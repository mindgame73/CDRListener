package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import jssc.SerialPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jmx.export.annotation.AnnotationMBeanExporter;

import java.awt.*;
import java.net.URL;


@SpringBootApplication
public class AppStart extends Application {

    private Stage primaryStage;
    private static ConfigurableApplicationContext context;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Platform.setImplicitExit(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/primal.fxml"));
        loader.setControllerFactory(context::getBean);
        Parent root = loader.load();
        primaryStage.setTitle("ИС СиАТВ АО ГНЦ НИИАР");
        primaryStage.getIcons().add(new Image("/icon.png"));
        primaryStage.setScene(new Scene(root, 1400, 900));
        createTray();
        primaryStage.show();


        primaryStage.setOnCloseRequest(event -> {
            primaryStage.hide();
        });

    }


    public static void main(String[] args) {

        SpringApplicationBuilder builder = new SpringApplicationBuilder(AppStart.class);
        builder.headless(false);
        context = builder.run(args);

        launch(args);
    }

    private void createTray(){
        if (!SystemTray.isSupported()) return;
        PopupMenu trayMenu = new PopupMenu();
        MenuItem exitProgram = new MenuItem("Завершить программу");
        MenuItem showProgram = new MenuItem("Показать");
        exitProgram.addActionListener(e -> System.exit(0));
        showProgram.addActionListener(e -> Platform.runLater(this::showStage));

        trayMenu.add(showProgram);
        trayMenu.addSeparator();
        trayMenu.add(exitProgram);

        URL icon = getClass().getResource("/icon.png");
        java.awt.Image iconImage = Toolkit.getDefaultToolkit().getImage(icon);
        TrayIcon trayIcon = new TrayIcon(iconImage,"ИС СиАТВ АО ГНЦ НИИАР", trayMenu);
        trayIcon.setImageAutoSize(true);

        trayIcon.addActionListener(e -> Platform.runLater(this::showStage));

        SystemTray tray = SystemTray.getSystemTray();
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private void showStage() {
        if (primaryStage != null){
            primaryStage.show();
            primaryStage.toFront();
        }
    }
}
