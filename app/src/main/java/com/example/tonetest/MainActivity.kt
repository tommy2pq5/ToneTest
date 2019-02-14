package com.example.tonetest

import android.content.Context
import android.media.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val handler=Handler()
        val runnable=object :Runnable{
            override fun run() {
                //ToneGenerator(AudioManager.STREAM_MUSIC,ToneGenerator.MAX_VOLUME).startTone(ToneGenerator.TONE_CDMA_CALL_SIGNAL_ISDN_NORMAL)

                play(440)

                handler.postDelayed(this,10000)
            }

        }
        handler.post(runnable)
    }
    fun play(frequency:Int){
        val SAMPLE_RATE=44100

        Log.d("StartGenWave",Calendar.getInstance().get(Calendar.MILLISECOND).toString())
        val buff=generateSinWave(frequency, SAMPLE_RATE)
        Log.d("EndGenWave",Calendar.getInstance().get(Calendar.MILLISECOND).toString())

        val audioAttributes=AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
        val audioFormat=AudioFormat.Builder()
            .setSampleRate(44100)
            .setEncoding(AudioFormat.ENCODING_PCM_8BIT)
            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
            .build()
        val audioTrack= AudioTrack.Builder()
            .setAudioAttributes(audioAttributes)
            .setAudioFormat(audioFormat)
            .setBufferSizeInBytes(buff.size)
            .build()

        audioTrack.play()

        Log.d("startWrite",Calendar.getInstance().get(Calendar.MILLISECOND).toString())
        audioTrack.write(buff,0,buff.size)
        Log.d("endWrite",Calendar.getInstance().get(Calendar.MILLISECOND).toString())

        audioTrack.stop()
        //audioTrack.flush()
    }

    // 1second
    private fun generateSinWave(frequency: Int, samplingRate:Int):ByteArray{
        var buff=ByteArray(samplingRate)
        for(i in 0 .. samplingRate-1){
            buff[i]=(100*Math.sin(2*Math.PI/samplingRate*i*frequency)).toByte()
            //Log.d("buff",buff[i].toString())
        }

        return buff
    }
}
