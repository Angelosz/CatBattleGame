package angelosz.catbattlegame.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import angelosz.catbattlegame.utils.DataStoreConstants.DAILY_REWARD_STREAK
import angelosz.catbattlegame.utils.DataStoreConstants.HAS_COMPLETED_ALPHA_RESET_V1
import angelosz.catbattlegame.utils.DataStoreConstants.HAS_RECEIVED_HALLOWEEN_PACKAGE
import angelosz.catbattlegame.utils.DataStoreConstants.LAST_CAMPAIGN_SELECTED
import angelosz.catbattlegame.utils.DataStoreConstants.LAST_DAILY_REWARD_CLAIM_DATE
import angelosz.catbattlegame.utils.DataStoreConstants.SETTING_AUTO_TARGET
import angelosz.catbattlegame.utils.DataStoreConstants.SETTING_MUSIC_ENABLED
import angelosz.catbattlegame.utils.DataStoreConstants.SETTING_NOTIFICATIONS_ENABLED
import angelosz.catbattlegame.utils.DataStoreConstants.SETTING_SOUND_EFFECTS_ENABLED
import angelosz.catbattlegame.utils.DataStoreConstants.USER_DATASTORE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_DATASTORE)

class DataStoreRepository(
    private val context: Context
){
    suspend fun saveSelectedCampaign(campaignIndex: Int){
        context.dataStore.edit{
            it[LAST_CAMPAIGN_SELECTED] = campaignIndex.toString()
        }
    }

    suspend fun getSelectedCampaign(): Int = context.dataStore.data.first()[LAST_CAMPAIGN_SELECTED]?.toInt() ?: 0

    val settingsFlow: Flow<PlayerSettings> = context.dataStore.data.map { prefs ->
        PlayerSettings(
            autoTargetSelect = prefs[SETTING_AUTO_TARGET] ?: false,
            musicEnabled = prefs[SETTING_MUSIC_ENABLED] ?: true,
            soundEffectsEnabled = prefs[SETTING_SOUND_EFFECTS_ENABLED] ?: true,
            notificationsEnabled = prefs[SETTING_NOTIFICATIONS_ENABLED] ?: true
        )
    }

    suspend fun getSettings(): PlayerSettings{
        val prefs = context.dataStore.data.first()
        return PlayerSettings(
            autoTargetSelect = prefs[SETTING_AUTO_TARGET] ?: false,
            musicEnabled = prefs[SETTING_MUSIC_ENABLED] ?: true,
            soundEffectsEnabled = prefs[SETTING_SOUND_EFFECTS_ENABLED] ?: true,
            notificationsEnabled = prefs[SETTING_NOTIFICATIONS_ENABLED] ?: true
        )
    }

    suspend fun saveSettings(options: PlayerSettings){
        context.dataStore.edit{
            it[SETTING_AUTO_TARGET] = options.autoTargetSelect
            it[SETTING_MUSIC_ENABLED] = options.musicEnabled
            it[SETTING_SOUND_EFFECTS_ENABLED] = options.soundEffectsEnabled
            it[SETTING_NOTIFICATIONS_ENABLED] = options.notificationsEnabled
        }
    }

    suspend fun getDailyRewardState(): DailyRewardState {
        val prefs = context.dataStore.data.first()
        val lastClaimDate = prefs[LAST_DAILY_REWARD_CLAIM_DATE] ?: ""
        val streak = prefs[DAILY_REWARD_STREAK]?.toIntOrNull() ?: 0
        return DailyRewardState(lastClaimDate = lastClaimDate, streak = streak)
    }

    suspend fun recordDailyRewardClaim(todayDateString: String, newStreak: Int) {
        context.dataStore.edit {
            it[LAST_DAILY_REWARD_CLAIM_DATE] = todayDateString
            it[DAILY_REWARD_STREAK] = newStreak.toString()
        }
    }

    suspend fun resetDailyRewardsData() {
        context.dataStore.edit {
            it[LAST_DAILY_REWARD_CLAIM_DATE] = ""
            it[DAILY_REWARD_STREAK] = "0"
        }
    }

    suspend fun hasReceivedHalloweenPackage(): Boolean {
        val prefs = context.dataStore.data.first()
        return prefs[HAS_RECEIVED_HALLOWEEN_PACKAGE] ?: false
    }

    suspend fun markHalloweenPackageReceived() {
        context.dataStore.edit {
            it[HAS_RECEIVED_HALLOWEEN_PACKAGE] = true
        }
    }

    suspend fun resetHalloweenEventClaim() {
        context.dataStore.edit {
            it[HAS_RECEIVED_HALLOWEEN_PACKAGE] = false
        }
    }

    suspend fun hasCompletedAlphaResetV1(): Boolean {
        val prefs = context.dataStore.data.first()
        return prefs[HAS_COMPLETED_ALPHA_RESET_V1] ?: false
    }

    suspend fun markAlphaResetV1Completed() {
        context.dataStore.edit {
            it[HAS_COMPLETED_ALPHA_RESET_V1] = true
        }
    }

}

data class PlayerSettings(
    val autoTargetSelect: Boolean = false,
    val musicEnabled: Boolean = true,
    val soundEffectsEnabled: Boolean = true,
    val notificationsEnabled: Boolean = true
)

data class DailyRewardState(
    val lastClaimDate: String = "",
    val streak: Int = 0
)