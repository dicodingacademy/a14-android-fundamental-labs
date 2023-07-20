package com.dicoding.picodiploma.mybroadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log

class SmsReceiver : BroadcastReceiver() {

    companion object {
        private val TAG = SmsReceiver::class.java.simpleName
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            for (message in messages) {
                val senderNum = message.originatingAddress
                val body = message.messageBody
                Log.d(TAG, "senderNum: $senderNum; message: $message")
                val showSmsIntent = Intent(context, SmsReceiverActivity::class.java)
                showSmsIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                showSmsIntent.putExtra(SmsReceiverActivity.EXTRA_SMS_NO, senderNum)
                showSmsIntent.putExtra(SmsReceiverActivity.EXTRA_SMS_MESSAGE, body)
                context.startActivity(showSmsIntent)
            }
        }
    }
}
