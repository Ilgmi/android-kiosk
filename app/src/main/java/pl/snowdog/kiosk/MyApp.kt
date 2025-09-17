package pl.snowdog.kiosk

import VolumeResetWorker
import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        val sharedPref =
            getSharedPreferences(getString(R.string.storage_key), MODE_PRIVATE) ?: return
        val autostart = sharedPref.getBoolean(getString(R.string.autostart), false)

        if (autostart){
            sharedPref.edit(commit = true) {
                putBoolean(getString(R.string.edit_key), false)
            }
        }

        scheduleVolumeReset(this)
    }

    fun scheduleVolumeReset(context: Context) {
        val request = PeriodicWorkRequestBuilder<VolumeResetWorker>(
            15, java.util.concurrent.TimeUnit.MINUTES
        )
            // Optional: add constraints if you like (but not required here)
            //.setConstraints(Constraints.NONE)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "reset_volume",
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }

}