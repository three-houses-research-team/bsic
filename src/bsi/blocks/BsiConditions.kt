package bsi.blocks

import bsi.*
import models.BaiSlot
import models.FeCharacter
import utils.BooleanOfLength
import utils.OfLength
import kotlin.reflect.KClass

sealed class BsiCondition : BsiBlock() {
  companion object {
    operator fun get(id: UInt) = idMap[id]
    val idMap: Map<UInt, KClass<out BsiCondition>> = mapOf(
      0u to HasOtherScriptRun::class,
      10u to CheckVariableValue::class,
      20u to CheckFlag70::class,
      50u to KillCheck::class,
      60u to IsCharacterIDSlain::class,
      70u to HPPercentageCharacterID::class,
      80u to CheckDeathStatus::class,
      90u to HPPercentage::class,
      100u to MovedCharacterOnTile::class,
      110u to MovedSpecificUnitInRectangle::class,
      120u to CheckTeamSurvivors::class,
      140u to Difficulty::class,
      200u to FirstTimeSelect::class,
      220u to TurnNumber::class,
      230u to Unknown230::class,
      250u to IsRecruited::class,
      280u to TeamMovedIntoRangeOf::class,
      320u to MovedAnyUnitInRectangle::class,
      340u to Route::class,
      350u to DidBAIsBattle::class,
      360u to DidBattle::class,
      370u to BylethGender::class,
      380u to CheckDoorOpened::class,
      390u to CountUnitsSlain::class,
      510u to CheckLeverActivated::class,
      530u to Unknown530::class,
      550u to CheckBattleDialogHappened::class,
      570u to Unknown570::class,
      1000u to HaveTalkedToCharacter::class,
      1010u to QuestCheck::class,
      1020u to CheckEventFlag::class,
      1030u to CheckClearedAuxMapAsQuestMap::class,
      1060u to Characters::class,
      1080u to CheckGiftedQuestItem::class,
      1120u to CheckChoiceId::class,
      1130u to SomethingWithCharacter::class,
      1190u to DeathFlag::class,
      1220u to ChapterSelect::class,
      1230u to HaveTalkedToCharacterID::class,
      1240u to InventoryContains::class,
      1270u to CharacterChosenForHeron::class,
      1320u to CheckTutorialSeen::class,

      99998u to AndContainer::class,
      99999u to OrContainer::class,
    )
    val conditionUnknownBlockLengths = mapOf(
      0u to 3,
      15u to 5,
      10u to 4,
      20u to 3,
      30u to 1,
      40u to 4,
      60u to 3,
      70u to 4,
      90u to 4,
      100u to 4,
      110u to 7,
      120u to 5,
      130u to 3,
      140u to 3,
      170u to 1,
      180u to 1,
      190u to 1,
      200u to 2,
      230u to 3,
      240u to 5,
      250u to 2,
      280u to 5,
      300u to 5,
      310u to 4,
      320u to 7,
      330u to 7,
      350u to 5,
      360u to 2,
      380u to 3,
      390u to 4,
      400u to 2,
      410u to 2,
      420u to 7,
      430u to 2,
      440u to 1,
      450u to 5,
      470u to 5,
      480u to 1,
      490u to 4,
      500u to 3,
      510u to 2,
      520u to 3,
      530u to 2,
      540u to 1,
      550u to 2,
      560u to 1,
      570u to 2,
      580u to 1,
      590u to 3,
      1000u to 3,
      1010u to 3,
      1030u to 3,
      1080u to 3,
      1110u to 2,
      1120u to 4,
      1130u to 4,
      1150u to 7,
      1160u to 2,
      1170u to 2,
      1180u to 3,
      1190u to 4,
      1210u to 3,
      1220u to 3,
      1230u to 2,
      1250u to 4,
      1240u to 3,
      1270u to 3,
      1280u to 2,
      1310u to 2,
      1320u to 2,
      1330u to 3,
      1340u to 3,
      1350u to 2,
      1360u to 3
    )
  }


  interface HasChildren {
    val children: UInt
  }
  data class IsRecruited(@BooleanOfLength(4) val negate: Boolean, val character: FeCharacter) : BsiCondition()
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

