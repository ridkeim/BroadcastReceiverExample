package ru.ridkeim.broadcastreceiverexample.activity

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import ru.ridkeim.broadcastreceiverexample.receivers.MessageReceiver
import ru.ridkeim.broadcastreceiverexample.R
import ru.ridkeim.broadcastreceiverexample.receivers.MessageReceiverLocal
import ru.ridkeim.broadcastreceiverexample.receivers.PowerStateReceiver

class MainActivity : AppCompatActivity() {
    private lateinit var localBroadcastManager: LocalBroadcastManager
    private lateinit var intentFilter: IntentFilter
    private lateinit var messageReceiverLocal1 : BroadcastReceiver
    private lateinit var messageReceiverLocal2 : BroadcastReceiver
    private lateinit var powerStateReceiver: PowerStateReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        localBroadcastManager = LocalBroadcastManager.getInstance(applicationContext)

        findViewById<Button>(R.id.button1).setOnClickListener {
            Intent().apply {
                action = MessageReceiver.ACTION_IMPLICIT_MESSAGE
                putExtra(MessageReceiver.MESSAGE_KEY, "implicit in manifest")
            }.also {
                applicationContext.sendBroadcast(it)
            }
        }

        findViewById<Button>(R.id.button2).setOnClickListener {
            Intent().apply {
                component = ComponentName(applicationContext, MessageReceiver::class.java)
                putExtra(MessageReceiver.MESSAGE_KEY, "explicit in manifest")
            }.also {
                applicationContext.sendBroadcast(it)
            }
        }

        findViewById<Button>(R.id.button3).setOnClickListener {
            Intent().apply {
                action = MessageReceiver.ACTION_IMPLICIT_MESSAGE2
                putExtra(MessageReceiver.MESSAGE_KEY, "implicit local bm")
            }.also {
                localBroadcastManager.sendBroadcast(it)
            }
        }

        findViewById<Button>(R.id.button4).setOnClickListener {
            Intent().apply {
                component = ComponentName(applicationContext, MessageReceiverLocal::class.java)
                putExtra(MessageReceiver.MESSAGE_KEY, "explicit local bm")
            }.also {
                localBroadcastManager.sendBroadcast(it)
            }
        }

        findViewById<Button>(R.id.button5).setOnClickListener {
            Intent().apply {
                action = MessageReceiver.ACTION_IMPLICIT_MESSAGE2
                putExtra(MessageReceiver.MESSAGE_KEY, "implicit in code")
            }.also {
                applicationContext.sendBroadcast(it)
            }
        }

        findViewById<Button>(R.id.button6).setOnClickListener {
            Intent().apply {
                component = ComponentName(applicationContext, MessageReceiverLocal::class.java)
                putExtra(MessageReceiver.MESSAGE_KEY, "explicit in code")
            }.also {
                applicationContext.sendBroadcast(it)
            }
        }

        messageReceiverLocal1 = MessageReceiverLocal()
        messageReceiverLocal2 = MessageReceiverLocal()
        powerStateReceiver = PowerStateReceiver()

        intentFilter = IntentFilter().apply {
            addAction(MessageReceiver.ACTION_IMPLICIT_MESSAGE2)
            addCategory(Intent.CATEGORY_DEFAULT)
        }
    }

    override fun onStart() {
        super.onStart()

        localBroadcastManager.registerReceiver(
                messageReceiverLocal1,
                intentFilter
        )
        registerReceiver(
                messageReceiverLocal2,
                intentFilter
        )
        registerReceiver(powerStateReceiver,
                IntentFilter().apply {
                    addAction(Intent.ACTION_POWER_CONNECTED)
                    addAction(Intent.ACTION_POWER_DISCONNECTED)
                }
        )
    }

    override fun onStop() {
        super.onStop()
        localBroadcastManager.unregisterReceiver(messageReceiverLocal1)
        unregisterReceiver(messageReceiverLocal2)
        unregisterReceiver(powerStateReceiver)
    }
}