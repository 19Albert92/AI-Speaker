package com.QwertyNetworks.ai_speaker.ui.main.media

import android.media.MediaRecorder

class MediaRecorderVoice(private var audioSavePath: String,
                         private var mediaRecorder: MediaRecorder
) {

    fun mediaRecorder(){
        mediaRecorder = MediaRecorder()
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mediaRecorder.setOutputFile(audioSavePath)
    }

    fun startRecorder() {
        mediaRecorder.prepare()
        mediaRecorder.start()
    }
    fun stopRecorder() {
        mediaRecorder.stop()
        mediaRecorder.release()
    }
}