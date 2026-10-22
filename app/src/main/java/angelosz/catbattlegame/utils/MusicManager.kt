package angelosz.catbattlegame.utils

import android.content.Context
import android.media.MediaPlayer
import angelosz.catbattlegame.R

object MusicManager {
    private var homeMediaPlayer: MediaPlayer? = null
    private var combatMediaPlayer: MediaPlayer? = null
    private var currentTrack: MusicTrack? = null

    enum class MusicTrack {
        HOME, COMBAT
    }

    fun play(context: Context, track: MusicTrack, musicEnabled: Boolean = true) {
        if (!musicEnabled) {
            pauseAll()
            return
        }

        if (currentTrack == track) {
            val activePlayer = when (track) {
                MusicTrack.HOME -> homeMediaPlayer
                MusicTrack.COMBAT -> combatMediaPlayer
            }
            if (activePlayer != null) {
                if (!activePlayer.isPlaying) {
                    try {
                        activePlayer.start()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                return
            }
        }

        pauseAll()

        val appContext = context.applicationContext
        try {
            when (track) {
                MusicTrack.HOME -> {
                    if (homeMediaPlayer == null) {
                        homeMediaPlayer = MediaPlayer.create(appContext, R.raw.sounds_background_home)?.apply {
                            isLooping = true
                            setVolume(1.0f, 1.0f)
                        }
                    }
                    homeMediaPlayer?.setVolume(1.0f, 1.0f)
                    homeMediaPlayer?.start()
                }
                MusicTrack.COMBAT -> {
                    if (combatMediaPlayer == null) {
                        combatMediaPlayer = MediaPlayer.create(appContext, R.raw.sounds_background_combat)?.apply {
                            isLooping = true
                            setVolume(1.0f, 1.0f)
                        }
                    }
                    combatMediaPlayer?.setVolume(1.0f, 1.0f)
                    combatMediaPlayer?.start()
                }
            }
            currentTrack = track
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun pauseAll() {
        try {
            if (homeMediaPlayer?.isPlaying == true) {
                homeMediaPlayer?.pause()
            }
            if (combatMediaPlayer?.isPlaying == true) {
                combatMediaPlayer?.pause()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stopAll() {
        try {
            homeMediaPlayer?.stop()
            homeMediaPlayer?.release()
            homeMediaPlayer = null

            combatMediaPlayer?.stop()
            combatMediaPlayer?.release()
            combatMediaPlayer = null
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            currentTrack = null
        }
    }
}
