package org.deb.vlcjideo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VlcjideoApplication extends Application {

	public static void main(String[] args) {
		SpringApplication.run(VlcjideoApplication.class, args);
	}

	@Override
	public void init() {
		SpringApplication.run(getClass()).getAutowireCapableBeanFactory().autowireBean(this);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane helloFxPane = new Pane(new Label("Hello JavaFx"));
		primaryStage.setScene(new Scene(helloFxPane));
		primaryStage.show();
	}
}
