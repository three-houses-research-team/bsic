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
      30u to IsStartOfMap::class, /** trigger: 10000/9999 */
      50u to KillCheck::class,
      60u to IsCharacterIDSlain::class,
      70u to HPPercentageCharacterID::class,
      80u to CheckDeathStatus::class,
      90u to HPPercentage::class,

      100u to MovedCharacterOnTile::class,
      110u to MovedSpecificUnitInRectangle::class,
      120u to CheckTeamSurvivors::class,
      130u to InteractedWithTerrain::class,
      140u to Difficulty::class,
      170u to IsStartOfBattle::class, /** trigger: 12000/11999 */
      180u to IsStartOfPlayerPhase::class, /** trigger: 13000/12999 */
      190u to IsStartOfEnemyPhase::class, /** trigger: 14000/13999 */

      200u to FirstTimeSelect::class,
      220u to TurnNumber::class,
      230u to IsAdjacentTo::class,
      240u to UnitMovedIntoRangeOf::class,
      250u to IsRecruited::class,
      280u to TeamMovedIntoRangeOf::class,

      300u to DidBattleCharacterIDs2::class,
      310u to MovedTeamIntoSpace::class,
      320u to MovedAnyUnitInRectangle::class,
      330u to DidBattleCharacterIDs::class,
      340u to Route::class,
      350u to DidBAIsBattle::class,
      360u to DidBattle::class,
      370u to BylethGender::class,
      380u to CheckDoorOpened::class,
      390u to CountUnitsSlain::class,

      400u to StartOfTeamPhase::class,
      410u to StartOfTeamPhase2::class,
      420u to MovedCharacterIntoRectangle::class,
      430u to OnlyUsedInBattleOfTheEagleAndLion::class,
      440u to StartOfBattleMaybe::class,
      450u to TeamDidBattle::class,
      470u to TeamDidBattleAndDefeat::class,
      480u to DidCompleteMap::class,
      490u to InventoryCheck::class,

      500u to RivalryoftheHousesUnused::class,
      510u to CheckLeverActivated::class,
      520u to IsGameMode::class,
      530u to WasRecruitedPreTimeskip::class,
      540u to DidUnitsTurnEnd::class,
      550u to CheckBattleDialogHappened::class,
      560u to UnkTheUndergroundChamber::class,
      570u to CharacterRecruitedButDead::class,
      580u to UmbralSurgeUsed::class,
      590u to NonAshenWolvesOnTile::class,

      1000u to HaveTalkedToCharacter::class,
      1010u to QuestCheck::class,
      1020u to CheckEventFlag::class,
      1030u to CheckClearedAuxMapAsQuestMap::class,
      1060u to Characters::class,
      1080u to CheckGiftedQuestItem::class,

      1110u to Unknown1110::class,
      1120u to CheckChoiceId::class,
      1130u to RecruitmentCheckMonastery::class,
      1150u to InteractedWithAreaMonasteryMaybe::class,
      1160u to Unknown1160::class,
      1170u to CheckPlayerInMonasteryArea::class,
      1180u to CalendarDay::class,
      1190u to DeathFlag::class,

      1210u to Unused1210::class,
      1220u to ChapterSelect::class,
      1230u to HaveTalkedToCharacterID::class,
      1240u to InventoryContains::class,
      1250u to Unused1250::class,
      1270u to CharacterChosenForHeron::class,
      1280u to Unknown1280::class,

      1310u to UnknownEdelgard::class,
      1320u to CheckTutorialSeen::class,
      1330u to HasDLC::class,
      1340u to CinderedShadowsCompletion::class,
      1350u to IsInSunlight::class,
      1360u to InfluencerPerks::class,

      99998u to AndContainer::class,
      99999u to OrContainer::class,
    )
    val conditionUnknownBlockLengths = mapOf(
      15u to 5,
      40u to 4,
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

  data class Route(@BooleanOfLength(4) val negateAgain: Boolean, val route: FeRoute, @BooleanOfLength(4) val isRoute: Boolean) : BsiCondition()
  data class BylethGender(val unk: UInt, @BooleanOfLength(4) val isFemale: Boolean) : BsiCondition()
  data class DeathFlag(val unk: UInt, val character: FeCharacter, val deathType: UInt, @BooleanOfLength(4) val isAlive: Boolean) : BsiCondition()
  data class HasOtherScriptRun(val unk: UInt, val scriptIndex: UInt, @BooleanOfLength(4) val didRun: Boolean) : BsiCondition()
  data class CheckEventFlag(val unk: UInt, val slot: UInt, val value: UInt) : BsiCondition()
  data class Difficulty(val unk: UInt, val difficulty: DifficultyMode, val unk2: ComparisonOperator2) : BsiCondition()
  data class ChapterSelect(val unk: UInt, val chapter: UInt, val comparison: ComparisonOperator2) : BsiCondition()
  data class CheckChoiceId(val unk: UInt, val choiceID: UInt, val answerIndex: UInt, val unk3: UInt) : BsiCondition()
  data class KillCheck(val unk: UInt, val character: FeCharacter, val unk2: UInt, val unk3: UInt) : BsiCondition()
  data class QuestCheck(val unk: UInt, val questID: UInt, val statusMaybe: UInt) : BsiCondition()
  data class TurnNumber(val unk: UInt, val turnNumber: UInt, val unk2: UInt, val unk3: UInt, val unk4: UInt) : BsiCondition()
  data class CheckDeathStatus(val unk: UInt, val bai: BaiSlot, @BooleanOfLength(4) val isDead: Boolean) : BsiCondition()
  data class HaveTalkedToCharacter(val unk: UInt, val bai: BaiSlot, val unk2: UInt) : BsiCondition()
  data class RecruitmentCheckMonastery(val unk: UInt, val character: FeCharacter, val unk2: UInt, @BooleanOfLength(4) val recruited: Boolean) : BsiCondition()
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
  data class CharacterRecruitedButDead(@BooleanOfLength(4) val negate: Boolean, val character: FeCharacter) : BsiCondition()
  data class IsAdjacentTo(val unk: UInt, val bai1: BaiSlot, val bai2: BaiSlot) : BsiCondition()
  data class WasRecruitedPreTimeskip(@BooleanOfLength(4) val negate: Boolean, val character: FeCharacter) : BsiCondition()
  data class DidBAIsBattle(@BooleanOfLength(4) val negate: Boolean, val bai: BaiSlot, val bai2: BaiSlot, /** always 300 */ val bai3: BaiSlot, val unk: UInt) : BsiCondition()
  data class CheckTeamSurvivors(@BooleanOfLength(4) val negate: Boolean, @OfLength(4) val team: Team, val unk: UInt, val number: UInt, val comparison: ComparisonOperator2) : BsiCondition()
  data class InteractedWithTerrain(val unk: UInt, val combined: CoordsInU4, @BooleanOfLength(4) val interacted: Boolean) : BsiCondition()
  data class InteractedWithAreaMonasteryMaybe(val unk: UInt, val unk2: UInt, val rect: Rectangle, val unk3: UInt) : BsiCondition()
  data class DidCompleteMap(@BooleanOfLength(4) val unk: Boolean) : BsiCondition()
  data class IsGameMode(val unk: UInt, val gameMode: GameMode, val comparisonMaybe: ComparisonOperator2) : BsiCondition()
  data class HasDLC(val unk: UInt, val value: DLCValueCheck, @BooleanOfLength(4) val mustHave: Boolean) : BsiCondition()
  data class InfluencerPerks(val unk: UInt, val amountOfUnlocks: UInt, val comparison: ComparisonOperator2) : BsiCondition()
  data class CinderedShadowsCompletion(val unk: UInt, val chapterNumber: UInt, @BooleanOfLength(4) val trueValue: Boolean) : BsiCondition()
  data class IsInSunlight(val unk: UInt, @BooleanOfLength(4) val trueValue: Boolean) : BsiCondition()
  data class CalendarDay(val unk: UInt, val day: UInt, val comparison: ComparisonOperator2) : BsiCondition()
  data class UnitMovedIntoRangeOf(val unk: UInt, val bai1: BaiSlot, val bai2: BaiSlot, val range: UInt, val unk2: UInt) : BsiCondition()
  data class MovedTeamIntoSpace(val unk: UInt, @OfLength(4) val team: Team, val coords: Coordinates) : BsiCondition()
  data class IsStartOfBattle(val unk: UInt) : BsiCondition()
  data class IsStartOfPlayerPhase(val unk: UInt) : BsiCondition()
  data class IsStartOfEnemyPhase(val unk: UInt) : BsiCondition()
  data class IsStartOfMap(val unk: UInt) : BsiCondition()

  data class StartOfTeamPhase(val unk: UInt, @OfLength(4) val team: Team) : BsiCondition()
  data class StartOfTeamPhase2(val unk: UInt, @OfLength(4) val team: Team) : BsiCondition()
  data class MovedCharacterIntoRectangle(val unk: UInt, val character: FeCharacter, val rect: Rectangle, val unk2: UInt) : BsiCondition()
  data class OnlyUsedInBattleOfTheEagleAndLion(val unk: UInt, val unk2: UInt) : BsiCondition()
  data class StartOfBattleMaybe(val unk: UInt) : BsiCondition()
  data class TeamDidBattle(val unk: UInt, @OfLength(4) val team: Team, val unk2: UInt, val unk3: UInt, val unk4: UInt) : BsiCondition()
  data class InventoryCheck(val unk: UInt, val item: ItemSlot, val value: UInt, val comparison: ComparisonOperator2) : BsiCondition()
  data class RivalryoftheHousesUnused(val unk: UInt, val bai: BaiSlot, val unk2: UInt) : BsiCondition()
  /**
   * (i.e., if there's a 540 check, the event will trigger after the circle around the acting unit is greyed out;
   * if there isn't, it'll trigger before—compare e.g., movie upon defeating Kronya in The Sealed Forest Snare to movie upon defeating Kostas in A Skirmish at Dawn)
   */
  data class DidUnitsTurnEnd(val unk: UInt) : BsiCondition()
  /** only in https://fedatamine.com/en-us/battles/6/the-underground-chamber */
  data class UnkTheUndergroundChamber(val unk: UInt) : BsiCondition()
  /** only in https://fedatamine.com/en-us/battles/92/a-beast-in-the-cathedral */
  data class UmbralSurgeUsed(val unk: UInt) : BsiCondition()
  /** only in https://fedatamine.com/en-us/battles/91/the-rite-of-rising */
  data class NonAshenWolvesOnTile(val unk: UInt, val coordinates: Coordinates) : BsiCondition()
  /** baislot 101 = anybody for some reason (not 300 like in other scripts) */
  data class DidBattleCharacterIDs(@BooleanOfLength(4) val negate: Boolean, val character: FeCharacter, val character2: FeCharacter, val bai3MaybeAlwaysEmpty: BaiSlot, @BooleanOfLength(4) val mustBeDefeated: Boolean, val unk: UInt, val unk2: UInt) : BsiCondition()
  data class DidBattleCharacterIDs2(@BooleanOfLength(4) val negate: Boolean, val character: FeCharacter, val character2: FeCharacter, val bai3MaybeAlwaysEmpty: BaiSlot, val unk: UInt) : BsiCondition()
  data class TeamDidBattleAndDefeat(@BooleanOfLength(4) val negate: Boolean, @OfLength(4) val team: Team, val character: FeCharacter, val unk: UInt, val always1: UInt) : BsiCondition()
  /** only ever used in some scripts related to Edelgard's monastery status in Throne of Knowledge and in a dummied-out script in Three Houses; purpose unknown */
  data class Unknown1110(val unk: UInt, val unk2: UInt) : BsiCondition()
  /** seems to check for some sort of flag (in second param) having been set by actions 2560 or 2730? */
  data class Unknown1160(val unk: UInt, val unk2: UInt) : BsiCondition()

  /** id in second param corresponds to msgdata listing starting at 0x000030ea in table 2, so 0 is "Personal Quarters", 34 is "Reception Hall", etc. */
  data class CheckPlayerInMonasteryArea(val unk: UInt, val areaID: UInt) : BsiCondition()

  data class Unused1210(val unk: UInt, val unk2: UInt, val unk3: UInt) : BsiCondition()
  data class Unused1250(val unk: UInt, val unk2: UInt, val unk3: UInt, val unk4: UInt) : BsiCondition()
  data class Unknown1280(val unk: UInt, val unk2: UInt) : BsiCondition()
  /** only use in http://fedatamine.com/en-us/monastery/11#event-base-22-0 */
  data class UnknownEdelgard(val unk: UInt, val unk2: UInt) : BsiCondition()
}
