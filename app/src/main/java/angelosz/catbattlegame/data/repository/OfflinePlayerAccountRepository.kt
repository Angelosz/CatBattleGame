package angelosz.catbattlegame.data.repository

import angelosz.catbattlegame.data.dao.PlayerDao
import angelosz.catbattlegame.data.entities.EnemyDiscoveryState
import angelosz.catbattlegame.data.entities.OwnedCat
import angelosz.catbattlegame.data.entities.PlayerAccount
import angelosz.catbattlegame.data.entities.PlayerTeam
import angelosz.catbattlegame.data.entities.PlayerTeamOwnedCat
import angelosz.catbattlegame.data.entities.notifications.BattleChestNotificationEntity
import angelosz.catbattlegame.data.entities.notifications.CatNotificationEntity
import angelosz.catbattlegame.data.entities.notifications.CurrencyNotificationEntity
import angelosz.catbattlegame.data.entities.notifications.NotificationsEntity
import angelosz.catbattlegame.ui.armory.data.SimpleArmoryCatData
import angelosz.catbattlegame.ui.combat.BasicCatData
import angelosz.catbattlegame.ui.home.notifications.BattleChestNotification
import angelosz.catbattlegame.ui.home.notifications.CatEvolutionNotification
import angelosz.catbattlegame.ui.home.notifications.CurrencyRewardNotification
import angelosz.catbattlegame.ui.home.notifications.LevelUpNotification
import angelosz.catbattlegame.ui.home.notifications.NotificationType
import kotlinx.coroutines.flow.Flow

class OfflinePlayerAccountRepository(val dao: PlayerDao): PlayerAccountRepository {
    /* Player Account */
    override suspend fun getPlayerAccountAsFlow(): Flow<PlayerAccount?> = dao.getPlayerAccountAsFlow()
    override suspend fun getPlayerAccount(): PlayerAccount? = dao.getPlayerAccount()
    override suspend fun insertPlayerAccount(playerAccount: PlayerAccount) = dao.insertPlayerAccount(playerAccount)
    override suspend fun updateAccount(playerAccount: PlayerAccount) = dao.updateAccount(playerAccount)

    /* Crystals */
    override suspend fun getCrystalsAmount(): Int = dao.getCrystalsAmount()
    override suspend fun addCrystals(amount: Int) = dao.addCrystals(amount)
    override suspend fun reduceCrystals(amount: Int) {
        val crystals = dao.getPlayerAccount()?.crystals
        if(crystals != null){
            if(amount > crystals){
                dao.reduceCrystals(crystals)
            } else {
                dao.reduceCrystals(amount)
            }
        }

    }

    /* Gold */
    override suspend fun getGoldAmount(): Int = dao.getGoldAmount()
    override suspend fun addGold(amount: Int) = dao.addGold(amount)
    override suspend fun reduceGold(amount: Int) {
        val gold = dao.getPlayerAccount()?.gold
        if(gold != null){
            if(amount > gold){
                dao.reduceGold(gold)
            } else {
                dao.reduceGold(amount)
            }
        }
    }

    /* Owned Cats */
    override suspend fun insertOwnedCat(ownedCat: OwnedCat) = dao.insertOwnedCat(ownedCat)
    override suspend fun insertOwnedCats(ownedCats: List<OwnedCat>) = dao.insertOwnedCats(ownedCats)
    override suspend fun updateOwnedCat(ownedCat: OwnedCat) = dao.updateOwnedCat(ownedCat)
    override suspend fun deleteOwnedCat(ownedCat: OwnedCat) = dao.deleteOwnedCat(ownedCat)
    override suspend fun getAllOwnedCats(): List<OwnedCat> = dao.getAllOwnedCats()
    override suspend fun getOwnedCatsByCatIds(catIds: List<Int>): List<OwnedCat> = dao.getOwnedCatsByIds(catIds)
    override suspend fun getOwnedCatByCatId(catId: Int): OwnedCat = dao.getOwnedCatByCatId(catId)
    override suspend fun getPaginatedOwnedCats(limit: Int, offset: Int): List<OwnedCat> = dao.getPaginatedOwnedCats(limit, offset)
    override suspend fun getOwnedCatsCount(): Int = dao.getCount()
    override suspend fun ownsCat(catId: Int): Boolean = dao.ownsCat(catId)
    override suspend fun getSimpleArmoryCatsDataPage(limit: Int, offset: Int ): List<SimpleArmoryCatData> = dao.getSimpleCatsDataWithExperiencePage(limit, offset)
    override suspend fun getSimpleCatsDataFromTeam(teamId: Long): List<SimpleArmoryCatData> = dao.getSimpleArmoryCatsDataFromTeam(teamId)

