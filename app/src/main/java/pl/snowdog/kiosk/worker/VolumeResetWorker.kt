// VolumeResetWorker.kt
import android.content.Context
import android.media.AudioManager
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class VolumeResetWorker(
    ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        val am = applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        val prefs = applicationContext.getSharedPreferences("pl.snowdog.kios.storage", Context.MODE_PRIVATE)


        // Choose the stream you care about. STREAM_MUSIC is usually what you want for media.
        val stream = AudioManager.STREAM_MUSIC

        val max = am.getStreamMaxVolume(stream)
        // If you literally want index=10, clamp it to device max.
        val current = am.getStreamVolume(AudioManager.STREAM_MUSIC).toFloat()
        val audio = prefs.getFloat("AUDIO_VOLUME", current).toInt()
        val targetIndex = audio.coerceAtMost(max).coerceAtLeast(0)

        // Optionally, if you want “10 out of 100%”, compute index from percentage:
        // val targetIndex = ((max * 0.10f).toInt()).coerceIn(0, max)

        am.setStreamVolume(stream, targetIndex, /*flags*/ 0)
        return Result.success()
    }
}
