package com.ands.chataitest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Dad52(Sobolev) on 7/2/2022.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiService: ApiService
): ViewModel() {

    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> = _messages

    private val messagesList = mutableListOf<Message>()

    private val uid = "Dad52"

    fun getAnswer(msg: String) = viewModelScope.launch {

        addUserMessage(msg)

        try {
            apiService.getAnswer(uid, msg).let { response ->
                if (response.isSuccessful) {
                    addBotMessage(response.body()!!.cnt)
                } else {
                    Log.e("TAG", "${response.errorBody()}")
                }
            }
        } catch (e: Exception) {
            Log.e("TAG", e.localizedMessage)

        }

    }

    private fun addUserMessage(msg: String) {
        messagesList.add(Message(message = msg, user = Message.USER_ID))
        _messages.value = messagesList
    }

    private fun addBotMessage(msg: String) {
        messagesList.add(Message(message = msg, user = Message.BOT_ID))
        _messages.value = messagesList
    }

}