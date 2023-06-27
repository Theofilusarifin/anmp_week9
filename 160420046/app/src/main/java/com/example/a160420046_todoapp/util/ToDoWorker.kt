package com.example.a160420046_todoapp.util

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class ToDoWorker (val context: Context, val params: WorkerParameters) : Worker(context, params){
    override fun doWork() : Result{
        NotificationHelper(context)
            .createNotification(inputData.getString("title").toString(),
                                inputData.getString("message").toString())
        return Result.success()
    }
}