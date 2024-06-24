package com.huiiro.ncn.core.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * 定时任务
 * 最小执行单元 15min
 *
 * @deprecated using alarm instead
 */
class CheckCrowWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    companion object {
        const val TAG = "CheckCrowWorker"
    }

    override fun doWork(): Result {
        Log.d(TAG, "doWork!")
        return Result.success()
    }
}