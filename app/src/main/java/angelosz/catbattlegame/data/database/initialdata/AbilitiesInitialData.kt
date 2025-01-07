package angelosz.catbattlegame.data.database.initialdata

import angelosz.catbattlegame.R
import angelosz.catbattlegame.data.entities.Ability
import angelosz.catbattlegame.data.entities.CatAbilityCrossRef
import angelosz.catbattlegame.domain.enums.AbilitySource
import angelosz.catbattlegame.domain.enums.AbilityTarget
import angelosz.catbattlegame.domain.enums.AbilityType
import angelosz.catbattlegame.domain.enums.CombatModifiers
import angelosz.catbattlegame.domain.enums.DamageType

class AbilitiesInitialData {
    val abilities = listOf(
        Ability(
            id = 1,
            name = "Quick Attack",
            description = "\"A fast attack that deals immediate damage with superior action speed.\"",
            image = R.drawable.ability_quick_attack_256,
            icon = R.drawable.ability_quick_attack_48,
            abilityType = AbilityType.DAMAGE,
            damageType = DamageType.PIERCING,
            attackSpeedMultiplier = 0.75f,
            damageMultiplier = 0.75f,
            combatModifier = null,
            targets = AbilityTarget.SINGLE_ENEMY,
            cooldown = 0
        ),
        Ability(
            id = 2,
            name = "Heal Ally",
            description = "\"Restores health to an ally, healing their wounds with restorative magic.\"",
            image = R.drawable.ability_heal_ally_256,
            icon = R.drawable.ability_heal_ally_48,
            abilityType = AbilityType.HEALING,
            damageType = DamageType.ELEMENTAL,
            attackSpeedMultiplier = 1f,
            damageMultiplier = 1.2f,
            combatModifier = null,
            targets = AbilityTarget.ALLY,
            cooldown = 1
        ),
        Ability(
            id = 3,
            name = "Defend Ally",
            description = "\"Protects an ally, reducing the damage they take for a brief period.\" Ally becomes SHIELDED for the next damage they receive.",
            image = R.drawable.ability_defend_ally_256,
            icon = R.drawable.ability_defend_ally_48,
            abilityType = AbilityType.STATUS_CHANGING,
            damageType = DamageType.ELEMENTAL,
            attackSpeedMultiplier = 1f,
            damageMultiplier = 1f,
            combatModifier = CombatModifiers.SHIELDED,
            targets = AbilityTarget.ALLY,
            cooldown = 3
        ),
        Ability(
            id = 4,
            name = "Back Stab",
            description = "\"A surprise attack from behind that deals extra damage.\"",
            image = R.drawable.ability_backstab_256,
            icon = R.drawable.ability_backstab_48,
            abilityType = AbilityType.DAMAGE,
            damageType = DamageType.PIERCING,
            attackSpeedMultiplier = 1f,
            damageMultiplier = 1.2f,
            combatModifier = null,
            targets = AbilityTarget.SINGLE_ENEMY,
            cooldown = 1
        ),
        Ability(
            id = 5,
            name = "Fire Ball",
            description = "\"Launches a fireball that explodes on impact, dealing area damage.\"",
            image = R.drawable.ability_fire_ball_256,
            icon = R.drawable.ability_fire_ball_48,
            abilityType = AbilityType.DAMAGE,
            damageType = DamageType.ELEMENTAL,
            attackSpeedMultiplier = 2f,
            damageMultiplier = 0.8f,
            combatModifier = null,
            targets = AbilityTarget.ENEMY_TEAM,
            cooldown = 2
        ),
        Ability(
            id = 6,
            name = "Magic Attack",
            description = "\"A magical spell that deals direct damage to the enemy.\"",
            image = R.drawable.ability_wand_attack_256,
            icon = R.drawable.ability_wand_attack_48,
            abilityType = AbilityType.DAMAGE,
            damageType = DamageType.ELEMENTAL,
            attackSpeedMultiplier = 1f,
            damageMultiplier = 1f,
            combatModifier = null,
            targets = AbilityTarget.SINGLE_ENEMY,
            cooldown = 0
        ),
        Ability(
            id = 7,
            name = "Punch",
            description = "\"A straightforward strike with a powerful paw.\"",
            image = R.drawable.ability_punch_256,
            icon = R.drawable.ability_punch_48,
            abilityType = AbilityType.DAMAGE,
            damageType = DamageType.BLUNT,
            attackSpeedMultiplier = 1f,
            damageMultiplier = 1f,
            combatModifier = null,
            targets = AbilityTarget.SINGLE_ENEMY,
            cooldown = 0
        ),
        Ability(
            id = 8,
            name = "Healing Punch",
            description = "\"A blow infused with restorative energy, healing an ally.\"",
            image = R.drawable.ability_healing_punch_256,
            icon = R.drawable.ability_healing_punch_48,
            abilityType = AbilityType.HEALING,
            damageType = DamageType.ELEMENTAL,
            attackSpeedMultiplier = 1f,
            damageMultiplier = 1f,
            combatModifier = null,
            targets = AbilityTarget.ALLY,
            cooldown = 0
        ),
        Ability(
            id = 9,
            name = "Inspire",
            description = "\"Motivates allies, boosting their morale and effectiveness in combat.\"",
            image = R.drawable.ability_inspire_allies_256,
            icon = R.drawable.ability_inspire_allies_48,
            abilityType = AbilityType.STATUS_CHANGING,
            damageType = DamageType.ELEMENTAL,
            attackSpeedMultiplier = 1f,
            damageMultiplier = 1f,
            combatModifier = null,
            targets = AbilityTarget.TEAM,
            cooldown = 0
        ),
        Ability(
            id = 10,
            name = "Justice",
            description = "\"A righteous attack that punishes foes with unwavering fairness.\" Applies STUNNED to the enemy.",
            image = R.drawable.ability_paladin_stun_256,
            icon = R.drawable.ability_paladin_stun_48,
            abilityType = AbilityType.STATUS_CHANGING,
            damageType = DamageType.ELEMENTAL,
            attackSpeedMultiplier = 1f,
            damageMultiplier = 1f,
            combatModifier = null,
            targets = AbilityTarget.SINGLE_ENEMY,
            cooldown = 0
        ),
        Ability(
            id = 11,
            name = "Roll",
            description = "A roll that deals damage.",
            image = R.drawable.ability_punch_256,
            icon = R.drawable.ability_punch_48,
            abilityType = AbilityType.DAMAGE,
            damageType = DamageType.BLUNT,
            attackSpeedMultiplier = 1.0f,
            damageMultiplier = 1.0f,
            combatModifier = null,
            targets = AbilityTarget.SINGLE_ENEMY,
            cooldown = 0,
            abilitySource = AbilitySource.ENEMY
        ),
        Ability(
            id = 12,
            name = "Big Roll",
            description = "A powerful roll that deals extra damage.",
            image = R.drawable.ability_punch_256,
            icon = R.drawable.ability_punch_48,
            abilityType = AbilityType.DAMAGE,
            damageType = DamageType.BLUNT,
            attackSpeedMultiplier = 1.2f,
            damageMultiplier = 0.8f,
            combatModifier = null,
            targets = AbilityTarget.ENEMY_TEAM,
            cooldown = 2,
            abilitySource = AbilitySource.ENEMY
        )
    )

    // catId to abilityIds
    val catAbilityCrossRef: Map<Int, List<Int>> = mapOf(
        1 to listOf(1),
        2 to listOf(1),
        3 to listOf(1),
        4 to listOf(1, 3),
        5 to listOf(6, 2),
        6 to listOf(6, 2),
        7 to listOf(6, 2),
        8 to listOf(6, 2),
        9 to listOf(1, 3),
        10 to listOf(1, 3),
        11 to listOf(1, 3),
        12 to listOf(1, 3, 2),
        13 to listOf(6),
        14 to listOf(6),
        15 to listOf(6, 5),
        16 to listOf(6, 5),
        17 to listOf(1),
        18 to listOf(1),
        19 to listOf(4),
        20 to listOf(4),
        21 to listOf(7),
        22 to listOf(7, 8),
        23 to listOf(7, 8, 9),
        24 to listOf(7, 8, 9)
    )

    fun getCatAbilitiesCrossRef(): List<CatAbilityCrossRef> =
        catAbilityCrossRef.flatMap { (catId, abilityIds) ->
            abilityIds.map { abilityId ->
                CatAbilityCrossRef(catId, abilityId)
            }
        }
}