package com.example.a160420046_todoapp.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Build.VERSION_CODES
import androidx.core.app.NotificationCompat
import com.example.a160420046_todoapp.R
import com.example.a160420046_todoapp.view.MainActivity

class NotificationHelper (val context: Context) {
    private val CHANNEL_ID = "todo_channel_id"
    private val CHANNEL_NAME = "todo_channel_name"
    private val NOTIFICATION_ID = 1

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = "Todo channel description"

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

    fun createNotification(title: String, message:String){
        createNotificationChannel()
        val intent = Intent(context, MainActivity::class.java)
//       Intent untuk memanggil MAIN Activity kalau notifikasinya dipencet
//        Clear TASK  berfungsi untuk membersihkan backstack sehingga hanya MAIN ACTIVITY yang ada di backstack
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//    Pending Intent berfungsi untuk dititipkan ke aplikasi lain seperti Aplication Manager dll untuk menjalankan intent apabila aplikasi tidak sedang dibuka
        val pendingIntent = PendingIntent.getActivities(context, 0, arrayOf(intent), PendingIntent.FLAG_IMMUTABLE)

        val icon = BitmapFactory.decodeResource(context.resources, R.drawable.todochar)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.checklist)
            .setLargeIcon(icon)
            .setContentTitle(title)
            .setContentText(message)
    }
}