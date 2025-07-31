package pl.snowdog.kiosk

import android.app.Application
import androidx.core.content.edit

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
    }
}