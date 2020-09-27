package org.deb.vlcjideo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.deb.vlcjideo.fx.VlcjJavaFxApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import java.util.List;

import static uk.co.caprica.vlcj.javafx.videosurface.ImageViewVideoSurfaceFactory.videoSurfaceForImageView;

@SpringBootApplication
public class VlcjideoApplication extends Application {

	private final MediaPlayerFactory mediaPlayerFactory;

	private final EmbeddedMediaPlayer embeddedMediaPlayer;

	private ImageView videoImageView;

	public VlcjideoApplication() {
		this.mediaPlayerFactory = new MediaPlayerFactory();
		this.embeddedMediaPlayer = mediaPlayerFactory.mediaPlayers().newEmbeddedMediaPlayer();
		this.embeddedMediaPlayer.events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
			@Override
			public void playing(MediaPlayer mediaPlayer) {
			}

			@Override
			public void paused(MediaPlayer mediaPlayer) {
			}

			@Override
			public void stopped(MediaPlayer mediaPlayer) {
			}

			@Override
			public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
			}
		});
	}

	public static void main(String[] args) {
		SpringApplication.run(VlcjideoApplication.class, args);
	}

	@Override
	public void init() {
//		register the main FxbootApplication class in the spring container with the call getAutowireCapableBeanFactory (). AutowireBean (this)
		SpringApplication.run(getClass()).getAutowireCapableBeanFactory().autowireBean(this);
		this.videoImageView = new ImageView();
		this.videoImageView.setPreserveRatio(true);

		embeddedMediaPlayer.videoSurface().set(videoSurfaceForImageView(this.videoImageView));

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

//        embeddedMediaPlayer.media().play("rtsp://live:cyc10P11v3@192.168.137.43:554/video.h264");

		embeddedMediaPlayer.media().play("https://www.youtube.com/watch?v=gMFL5TY5D0E");

		embeddedMediaPlayer.controls().setPosition(0.4f);
	}

	@Override
	public final void stop() {
		embeddedMediaPlayer.controls().stop();
		embeddedMediaPlayer.release();
		mediaPlayerFactory.release();
	}
}
