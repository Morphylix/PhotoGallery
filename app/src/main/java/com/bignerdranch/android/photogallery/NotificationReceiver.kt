package com.bignerdranch.android.photogallery

import android.app.Activity
import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

private const val TAG = "NotificationReceiver"

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.i(TAG, "Received broadcast: ${p1?.action}")
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        val requestCode = p1?.getIntExtra(PollWorker.REQUEST_CODE, 0)
        val notification: Notification? = p1?.getParcelableExtra(PollWorker.NOTIFICATION)
        val notificationManager = p0?.let { NotificationManagerCompat.from(it) }
        if (requestCode != null && notification != null) {
            notificationManager?.notify(requestCode, notification)
        }
    }
}