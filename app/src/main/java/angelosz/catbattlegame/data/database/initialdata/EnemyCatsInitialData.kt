package angelosz.catbattlegame.data.database.initialdata

import angelosz.catbattlegame.R
import angelosz.catbattlegame.data.entities.EnemyAbility
import angelosz.catbattlegame.data.entities.EnemyCat

class EnemyCatsInitialData {
    val enemyCats = listOf(
        EnemyCat(id = 1,
            name = "Wool Ball",
            baseHealth = 60f,
            baseAttack = 18f,
            baseDefense = 1.2f,
            attackSpeed = 1.25f,
            image = R.drawable.enemy_wool_ball_300
        ),
        EnemyCat(id = 2,
            name = "Mouse Thief",
            baseHealth = 45f,
            baseAttack = 11f,
            baseDefense = 1f,
            attackSpeed = 0.9f,
            image = R.drawable.enemy_mouse_thief_300),
        EnemyCat(id = 3,
            name = "Playful Puppy",
            baseHealth = 120f,
            baseAttack = 20f,
            baseDefense = 2f,
            attackSpeed = 1.3f,
            image = R.drawable.enemy_puppy_300),
        EnemyCat(id = 4, name = "Sparrow Scout",
            baseHealth = 120f, image = R.drawable.enemy_sparrow_apprentice_guard_300),
        EnemyCat(id = 5, name = "Sparrow Guard",
            baseHealth = 150f, image = R.drawable.enemy_sparrow_guard_300),
        EnemyCat(id = 6, name = "Raven Guard",
            baseHealth = 150f, image = R.drawable.enemy_raven_guard_300),
    )

    val enemyAbilities = listOf(
        EnemyAbility(enemyCatId = 1, abilityId = 11),
        EnemyAbility(enemyCatId = 1, abilityId = 12),
        EnemyAbility(enemyCatId = 2, abilityId = 1),
        EnemyAbility(enemyCatId = 3, abilityId = 10),
        EnemyAbility(enemyCatId = 3, abilityId = 11),
        EnemyAbility(enemyCatId = 3, abilityId = 12),
        EnemyAbility(enemyCatId = 4, abilityId = 1),
        EnemyAbility(enemyCatId = 4, abilityId = 13),
        EnemyAbility(enemyCatId = 5, abilityId = 1),
        EnemyAbility(enemyCatId = 5, abilityId = 13),
        EnemyAbility(enemyCatId = 6, abilityId = 1),
        EnemyAbility(enemyCatId = 6, abilityId = 2),
        EnemyAbility(enemyCatId = 6, abilityId = 11),
        EnemyAbility(enemyCatId = 6, abilityId = 12),
    )
}