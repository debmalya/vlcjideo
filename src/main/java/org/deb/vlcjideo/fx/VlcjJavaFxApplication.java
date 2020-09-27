package org.deb.vlcjideo.fx;

import static uk.co.caprica.vlcj.javafx.videosurface.ImageViewVideoSurfaceFactory.videoSurfaceForImageView;

import java.util.List;

import org.deb.vlcjideo.adapter.VLCJMediaAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 *
 */
@Component
@Slf4j
public class VlcjJavaFxApplication extends Application {

   

	private final MediaPlayerFactory mediaPlayerFactory;

    private final EmbeddedMediaPlayer embeddedMediaPlayer;

    private ImageView videoImageView;

    public VlcjJavaFxApplication() {
        this.mediaPlayerFactory = new MediaPlayerFactory();
        this.embeddedMediaPlayer = mediaPlayerFactory.mediaPlayers().newEmbeddedMediaPlayer();
        this.embeddedMediaPlayer.events().addMediaPlayerEventListener(new VLCJMediaAdapter());
    }

    @Override
    public void init() {
        this.videoImageView = new ImageView();
        this.videoImageView.setPreserveRatio(true);

        embeddedMediaPlayer.videoSurface().set(videoSurfaceForImageView(this.videoImageView));
        SpringApplication.run(getClass()).getAutowireCapableBeanFactory().autowireBean(this);
        if (log.isInfoEnabled()){
            log.info("~~~ Initialized VlcjJavaFxApplication ~~~");
        }

    }

    @Override
    public final void start(Stage primaryStage) throws Exception {
        if (log.isInfoEnabled()){
            log.info("~~~ Initialized ~~~");
        }
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

        embeddedMediaPlayer.media().play("https://www.youtube.com/watch?v=gMFL5TY5D0E");

        embeddedMediaPlayer.controls().setPosition(0.4f);
    }

    @Override
    public final void stop() {
        embeddedMediaPlayer.controls().stop();
        embeddedMediaPlayer.release();
        mediaPlayerFactory.release();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

