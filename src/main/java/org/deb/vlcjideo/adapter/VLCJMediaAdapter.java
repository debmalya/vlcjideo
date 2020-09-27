package org.deb.vlcjideo.adapter;

import lombok.extern.slf4j.Slf4j;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;

@Slf4j
public class VLCJMediaAdapter extends MediaPlayerEventAdapter{
	@Override
	public void playing(MediaPlayer mediaPlayer) {
		/*
		if (log.isInfoEnabled()){
			log.info("Media is playing");
		}
		*/

	}

	@Override
	public void paused(MediaPlayer mediaPlayer) {
		/*
		if (log.isInfoEnabled()){
			log.info("Media paused");
		}

		 */
	}

	@Override
	public void stopped(MediaPlayer mediaPlayer) {
		/*
		if (log.isInfoEnabled()){
			log.info("Media stopped");
		}

		 */
	}

	@Override
	public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
		/*
		if (log.isInfoEnabled()){
			log.info("Media time changed");
		}

		 */
	}
}
