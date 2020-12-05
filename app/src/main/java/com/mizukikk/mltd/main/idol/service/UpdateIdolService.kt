package com.mizukikk.mltd.main.idol.service

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.mizukikk.mltd.Inject
import com.mizukikk.mltd.R
import com.mizukikk.mltd.api.ResponseCallBack
import com.mizukikk.mltd.api.response.Card
import com.mizukikk.mltd.data.source.local.preferences.PreferencesHelper
import com.mizukikk.mltd.extension.nextUpdateTimeMillis
import com.mizukikk.mltd.main.MainActivity
import com.mizukikk.mltd.utils.ServiceUtils
import retrofit2.Call

class UpdateIdolService : Service() {


    companion object {
        private const val NAME = "UpdateIdolService"
        private val TAG = UpdateIdolService::class.java.simpleName
        private const val FOREGROUND_NOTIFICATION_ID = 114514
        private const val LAST_IDOL_ID = "lastIdolId"
        private const val NOTIFICATION_CHANNEL_ID = "updateDataService"
        const val UPDATE_RESULT = "updateResult"

        /**
         * when service is running start() will do noting
         */
        fun start(context: Context, lastIdolId: Int) {
            val isServiceRunning =
                ServiceUtils.isServiceRunning(
                    context,
                    UpdateIdolService::class.java.name
                )
            if (isServiceRunning.not()) {
                val intent = Intent(context, UpdateIdolService::class.java)
                intent.putExtra(LAST_IDOL_ID, lastIdolId)
                ContextCompat.startForegroundService(context, intent)
            }
        }
    }

    private val repository = Inject.providerMLTDRepository()

    override fun onCreate() {
        super.onCreate()
        val pendingIntent =
            Intent(this, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, 0)
            }
        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.stat_sys_download_done)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(getString(R.string.notification_idol_data_updating))
            .setContentIntent(pendingIntent)
            .build()
        startForeground(FOREGROUND_NOTIFICATION_ID, notification)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        downloadData(intent)
        return START_STICKY
    }

    private fun downloadData(intent: Intent?) {
        val lastIdolId = intent?.getIntExtra(LAST_IDOL_ID, 0)!!
        repository.checkUpdate(lastIdolId, object : ResponseCallBack<List<Card.CardResponse>>() {
            override fun success(response: List<Card.CardResponse>) {
                val update = response.isNotEmpty()
                if (update) {
                    updateDB()
                } else {
                    stopForeground(true)
                }
            }

            override fun fail(
                errorMessage: String,
                errorCode: Int?,
                call: Call<List<Card.CardResponse>>
            ) {
                stopForeground(true)
            }
        })
    }

    private fun updateDB() {
        repository.downloadAllCard(object : ResponseCallBack<List<Card.CardResponse>>() {
            override fun success(response: List<Card.CardResponse>) {
                val finishProgress = response.size
                repository.saveAll({ progress ->
                    if (finishProgress == progress) {
                        saveNextUpdateTimeMillis()
                        sendBroadcast(Intent(UPDATE_RESULT))
                        stopForeground(true)
                    }
                }, *response.toTypedArray())
            }

            override fun fail(
                errorMessage: String,
                errorCode: Int?,
                call: Call<List<Card.CardResponse>>
            ) {
                stopForeground(true)
            }
        })
    }

    private fun saveNextUpdateTimeMillis() {
        PreferencesHelper.nextUpdateTimeMillis =
            System.currentTimeMillis().nextUpdateTimeMillis()
    }

}
