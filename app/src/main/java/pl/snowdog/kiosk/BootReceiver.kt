package pl.snowdog.kiosk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Re-schedule after reboot
        setScheduler(context)
    }

}

fun setScheduler(context: Context){
    val sharedPref =
        context.getSharedPreferences(context.getString(R.string.storage_key), Context.MODE_PRIVATE) ?: return
    val time = sharedPref.getString(context.getString(R.string.reload_time), "02:00")?.split(":")
    if (time != null && time.count() == 2){
        val hour = time[0].toInt()
        val min = time[1].toInt()
        RefreshScheduler.scheduleDailyRefresh(context, hour, min)
    }
}