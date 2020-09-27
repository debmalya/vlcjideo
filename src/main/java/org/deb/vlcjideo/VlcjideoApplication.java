package org.deb.vlcjideo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.deb.vlcjideo.adapter.VLCJMediaAdapter;
import org.deb.vlcjideo.dto.APIResponse;
import org.deb.vlcjideo.dto.VideoDTORequest;
import org.deb.vlcjideo.service.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.media.*;
import uk.co.caprica.vlcj.player.base.State;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import java.util.List;

import static uk.co.caprica.vlcj.javafx.videosurface.ImageViewVideoSurfaceFactory.videoSurfaceForImageView;

@SpringBootApplication
@Slf4j
@RestController("Modify RTSP video URL and play with JavaFX")
@RequestMapping("api/v0/video")
public class VlcjideoApplication extends Application {

	private final MediaPlayerFactory mediaPlayerFactory;

	private final EmbeddedMediaPlayer embeddedMediaPlayer;

	@Autowired
	private URLService urlService;

	private Stage streamingStage;

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
		// register the main VlcjideoApplication class in the spring container with the
		// call getAutowireCapableBeanFactory (). AutowireBean (this)
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

		Scene scene = new Scene(root, root.getMaxWidth() / 8, root.getHeight() / 8, Color.BLACK);
		primaryStage.setTitle("vlcj JavaFX");
		primaryStage.setScene(scene);
		primaryStage.show();
		streamingStage = primaryStage;
		embeddedMediaPlayer.media().play(urlService.getURL(),"enable");
		embeddedMediaPlayer.controls().setPosition(0.4f);
	}

	@Override
	public final void stop() {
		embeddedMediaPlayer.controls().stop();
		embeddedMediaPlayer.release();
		mediaPlayerFactory.release();
	}

	@PostMapping("/set")
	public ResponseEntity<APIResponse> setURL(@RequestBody final VideoDTORequest videoChangeRequest) {
		if (log.isInfoEnabled()) {
			log.info(String.format("Incoming request : %s", videoChangeRequest));
		}
		APIResponse apiResponse = new APIResponse();

		String videoURL = videoChangeRequest.getUrl();
		if (!StringUtils.isEmpty(videoURL)) {
			if (!StringUtils.isEmpty(videoChangeRequest.getTitle()) && streamingStage != null) {
				streamingStage.setTitle(videoChangeRequest.getTitle());
			}
			if (log.isInfoEnabled()) {
				log.info(String.format(" BEFORE Playing track no :%d", embeddedMediaPlayer.video().track()));
				log.info(String.format(" BEFORE No. of tracks %d", embeddedMediaPlayer.video().trackCount()));
				log.info(
						String.format(" BEFORE track description %s", embeddedMediaPlayer.video().trackDescriptions()));
			}

			boolean isStarted = embeddedMediaPlayer.media().play(videoURL);

			if (isStarted) {
				int currentTrack = embeddedMediaPlayer.video().setTrack(0);

				if (log.isInfoEnabled()) {
					log.info(String.format(" AFTER Playing track no :%d", currentTrack));
					log.info(String.format(" AFTER No. of tracks %d", embeddedMediaPlayer.video().trackCount()));
					log.info(String.format(" AFTER Track descriptions : %s",
							embeddedMediaPlayer.video().trackDescriptions()));
				}
				if (log.isDebugEnabled()) {
					log.debug(String.format("%s started", videoChangeRequest.getUrl()));
				}
			} else {
				if (log.isInfoEnabled()) {
					log.info(String.format(" Media is not prepared, cannot be played."));
				}
			}

			apiResponse.setStatus("OK");
		} else {
			apiResponse.setErrorCode(String.valueOf(HttpStatus.BAD_REQUEST));
			apiResponse.setErrorMessage("Please specify URL.");
			return ResponseEntity.badRequest().body(apiResponse);
		}

		apiResponse.setErrorCode("");
		apiResponse.setErrorMessage("");
		return ResponseEntity.ok(apiResponse);
	}

}
