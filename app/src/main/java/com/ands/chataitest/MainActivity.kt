package com.ands.chataitest

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ands.chataitest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private var recordAudioRequestCode = 1
    private val messagesAdapter = MessagesAdapter()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        checkAudioPermission()
        setupListeners()
        setupRV()

        val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale("Russian", "RU"))
        }

        speechRecognizer.setRecognitionListener(object : SimpleRecognitionListener() {
            override fun onResults(bundle: Bundle?) {
                val data = bundle?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                binding.messageEditText.setText(data?.get(0))
            }
        })

        binding.micBtn.setOnTouchListener { _, motion ->

            if (motion?.action == MotionEvent.ACTION_UP) {
                binding.micBtn.colorFilter = null
                speechRecognizer.stopListening()
            }
            if (motion?.action == MotionEvent.ACTION_DOWN) {
                binding.micBtn.setColorFilter(resources.getColor(R.color.teal_200))
                speechRecognizer.startListening(intent)
                vibrate()
            }
            false
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.changeName) Toast.makeText(this, "Change name selected", Toast.LENGTH_SHORT).show()
        return super.onOptionsItemSelected(item)
    }

    private fun setupRV() = with(binding) {
        listMessageRV.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = messagesAdapter
        }
        viewModel.messages.observe(this@MainActivity) {
            messagesAdapter.submitList(it)
            messagesAdapter.notifyDataSetChanged()
            listMessageRV.scrollToPosition(messagesAdapter.itemCount - 1)
        }
    }

    private fun checkAudioPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                recordAudioRequestCode)
        }
    }

    private fun setupListeners() = with(binding) {
        sendBtn.setOnClickListener() {
            viewModel.getAnswer(messageEditText.text.toString())
            messageEditText.text.clear()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == recordAudioRequestCode && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) Toast.makeText(this,
                "Permission Granted",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun vibrate() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(200, 1))
            } else {
                vibrator.vibrate(200)
            }
        }
    }


}