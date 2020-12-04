package ru.ridkeim.broadcastreceiverexample.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

open class MessageReceiver : BroadcastReceiver() {
    companion object {
        const val MESSAGE_KEY = "ru.ridkeim.brodcastreceiverexample.MESSAGE"
        const val ACTION_IMPLICIT_MESSAGE = "ru.ridkeim.brodcastreceiverexample.action.IMPLICIT_MESSAGE"
        const val ACTION_IMPLICIT_MESSAGE2 = "ru.ridkeim.brodcastreceiverexample.action.IMPLICIT_MESSAGE2"
        const val ACTION_FILTERED = "ru.ridkeim.brodcastreceiverexample.action.FILTERED"
    }
    override fun onReceive(context: Context, intent: Intent) {
        val s = intent.getStringExtra(MESSAGE_KEY) ?: ""
        val message = when (intent.action) {
            ACTION_IMPLICIT_MESSAGE -> "Action implicit: $s"
            ACTION_IMPLICIT_MESSAGE2 -> "Action implicit: $s"
            null -> "Action null: $s"
            else -> "Эммм что? $s"
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}