package com.ands.chataitest

import android.os.Bundle
import android.speech.RecognitionListener

/**
 * Created by Dad52(Sobolev) on 6/28/2022.
 */
abstract class SimpleRecognitionListener: RecognitionListener {
    override fun onReadyForSpeech(p0: Bundle?) {}

    override fun onBeginningOfSpeech() {}

    override fun onRmsChanged(p0: Float) {}

    override fun onBufferReceived(p0: ByteArray?) {

    }

    override fun onEndOfSpeech() {

    }

    override fun onError(p0: Int) {

    }

    abstract override fun onResults(bundle: Bundle?)

    override fun onPartialResults(p0: Bundle?) {

    }

    override fun onEvent(p0: Int, p1: Bundle?) {

    }
}