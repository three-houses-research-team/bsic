package bsi.blocks

import bsi.blocks.BsiCondition.*
import bsi.models.BaiSlot
import bsi.models.FeCharacter
import utils.BooleanOfLength
import utils.OfLength

val bsiConditionIds = mapOf(
  110u to MovedSpecificUnitInRectangle::class,
  320u to MovedAnyUnitInRectangle::class,
  99998u to AndContainer::class,
  99999u to OrContainer::class,
  1060u to Characters::class,
  340u to Route::class,
  0x172u to BylethGender::class,
  0x4a6u to DeathFlag::class,
  0u to HasOtherScriptRun::class,
  0x3fcu to Unk3fc::class,
  140u to Difficulty::class,
  80u to CheckDeathStatus::class,
  220u to TurnNumber::class,
  1220u to ChapterSelect::class,
  1000u to HaveTalkedToCharacter::class,
  1120u to CheckChoiceId::class,
  50u to KillCheck::class,
  1010u to QuestCheck::class,
  1130u to SomethingWithCharacter::class,
  250u to SpareKillResult::class,
  1270u to CharacterChosenForHeron::class,
  110u to MovedSpecificUnitInRectangle::class,
  320u to MovedAnyUnitInRectangle::class,
  390u to CountUnitsSlain::class,
  1240u to InventoryContains::class,
  200u to FirstTimeSelect::class,
  360u to DidBattle::class,
)

sealed class BsiCondition : BsiBlock() {

  interface HasChildren {
    val children: UInt
  }
  data class SpareKillResult(val result: UInt, val whoMaybe: UInt) : BsiCondition()
  data class AndContainer(val unk: UInt, override val children: UInt, val length: UInt) : BsiCondition(), HasChildren
  data class OrContainer(val unk: UInt, override val children: UInt, val length: UInt) : BsiCondition(), HasChildren
  data class Characters(val unk: UInt, val char1Id: UInt, val char2Id: UInt, val unk2: UInt, val flag1: UInt, val flag2: UInt) : BsiCondition() {
    val character1 = FeCharacter[char1Id]
    val character2 = if (char2Id == 0u) null else FeCharacter.values().getOrNull(char2Id.toInt())
    fun contains(character: FeCharacter) = character1 == character || character2 == character
    fun personNot(character: FeCharacter) = when {
      character1 == character -> character2
      character2 == character -> character1
      else -> null
    }
  }

  data class Route(val unk: UInt, val route: UInt, @BooleanOfLength(4) val isRoute: Boolean) : BsiCondition()
  data class BylethGender(val unk: UInt, @BooleanOfLength(4) val isFemale: Boolean) : BsiCondition()
  data class DeathFlag(val unk: UInt, val character: FeCharacter, val deathType: UInt, @BooleanOfLength(4) val isAlive: Boolean) : BsiCondition()
  data class HasOtherScriptRun(val unk: UInt, val scriptIndex: UInt, @BooleanOfLength(4) val didRun: Boolean) : BsiCondition()
  data class Unk3fc(val unk: UInt, val slot: UInt, val value: UInt) : BsiCondition()
  data class Difficulty(val unk: UInt, val difficulty: DifficultyMode, val unk2: UInt) : BsiCondition()
  data class ChapterSelect(val unk: UInt, val chapter: UInt, val unk2: UInt) : BsiCondition()
  data class CheckChoiceId(val unk: UInt, val choiceID: UInt, val answerIndex: UInt, val unk3: UInt) : BsiCondition()
  data class KillCheck(val unk: UInt, val character: FeCharacter, val unk2: UInt, val unk3: UInt) : BsiCondition()
  data class QuestCheck(val unk: UInt, val questID: UInt, val statusMaybe: UInt) : BsiCondition()
  data class TurnNumber(val unk: UInt, val turnNumber: UInt, val unk2: UInt, val unk3: UInt, val unk4: UInt) : BsiCondition()
  data class CheckDeathStatus(val unk: UInt, val bai: BaiSlot, @BooleanOfLength(4) val isDead: Boolean) : BsiCondition()
  data class HaveTalkedToCharacter(val unk: UInt, val bai: BaiSlot, val unk2: UInt) : BsiCondition()
  data class SomethingWithCharacter(val unk: UInt, val character: FeCharacter, val unk2: UInt, val unk3: UInt) : BsiCondition()
  data class CharacterChosenForHeron(val unk: UInt, val character: FeCharacter, @BooleanOfLength(4) val isSelected: Boolean): BsiCondition()
  data class MovedSpecificUnitInRectangle(val unk: UInt, val bai: BaiSlot, val left: UInt, val top: UInt, val right: UInt, val bottom: UInt, val unk2: UInt) : BsiCondition()
  data class MovedAnyUnitInRectangle(val unk0: UInt, val unk1: UInt, val left: UInt, val top: UInt, val right: UInt, val bottom: UInt, val unk2: UInt) : BsiCondition()
  data class CountUnitsSlain(val unk: UInt, @OfLength(4) val team: Team, val amount: UInt, @OfLength(4) val comparison: ComparisonOperator) : BsiCondition()

  // (in this case you add 2000 to item id to call the table for gifts/lost items/quest items, apparently)
  data class InventoryContains(@BooleanOfLength(4) val doesNotHaveItem: Boolean, val itemId: UInt, val unk: UInt) : BsiCondition()
  data class FirstTimeSelect(val unk: UInt, val bai: BaiSlot) : BsiCondition()
  data class DidBattle(val unk: UInt, val bai: BaiSlot) : BsiCondition()

  data class UnknownCondition(val magic: UInt, val argument: List<UInt>) : BsiCondition()
}

enum class ComparisonOperator { LT, LTE, GT, GTE }
enum class DifficultyMode { NORMAL, HARD, MADDENING }
enum class Team { PLAYER, ENEMY, ALLY, ENEMY_YELLOW, UNK4 }

