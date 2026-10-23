package angelosz.catbattlegame.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreConstants{
    const val USER_DATASTORE = "user_data"
    val LAST_CAMPAIGN_SELECTED = stringPreferencesKey("campaign")
    val SETTING_AUTO_TARGET = booleanPreferencesKey("auto_target_select")
    val SETTING_MUSIC_ENABLED = booleanPreferencesKey("setting_music_enabled")
    val SETTING_SOUND_EFFECTS_ENABLED = booleanPreferencesKey("setting_sound_effects_enabled")
    val SETTING_NOTIFICATIONS_ENABLED = booleanPreferencesKey("setting_notifications_enabled")
    val LAST_DAILY_REWARD_CLAIM_DATE = stringPreferencesKey("last_daily_reward_claim_date")
    val DAILY_REWARD_STREAK = stringPreferencesKey("daily_reward_streak")
    val HAS_RECEIVED_HALLOWEEN_PACKAGE = booleanPreferencesKey("has_received_halloween_package")
    val HAS_COMPLETED_ALPHA_RESET_V1 = booleanPreferencesKey("has_completed_alpha_reset_v1")
}