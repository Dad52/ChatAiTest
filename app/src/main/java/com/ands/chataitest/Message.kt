package com.ands.chataitest

/**
 * Created by Dad52(Sobolev) on 7/2/2022.
 */
data class Message(
    val message: String,
    val user: Int
) {
    companion object {
        const val BOT_ID = 1
        const val USER_ID = 0
    }
}