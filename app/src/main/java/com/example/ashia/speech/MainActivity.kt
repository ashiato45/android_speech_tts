package com.example.ashia.speech

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.View
import android.widget.EditText
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.KeyEvent
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.view.*
import org.w3c.dom.Text
import java.util.*


class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var sp : TextToSpeech
    override fun onInit(p0: Int) {
        findViewById<Button>(R.id.btn_speak).isEnabled = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sp = TextToSpeech(this, this)
    }

    private val SPEECH_REQUEST_CODE = 0

    // Create an intent that can start the Speech Recognizer activity
    private fun displaySpeechRecognizer() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        // Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, SPEECH_REQUEST_CODE)
    }

    // This callback is invoked when the Speech Recognizer returns.
    // This is where you process the intent and extract the speech text from the intent.
    override fun onActivityResult(requestCode: Int, resultCode: Int,
                                  data: Intent?) {
        // data has to be nullable type because data is null if the speech is cancelled!
        if(data != null) {
            if (requestCode == SPEECH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                val results = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS)
                val spokenText = results[0]
                // Do something with spokenText
                findViewById<EditText>(R.id.editText).setText(spokenText)
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Log.e("mine", "cancelled!")
            }


            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun onRecognizeClicked(view: View){
        displaySpeechRecognizer()

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun onSpeakClicked(view: View){
        val v = findViewById<EditText>(R.id.editText)
        val vv: String = v.text.toString()

        sp.speak(vv, TextToSpeech.QUEUE_FLUSH, null, "test")

    }

}
