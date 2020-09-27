package org.deb.vlcjideo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.deb.vlcjideo.adapter.VLCJMediaAdapter;
import org.deb.vlcjideo.service.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import java.util.List;

import static uk.co.caprica.vlcj.javafx.videosurface.ImageViewVideoSurfaceFactory.videoSurfaceForImageView;

@SpringBootApplication
@Slf4j
public class VlcjideoApplication  extends  Application {

	private final MediaPlayerFactory mediaPlayerFactory;

	private final EmbeddedMediaPlayer embeddedMediaPlayer;

	@Autowired
	private URLService urlService;

	private ImageView videoImageView;

	public VlcjideoApplication() {
		this.mediaPlayerFactory = new MediaPlayerFactory();
		this.embeddedMediaPlayer = mediaPlayerFactory.mediaPlayers().newEmbeddedMediaPlayer();
		this.embeddedMediaPlayer.events().addMediaPlayerEventListener(new VLCJMediaAdapter());
	}

	public static void main(String[] args) {

//		SpringApplication.run(VlcjideoApplication.class, args);
		Application.launch();
	}

	@Override
	public void init() {

		this.videoImageView = new ImageView();
		this.videoImageView.setPreserveRatio(true);

		embeddedMediaPlayer.videoSurface().set(videoSurfaceForImageView(this.videoImageView));
		//		register the main VlcjideoApplication class in the spring container with the call getAutowireCapableBeanFactory (). AutowireBean (this)
		SpringApplication.run(getClass()).getAutowireCapableBeanFactory().autowireBean(this);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		List<String> params = getParameters().getRaw();

		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: black;");

		videoImageView.fitWidthProperty().bind(root.widthProperty());
		videoImageView.fitHeightProperty().bind(root.heightProperty());

		root.widthProperty().addListener((observableValue, oldValue, newValue) -> {
			// If you need to know about resizes
		});

		root.heightProperty().addListener((observableValue, oldValue, newValue) -> {
			// If you need to know about resizes
		});

		root.setCenter(videoImageView);

		Scene scene = new Scene(root, 1200, 675, Color.BLACK);
		primaryStage.setTitle("vlcj JavaFX");
		primaryStage.setScene(scene);
		primaryStage.show();

		if (log.isInfoEnabled()){
			log.info(String.format("Trying to play %s",urlService.getURL()));
		}

		embeddedMediaPlayer.media().play(urlService.getURL());

		embeddedMediaPlayer.controls().setPosition(0.4f);
	}

	@Override
	public final void stop() {
		embeddedMediaPlayer.controls().stop();
		embeddedMediaPlayer.release();
		mediaPlayerFactory.release();
	}


}
