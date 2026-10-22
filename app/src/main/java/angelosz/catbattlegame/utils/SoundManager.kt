package angelosz.catbattlegame.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.media.ToneGenerator
import angelosz.catbattlegame.domain.enums.CombatModifier
import angelosz.catbattlegame.domain.enums.DamageType

object SoundManager {
    var soundEffectsEnabled: Boolean = true

    private var soundPool: SoundPool? = null
    private var toneGenerator: ToneGenerator? = try {
        ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME)
    } catch (e: Exception) {
        null
    }

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .setAudioAttributes(audioAttributes)
            .build()
    }

    fun playButtonClick(context: Context) {
        if (!soundEffectsEnabled) return
        try {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as? AudioManager
            audioManager?.playSoundEffect(AudioManager.FX_KEY_CLICK, 1.0f)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun playDamageSound(context: Context, damageType: DamageType) {
        if (!soundEffectsEnabled) return
        try {
            when (damageType) {
                DamageType.BLUNT, DamageType.PIERCING, DamageType.HEROIC -> {
                    toneGenerator?.startTone(ToneGenerator.TONE_DTMF_0, 150)
                }
                DamageType.ELEMENTAL, DamageType.ETHEREAL -> {
                    toneGenerator?.startTone(ToneGenerator.TONE_DTMF_D, 180)
                }
                DamageType.POISON -> {
                    toneGenerator?.startTone(ToneGenerator.TONE_PROP_PROMPT, 150)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun playHealSound(context: Context) {
        if (!soundEffectsEnabled) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_ACK, 200)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun playShieldSound(context: Context) {
        if (!soundEffectsEnabled) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP, 150)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun playStatusSound(context: Context, modifier: CombatModifier) {
        if (!soundEffectsEnabled) return
        try {
            when (modifier) {
                CombatModifier.STUNNED -> toneGenerator?.startTone(ToneGenerator.TONE_SUP_ERROR, 200)
                CombatModifier.SLOWED -> toneGenerator?.startTone(ToneGenerator.TONE_PROP_NACK, 180)
                CombatModifier.POISONED -> toneGenerator?.startTone(ToneGenerator.TONE_PROP_PROMPT, 150)
                else -> {}
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
