package com.QwertyNetworks.ai_speaker.ui.main.media

import android.media.AudioAttributes
import android.media.MediaPlayer
import java.io.IOException

class MediaPlayerVoice(
    var mediaPlayer: MediaPlayer,
    var audioPath: String
    ) {

    fun playMediaPlayer() {
        try {
            mediaPlayer.setDataSource(audioPath)
            mediaPlayer.prepareAsync()
            mediaPlayer.start()

        } catch (e: IOException) {
            e.stackTrace
        }
    }
}