    /* Player Teams */
    override suspend fun insertPlayerTeam(playerTeam: PlayerTeam): Long = dao.insertPlayerTeam(playerTeam)
    override suspend fun updatePlayerTeam(playerTeam: PlayerTeam) = dao.updatePlayerTeam(playerTeam)
    override suspend fun deleteTeam(playerTeam: PlayerTeam) = dao.deleteTeam(playerTeam)
    override suspend fun teamExists(teamId: Long): Boolean = dao.teamExists(teamId)
    override suspend fun clearTeam(teamId: Long) = dao.clearTeam(teamId)
    override suspend fun deleteTeamById(teamId: Long) = dao.deleteTeamById(teamId)
    override suspend fun getPlayerTeamById(teamId: Long) = dao.getPlayerTeamById(teamId)
    override suspend fun getAllPlayerTeams(): List<PlayerTeam> = dao.getAllPlayerTeams()
    override suspend fun addCatToTeam(playerTeamOwnedCat: PlayerTeamOwnedCat) = dao.addCatToTeam(playerTeamOwnedCat)
    override suspend fun getTeamData(teamId: Long): List<BasicCatData> {
        val catBasicData = dao.getPlayerTeamCats(teamId)
        return catBasicData
    }

    override suspend fun discoverEnemies(enemies: List<EnemyDiscoveryState>) = dao.discoverEnemies(enemies)

    /* Notifications */
    override suspend fun getAllNotificationsAsFlow(): Flow<List<NotificationsEntity>> = dao.getAllNotificationsFlow()
    override suspend fun getAllNotifications(): List<NotificationsEntity> = dao.getAllNotifications()
    override suspend fun deleteNotification(notificationId: Long, type: NotificationType) {
        when(type) {
            NotificationType.LEVEL_UP -> dao.deleteCatNotification(notificationId)
            NotificationType.CAT_EVOLUTION -> dao.deleteCatNotification(notificationId)
            NotificationType.BATTLE_CHEST_REWARD -> dao.deleteBattleChestNotification(notificationId)
            NotificationType.CURRENCY_REWARD -> dao.deleteCurrencyNotification(notificationId)
        }
        dao.deleteNotification(notificationId)
    }

    override suspend fun insertLevelUpNotification(notification: LevelUpNotification) {
        val id = dao.insertCatNotification(
            CatNotificationEntity(
                catName = notification.name,
                catImage = notification.image,
                level = notification.level,
                notificationText = notification.notificationText,
                notificationType = NotificationType.LEVEL_UP
            )
        )
        dao.insertNotification(NotificationsEntity(id, NotificationType.LEVEL_UP))
    }
    override suspend fun insertEvolutionNotification(notification: CatEvolutionNotification) {
        val id = dao.insertCatNotification(
            CatNotificationEntity(
                catName = notification.name,
                catImage = notification.image,
                evolutionName = notification.evolutionName,
                evolutionImage = notification.evolutionImage,
                notificationText = notification.notificationText,
                notificationType = NotificationType.CAT_EVOLUTION
            )
        )
        dao.insertNotification(NotificationsEntity(id, NotificationType.CAT_EVOLUTION))
    }
    override suspend fun getCatEvolutionNotification(notificationId: Long): CatEvolutionNotification {
        val catNotificationEntity = dao.getCatNotification(notificationId)
        return CatEvolutionNotification(
            id = catNotificationEntity.id,
            name = catNotificationEntity.catName,
            image = catNotificationEntity.catImage,
            evolutionName = catNotificationEntity.evolutionName,
            evolutionImage = catNotificationEntity.evolutionImage,
            notificationText = catNotificationEntity.notificationText
        )
    }
    override suspend fun getLevelUpNotification(notificationId: Long): LevelUpNotification {
        val notification = dao.getCatNotification(notificationId)
        return LevelUpNotification(
            id = notification.id,
            name = notification.catName,
            image = notification.catImage,
            level = notification.level,
            notificationText = notification.notificationText
        )
    }

    override suspend fun insertBattleChestNotification(notification: BattleChestNotification) {
        val id = dao.insertBattleChestNotification(
            BattleChestNotificationEntity(
                battleChestRarity = notification.rarity,
                battleChestType = notification.battleChestType,
                notificationType = NotificationType.BATTLE_CHEST_REWARD
            )
        )
        dao.insertNotification(NotificationsEntity(id, NotificationType.BATTLE_CHEST_REWARD))
    }

    override suspend fun getBattleChestNotification(notificationId: Long): BattleChestNotification {
        val notification = dao.getBattleChestNotification(notificationId)
        return BattleChestNotification(
            id = notification.id,
            rarity = notification.battleChestRarity,
            battleChestType = notification.battleChestType,
            notificationText = notification.notificationText
        )
    }

    override suspend fun insertCurrencyNotification(notification: CurrencyRewardNotification) {
        val id = dao.insertCurrencyNotification(
            CurrencyNotificationEntity(
                type = notification.currencyType,
                amount = notification.amount,
                notificationText = notification.notificationText,
                notificationType = NotificationType.CURRENCY_REWARD
            )
        )
        dao.insertNotification(NotificationsEntity(id, NotificationType.CURRENCY_REWARD))
    }

    override suspend fun getCurrencyNotification(notificationId: Long): CurrencyRewardNotification {
        val notification = dao.getCurrencyNotification(notificationId)
        return CurrencyRewardNotification(
            id = notification.id,
            currencyType = notification.type,
            amount = notification.amount,
            notificationText = notification.notificationText
        )
    }
}