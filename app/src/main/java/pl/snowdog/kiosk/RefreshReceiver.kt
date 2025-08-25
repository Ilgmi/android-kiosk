package pl.snowdog.kiosk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class RefreshReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Re-schedule for tomorrow first (handles DST)
        setScheduler(context)

        // Bring/notify activity to refresh
        val i = Intent(context, WebviewActivity::class.java).apply {
            action = RefreshScheduler.ACTION_REFRESH
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        context.startActivity(i)
    }
}

