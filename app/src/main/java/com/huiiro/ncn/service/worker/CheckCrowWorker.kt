package com.huiiro.ncn.service.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class CheckCrowWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        Log.d("CheckCrowWorker", "doWork!")
        return Result.success()
    }
}