  data class Route(val unk: UInt, val route: FeRoute, @BooleanOfLength(4) val isRoute: Boolean) : BsiCondition()
  data class BylethGender(val unk: UInt, @BooleanOfLength(4) val isFemale: Boolean) : BsiCondition()
  data class DeathFlag(val unk: UInt, val character: FeCharacter, val deathType: UInt, @BooleanOfLength(4) val isAlive: Boolean) : BsiCondition()
  data class HasOtherScriptRun(val unk: UInt, val scriptIndex: UInt, @BooleanOfLength(4) val didRun: Boolean) : BsiCondition()
  data class CheckEventFlag(val unk: UInt, val slot: UInt, val value: UInt) : BsiCondition()
  data class Difficulty(val unk: UInt, val difficulty: DifficultyMode, val unk2: UInt) : BsiCondition()
  data class ChapterSelect(val unk: UInt, val chapter: UInt, val comparison: ComparisonOperator2) : BsiCondition()
  data class CheckChoiceId(val unk: UInt, val choiceID: UInt, val answerIndex: UInt, val unk3: UInt) : BsiCondition()
  data class KillCheck(val unk: UInt, val character: FeCharacter, val unk2: UInt, val unk3: UInt) : BsiCondition()
  data class QuestCheck(val unk: UInt, val questID: UInt, val statusMaybe: UInt) : BsiCondition()
  data class TurnNumber(val unk: UInt, val turnNumber: UInt, val unk2: UInt, val unk3: UInt, val unk4: UInt) : BsiCondition()
  data class CheckDeathStatus(val unk: UInt, val bai: BaiSlot, @BooleanOfLength(4) val isDead: Boolean) : BsiCondition()
  data class HaveTalkedToCharacter(val unk: UInt, val bai: BaiSlot, val unk2: UInt) : BsiCondition()
  data class SomethingWithCharacter(val unk: UInt, val character: FeCharacter, val unk2: UInt, val unk3: UInt) : BsiCondition()
  data class CharacterChosenForHeron(val unk: UInt, val character: FeCharacter, @BooleanOfLength(4) val isSelected: Boolean): BsiCondition()
  data class MovedSpecificUnitInRectangle(val unk: UInt, val bai: BaiSlot, val rect: Rectangle, val unk2: UInt) : BsiCondition()
  data class MovedAnyUnitInRectangle(val unk0: UInt, val unk1: UInt, val rect: Rectangle, val unk2: UInt) : BsiCondition()
  data class CountUnitsSlain(val unk: UInt, @OfLength(4) val team: Team, val amount: UInt, @OfLength(4) val comparison: ComparisonOperator) : BsiCondition()

  // (in this case you add 2000 to item id to call the table for gifts/lost items/quest items, apparently)
  data class InventoryContains(@BooleanOfLength(4) val doesNotHaveItem: Boolean, val itemId: UInt, val unk: UInt) : BsiCondition()
  data class FirstTimeSelect(val unk: UInt, val bai: BaiSlot) : BsiCondition()
  data class DidBattle(val unk: UInt, val bai: BaiSlot) : BsiCondition()

  data class CheckFlag70(val unk: UInt, val slot: UInt, @BooleanOfLength(4) val value: Boolean) : BsiCondition()
  data class CheckGiftedQuestItem(val unk: UInt, val questItem: UInt, val character: FeCharacter): BsiCondition()
  data class CheckClearedAuxMapAsQuestMap(val unk: UInt, val battleID: UInt, @BooleanOfLength(4) val value: Boolean) : BsiCondition()
  data class MovedCharacterOnTile(val unk: UInt, val bai: UInt, val coordinates: Coordinates): BsiCondition()
  data class CheckBattleDialogHappened(val unk: UInt, val battleDialogID: UInt) : BsiCondition()
  data class CheckVariableValue(val unk: UInt, val slot: UInt, val value: UInt, val comparison: ComparisonOperator2) : BsiCondition()

  data class UnknownCondition(val magic: UInt, val argument: List<UInt>) : BsiCondition()
  data class CheckTutorialSeen(@BooleanOfLength(4) val didNotSee: Boolean, val tutorialID: UInt) : BsiCondition()
  data class HaveTalkedToCharacterID(@BooleanOfLength(4) val didNot: Boolean, val character: FeCharacter) : BsiCondition()
  data class CheckLeverActivated(@BooleanOfLength(4) val boolMaybe: Boolean, val idMaybe: UInt) : BsiCondition()
  data class CheckDoorOpened(val unk: UInt, val id: UInt, val unk2: UInt) : BsiCondition()
  data class HPPercentage(@BooleanOfLength(4) val negate: Boolean, val bai: BaiSlot, val percentage: Int, val comparison: ComparisonOperator2) : BsiCondition()
  data class TeamMovedIntoRangeOf(val unk: UInt, val target: BaiSlot, @OfLength(4) val team: Team, val range: UInt, val comparison: ComparisonOperator2) : BsiCondition()
  data class IsCharacterIDSlain(val unk: UInt, val character: FeCharacter, @BooleanOfLength(4) val slain: Boolean) : BsiCondition()
  data class HPPercentageCharacterID(val unk: UInt, val character: FeCharacter, val percentage: Int, val comparison: ComparisonOperator2) : BsiCondition()
  data class Unknown570(@BooleanOfLength(4) val boolMaybe: Boolean, val character: FeCharacter) : BsiCondition()
  data class Unknown230(val unk: UInt, val bai1: BaiSlot, val bai2: BaiSlot) : BsiCondition()
  data class Unknown530(val unk: UInt, val character: FeCharacter) : BsiCondition()
  data class DidBAIsBattle(@BooleanOfLength(4) val negate: Boolean, val bai: BaiSlot, val bai2: BaiSlot, /** always 300 */ val bai3: BaiSlot, val unk: UInt) : BsiCondition()
  data class CheckTeamSurvivors(@BooleanOfLength(4) val negate: Boolean, @OfLength(4) val team: Team, val unk: UInt, val number: UInt, val comparison: ComparisonOperator2) : BsiCondition()
}
