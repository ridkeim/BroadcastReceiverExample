package ru.ridkeim.broadcastreceiverexample.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class PowerStateReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val message = when(intent.action){
            Intent.ACTION_POWER_DISCONNECTED -> "Power disconnected"
            Intent.ACTION_POWER_CONNECTED -> "Power connected"
            else -> "gerarahere"
        }
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

}