package bsi.blocks

import bsi.*
import models.BaiSlot
import models.Battalion
import models.FeCharacter
import utils.BooleanOfLength
import utils.OfLength
import utils.OfType
import kotlin.reflect.KClass

sealed class BsiAction : BsiBlock()  {
  companion object {
    operator fun get(id: UInt) = idMap[id]
    val idMap: Map<UInt, KClass<out BsiAction>> = mapOf(
      0u to ShowTextS::class,
      15u to BattleTalk::class,
      20u to PlayBGM::class,
      30u to MusicSomething::class,
      40u to Unknown40::class,
      50u to IncrementVariable::class,
      60u to Unknown60::class,
      70u to SetFlag70::class,
      80u to TargetScriptMaybe::class,
      90u to EndMission::class,

      130u to SpawnUnit130::class,
      140u to Walk::class,
      150u to ReplaceUnitBAI::class,
      160u to RemoveFromMap::class,
      170u to KillAllInTeam::class,

      220u to Unknown220::class,
      240u to GrantItem::class,
      250u to Unknown250::class,
      270u to SetupTerrainEffects::class,

      320u to Unknown320::class,
      330u to MoveCameraToBai::class,
      340u to MoveCameraToCoords::class,
      370u to MoveToUnitAI2::class,
      380u to MoveToRectangleAI::class,

      400u to MakeMapTilesBlue::class,
      430u to PlayMovie::class,
      440u to SetMovementFlag::class,
      450u to Unknown450::class,
      460u to ApplyStatusToUnit::class,
      470u to ApplyStatusToUnitAOE::class,

      530u to Unknown530::class,
      540u to WriteSomethingToSaveMaybe2::class,
      550u to Unknown550::class,
      560u to Unknown560::class,
      570u to OpenDoor::class,
      580u to MoveToRectangleAI580::class,
      590u to SetupWorkTilePair::class,

      600u to SpawnUnit::class,
      610u to KeepOnMapWhenSlain::class,
      620u to SpawnSpooky::class,
      630u to ChangeRotation::class,
      640u to FadeToBlack::class,
      650u to ShortPause::class,
      660u to PersuadeKill::class,
      670u to Unknown670::class,
      680u to SpareKill::class,
      690u to MoveToUnitAI::class,

      700u to GrantBattalion::class,
      710u to UpdateMapResultConditions::class,
      720u to GrantItemQuantity::class,
      730u to RemoveInventory::class,
      740u to SetDeathString::class,
      750u to Unk750::class,
      760u to Unk760::class,
      770u to Unk770::class,
      780u to Unk780::class,
      790u to UpdateMapVictoryAndDefeat::class,

      800u to UpdateUnitStatus::class,
      810u to BlackBeastSomething::class,
      820u to MoveToRectangleAI820::class,
      830u to GrantDroppableItem::class,
      840u to ChaliceBlast::class,
      850u to MapShake::class,
      860u to FadeToWhite::class,

      2010u to MonasteryTalk::class,
      2030u to SpawnCharacterMonastery::class,
      2040u to RemoveFromMonastery::class,
      2050u to Unknown2050::class,
      2060u to Unknown2060::class,
      2070u to SetEventFlag::class,

      2100u to SetQuestStatus::class,
      2120u to ShowTutorial::class,
      2180u to MoveBaiMonastery::class,
      2190u to SupportPoints::class,

      2200u to RemoveItemMonasteryGift::class,
      2210u to SetOrderThisCharacterAppearsInScript::class,
      2260u to GrantItemMonasteryGift::class,
      2280u to Unknown2280::class,

      2300u to CreateTalkingGroup::class,
      2340u to Unknown2340::class,
      2360u to FadeToBlackMonastery::class,
      2370u to BylethChoiceDialog::class,

      2420u to SetDoorState::class,
      2430u to ShowVMesMonastery::class,
      2440u to ShowChoiceDialog::class,
      2450u to Unknown2450::class,
      2460u to Unknown2460::class,
      2470u to SkipToEndOfMonth::class,
      2480u to RecruitTalk::class,

      2500u to SetupAuxBattleMapAsQuestMap::class,
      2510u to Unknown2510::class,
      2520u to GrantItemMonastery::class,
      2530u to RemoveItemMonastery::class,
      2540u to Unknown2540::class,
      2550u to Unknown2550::class,
      2560u to Unknown2560::class,
      2570u to Unknown2570::class,
      2580u to Unknown2580::class,
      2590u to Unknown2590::class,

      2600u to Unknown2600::class,
      2610u to Unknown2610::class,
      2640u to ChooseForHeronCup::class,
      2650u to SetMonasteryTodoMessage::class,
      2660u to RemoveMonasteryTodoMessage::class,
      2690u to Unknown2690::class,

      2700u to SetParalogueAvailability::class,
      2710u to Unknown2710::class,
      2720u to Unknown2720::class,
      2730u to Unknown2730::class,
      2740u to SetNormalQuestIndicator::class,
      2750u to HideCharacterOnMinimap::class,
      2760u to Unknown2760::class,
      2770u to MonasteryLocationAvailability::class,
      2780u to Unknown2780::class,
      2790u to Unknown2790::class,

      2800u to SetFinishExploringActive::class,
      2810u to ShowEnding::class,
      2830u to ShowEnding1411::class,
      2840u to SetQuestIndicatorOnCharacter::class,
      2860u to PlaceCharacterInAbyss::class,
    )
    val actionUnknownBlockLengths = mapOf(
      2870u to 3,
      10000u to 9
    )
  }

  data class UnknownAction(val magic: UInt, val argument: List<UInt>) : BsiAction()

  data class SetDeathString(val who: BaiSlot, val textString: UInt) : BsiAction()

  data class PersuadeKill(val target: BaiSlot, val textOnPersuade: UInt, val textOnKill: UInt) : BsiAction()
  data class SpareKill(val textOnKill: UInt, val textOnSpare: UInt) : BsiAction()
  data class RemoveFromMap(val target: BaiSlot, val unk1: UInt, val unk2: UInt, val unk3: UInt, val unk4: UInt) : BsiAction()
  data class WriteSomethingToSaveMaybe2(val target: BaiSlot, val unk1: UInt, val unk2: UInt) : BsiAction()
  data class SupportPoints(val unk: UInt, val character: FeCharacter, val upDown: UInt, val unk3: UInt) : BsiAction()
  data class SetQuestStatus(val questId: UInt, val status: UInt) : BsiAction()
  data class SetQuestIndicatorOnCharacter(val bai: BaiSlot, @BooleanOfLength(4) val hasIndicator: Boolean) : BsiAction()
  data class IncrementVariable(val variableId: UInt, val amount: UInt) : BsiAction()

  data class ShowTextS(val id: UInt) : BsiAction()
  data class ShowEnding(val id: UInt) : BsiAction()
  data class ShowEnding1411(val unk: UInt, val unk2: UInt) : BsiAction()
  data class SpawnUnit(val bai: BaiSlot, val unk2: UInt, val unk3: UInt, val unk4: UInt) : BsiAction()
  data class SpawnUnit130(val bai: BaiSlot, val unk2: UInt, val unk3: UInt, val unk4: UInt, val unk5: UInt, val unk6: UInt, val unk7: UInt) : BsiAction()
  data class Unk750(val target: BaiSlot) : BsiAction()
  data class ShowTutorial(val tutorialID: UInt) : BsiAction()
  data class BattleTalk(val entryID: UInt, val unk2: UInt, val unk3: UInt, val unk4: UInt, val unk5: UInt) : BsiAction()
  data class BylethChoiceDialog(val bai: BaiSlot, val triggerBlock: UInt, val choiceID: UInt, val questionIndex1: UInt, val questionIndex2: UInt, val questionIndex3: UInt, val answer1Block: UInt, val answer2Block: UInt, val answer3Block: UInt, val answersCompleteBlock: UInt) : BsiAction()
  data class SetOrderThisCharacterAppearsInScript(val bai: BaiSlot, val conversationId: UInt) : BsiAction()
  data class MonasteryTalk(val bai: BaiSlot, val vmesBlockID: UInt) : BsiAction()
  data class RecruitTalk(val chararacterID: UInt, val preTextBlockID: UInt, val answeredYes: UInt, val answeredNo: UInt, val recruitFail: UInt) : BsiAction()
  data class Walk(val target: BaiSlot, val coordinates: Coordinates, val teleport: UInt, val unk2: UInt, val unk3: UInt, val unk4: UInt, val unk5: UInt, val unk6: UInt, val unk7: UInt) : BsiAction()
  data class ReplaceUnitBAI(val into: BaiSlot, val who: BaiSlot, val unk0: UInt, val unk1: UInt, val unk2: UInt, val unk3: UInt, val unk4: UInt, val unk5: UInt, val unk6: UInt) : BsiAction()
  data class MoveCameraToCoords(val coordinates: Coordinates, val unk0: UInt, val unk1: UInt, val unk2: UInt) : BsiAction()
  data class MakeMapTilesBlue(val rect: Rectangle, val unk0: UInt, val unk1: UInt, val unk2: UInt) : BsiAction()
  data class SpawnSpooky(val bai: BaiSlot, val coordinates: Coordinates, val unk: UInt, val unk1: UInt) : BsiAction()
  data class GrantBattalion(@OfLength(4) val battalion: Battalion) : BsiAction()
  data class SetMovementFlag(val bai: BaiSlot, val movementValue: UInt) : BsiAction()
  data class SetEventFlag(val id: UInt, val value: UInt) : BsiAction()
  // (it looks like it calls from weapon list by default, and you add 600 to the number to make it call from the Equipment list, or add 1000 to call from the Items list)
  data class GrantItem(val who: BaiSlot, val item: ItemSlot) : BsiAction()
  data class EndMission(@BooleanOfLength(4) val failure: Boolean) : BsiAction()
  data class PlayMovie(val movieID: UInt, val unk: UInt) : BsiAction()

  data class SetFlag70(val slot: UInt, @BooleanOfLength(4) val value:  Boolean) : BsiAction()
  data class SetupAuxBattleMapAsQuestMap(val battleID: UInt, val unk: UInt) : BsiAction()
  data class ApplyStatusToUnit(val target: BaiSlot, val effectType: UInt, @BooleanOfLength(4) val apply: Boolean) : BsiAction()
  data class ApplyStatusToUnitAOE(val rect: Rectangle, val unk: UInt, @OfLength(4) val team: Team, val effectType: UInt, @BooleanOfLength(4) val apply: Boolean) : BsiAction()
  data class MoveCameraToBai(val target: BaiSlot, val unk: UInt, val unk2: UInt, val unk3: UInt) : BsiAction()
  data class SpawnCharacterMonastery(val target: BaiSlot) : BsiAction()
  data class SetNormalQuestIndicator(val target: BaiSlot, @BooleanOfLength(4) val unk: Boolean) : BsiAction()
  data class GrantItemMonastery(val item: UInt, val quantityMaybe: UInt, @BooleanOfLength(4) val unk: Boolean) : BsiAction()
  data class RemoveItemMonastery(val item: UInt, val quantityMaybe: UInt, @BooleanOfLength(4) val unk: Boolean) : BsiAction()
  data class GrantItemMonasteryGift(val item: UInt, val quantityMaybe: UInt, @BooleanOfLength(4) val unk: Boolean) : BsiAction()
  data class RemoveItemMonasteryGift(val item: UInt, val quantityMaybe: UInt, @BooleanOfLength(4) val unk: Boolean) : BsiAction()
  data class PlayBGM(val id: UInt, @BooleanOfLength(4) val unk: UInt) : BsiAction()
  data class MusicSomething(val unk: UInt) : BsiAction()
  data class SetupTerrainEffects(val unk: UInt) : BsiAction()

  /** Related to [BsiCondition.CheckDoorOpened] */
  data class OpenDoor(val id: UInt, val unk: UInt) : BsiAction()
  data class SetupWorkTilePair(val tile1: Coordinates, val tile2: Coordinates) : BsiAction()
  data class SetMonasteryTodoMessage(val unk: UInt, val messageTextSId: UInt) : BsiAction()
  data class RemoveMonasteryTodoMessage(val unk: UInt, val messageTextSId: UInt) : BsiAction()
  data class MoveToUnitAI2(val target: BaiSlot, val unk1: UInt, val unk2: UInt, val unk3: UInt, val unk4: UInt, val unk5: UInt, val unk6: UInt) : BsiAction()
  data class MoveToRectangleAI(val target: BaiSlot, val rect: Rectangle, val unk: UInt, val unk2: UInt) : BsiAction()
  data class RemoveInventory(val bai: BaiSlot) : BsiAction()
  data class KillAllInTeam(val rect: Rectangle, @OfLength(4) val team: Team, val unk2: UInt) : BsiAction()
  object ShortPause : BsiAction() {
    override fun toString() = "ShortPause"
  }
  data class MoveToUnitAI(val target: BaiSlot, @OfType(BaiSlot::class) @OfLength(5) val to: List<BaiSlot>, val unk5: UInt) : BsiAction()
  data class Unknown320(val bai: BaiSlot) : BsiAction()
  data class KeepOnMapWhenSlain(val bai: BaiSlot, val unk: UInt, @BooleanOfLength(4) val negate: Boolean) : BsiAction()
  data class UpdateMapResultConditions(val conditionType: MapResultConditionsType, val stringValue: UInt) : BsiAction()
  data class GrantItemQuantity(val item: ItemSlot, val quantity: UInt) : BsiAction()
  data class FadeToBlack(@BooleanOfLength(4) val black: Boolean) : BsiAction()
  data class MoveBaiMonastery(val bai: BaiSlot, val coords: Coordinates, val unk: UInt) : BsiAction()
  data class CreateTalkingGroup(@OfType(BaiSlot::class) @OfLength(3) val bais: List<BaiSlot>, @BooleanOfLength(4) val unk: Boolean) : BsiAction()
  data class FadeToBlackMonastery(@BooleanOfLength(4) val black: Boolean) : BsiAction()
  object SkipToEndOfMonth : BsiAction() {
    override fun toString() = "SkipToEndOfMonth"
  }

  data class PlaceCharacterInAbyss(val bai: BaiSlot, val x: WeirdCoordinate, val y: WeirdCoordinate, val unk: UInt) : BsiAction()
  data class RemoveFromMonastery(val bai: BaiSlot) : BsiAction()
  data class ShowChoiceDialog(val choiceID: UInt) : BsiAction()
  data class FadeToWhite(@BooleanOfLength(4) val enable: Boolean) : BsiAction()
  data class MapShake(val coords: Coordinates, val unk: UInt) : BsiAction()
  /** it only actually works if fifth param is 1? (it gets run with fifth params of 0, 2, and 3 on the whole map, but run with fifth param of 1 on a set of three rectangles that covers everywhere except where Aelfric is standing) **/
  data class ChaliceBlast(val rect: Rectangle, val unk: UInt, val damage: UInt) : BsiAction()
  object UpdateUnitStatus : BsiAction() {
    override fun toString() = "UpdateUnitStatus"
  }

  /** only used in http://fedatamine.com/en-us/battles/26/stand-strong-at-shambhala */
  data class Unknown220(val bai: BaiSlot, val unk: UInt) : BsiAction()
  /** only used in https://fedatamine.com/en-us/battles/47/the-face-beneath#event-script-33 */
  data class Unknown250(val bai: BaiSlot, val unk: UInt) : BsiAction()

  data class Unknown40(val unk: UInt, val unk2: UInt) : BsiAction()
  data class Unknown60(val unk: UInt, val unk2: UInt) : BsiAction()
  data class TargetScriptMaybe(val scriptID: UInt) : BsiAction()
  /** used in various battles, generally before spawning/moving/removing a unit; purpose unknown */
  data class Unknown450(val bai: BaiSlot, val unk: UInt, val unk2: UInt, val unk3: UInt) : BsiAction()
  data class Unknown530(val bai: BaiSlot, val unk: UInt) : BsiAction()
  /** only used in https://fedatamine.com/en-us/battles/4/assault-at-the-rite-of-rebirth#event-script-70 */
  data class Unknown550(val unk: UInt) : BsiAction()
  /** unk = 0, 1, 2, 3 */
  data class Unknown560(val unk: UInt) : BsiAction()
  data class ChangeRotation(val bai: BaiSlot, val rotation: UInt) : BsiAction()
  data class UpdateMapVictoryAndDefeat(val victory: UInt, val defeat: UInt) : BsiAction()
  data class GrantDroppableItem(val bai: BaiSlot, val item: ItemSlot) : BsiAction()
  /** Only used on https://fedatamine.com/en-us/battles/5/the-gautier-inheritance */
  data class Unknown670(val unk: UInt, val unk2: UInt) : BsiAction()
  /** might be related to BGM fadeouts? (only ever used for before/after the movie in The Magdred Ambush, lords talking to Tomas in The Remire Calamity, and upon defeating Seteth in Protecting Garreg Mach) */
  data class Unk760(val unk: UInt, val unk2: UInt) : BsiAction()
  /** only ever used in some dummied-out scripts in Rivalry of the Houses */
  data class Unk770(val unk: UInt) : BsiAction()
  /** only ever used in a script activated upon defeating Rhea in the CF version of The Battle of Garreg Mach */
  data class Unk780(val unk: UInt) : BsiAction()
  data class SetFinishExploringActive(@BooleanOfLength(4) val active: Boolean) : BsiAction()
  data class HideCharacterOnMinimap(val character: FeCharacter, @BooleanOfLength(4) val hidden: Boolean) : BsiAction()
  data class SetParalogueAvailability(val battleID: UInt, @BooleanOfLength(4) val active: Boolean) : BsiAction()
  /** seems to just be a copy of [MoveToRectangleAI], though possibly somehow related to chest-opening behavior since it's only ever used for the Crest Stone-stealing soldiers in Conflict in the Holy Tomb and the enemy thieves in Stand Strong at Shambhala/Assault on Enbarr */
  data class MoveToRectangleAI580(val target: BaiSlot, val rect: Rectangle, val unk: UInt, val unk2: UInt) : BsiAction()
  /** seems to be another copy of [MoveToRectangleAI]?? why */
  data class MoveToRectangleAI820(val target: BaiSlot, val rect: Rectangle, val unk: UInt, val unk2: UInt) : BsiAction()
  /** only ever used in scripts related to the appearance of the Black Beast in The Gautier Inheritance and the Forlorn Beast in Black Market Scheme; purpose unknown */
  data class BlackBeastSomething(val unk: UInt, val unk2: UInt, val bai: BaiSlot) : BsiAction()

  /** only ever used in some scripts activated after choosing "Return to the audience chamber" during the first Chapter 1 exploration and a dummied-out script activated after talking to Hanneman during that same exploration; purpose unknown */
  data class Unknown2050(val unk: UInt) : BsiAction()
  /** only ever used in a script at the start of the first Chapter 1 exploration; purpose unknown */
  data class Unknown2060(val unk: UInt) : BsiAction()
  /** only ever used in scripts at the start of various explorations; purpose unknown */
  data class Unknown2280(val unk: UInt) : BsiAction()
  data class ChooseForHeronCup(val character: FeCharacter, @BooleanOfLength(4) val choose: Boolean) : BsiAction()
  data class ShowVMesMonastery(val vmesID: UInt) : BsiAction()
  /** notable IDs: 27 is Bernadetta's room, 40 is Edelgard's room (the placement of these two relative to each other can be used to infer that the IDs of the student dorm doors are ordered by their map position from top to bottom and from 1F to 2F, so they range from Dedue's at 20 to Ingrid's at 43), 66 is the infirmary, 70 is blocked off until timeskip, 72 is Rhea's room, 73 is blocked off until chapter 10 */
  data class SetDoorState(val doorID: UInt, @BooleanOfLength(4) val closed: Boolean) : BsiAction()
  /** only ever used in a script activated after choosing "Return to the audience chamber" during the first Chapter 1 exploration; purpose unknown */
  data class Unknown2450(val unk: UInt) : BsiAction()
  /** only ever used in scripts at the start of Chapter 1/CF 12/Cindered Shadows explorations; purpose unknown */
  data class Unknown2460(val unk: UInt) : BsiAction()
  /** only ever used in scripts at the start of various explorations and scripts activated by certain unused event flags in null block 52; purpose unknown */
  data class Unknown2510(val unk: UInt, val unk2: UInt) : BsiAction()
  /** something related to characters that have changed dialogue during a quest? (this is only ever used for dialogue scripts related to the finding-Jeralt quest, the fishing tournament, and picking a dancer) */
  data class Unknown2540(val bai: BaiSlot) : BsiAction()
  data class Unknown2550(val unk: UInt, val unk2: UInt) : BsiAction()
  /** sets flags checked by [BsiCondition.Unknown1160], maybe? */
  data class Unknown2560(val unk: UInt, val unk2: UInt) : BsiAction()
  /** only ever used in scripts at the start of Chapter 5/Chapter 10 explorations; purpose unknown */
  data class Unknown2570(val unk1: UInt, val unk2: UInt, val unk3: UInt) : BsiAction()
  /** only ever used in a script activated after clearing a No Stone Unturned quest in Chapter 6 exploration; seems to somehow activate the choice at the entrance to Jeritza's room for triggering the story battle early? (the second param of it corresponds to that choiceID) */
  data class Unknown2580(val unk: UInt, val unk2: UInt) : BsiAction()
  /** only ever used in scripts related to Edelgard's monastery status in Throne of Knowledge; purpose unknown */
  data class Unknown2590(val unk: UInt, val unk2: UInt) : BsiAction()
  /** only ever used during monastery fade-to-black segments; purpose unknown */
  object Unknown2600 : BsiAction() {
    override fun toString() = "Unknown2600"
  }
  /** only ever used in scripts that activate it after finishing the initial quest in the first Chapter 1 exploration or after talking to Hanneman in Chapter 1/2 explorations */
  data class Unknown2610(
    val unk1: UInt,
    val unk2: UInt,
    val unk3: UInt,
    val unk4: UInt,
    val unk5: UInt,
    val unk6: UInt,
    val unk7: UInt,
    val unk8: UInt,
    val unk9: UInt,
  ) : BsiAction()

  /** only ever used in a script that activates it at the same time as the "Look for Jeralt's belongings in his quarters" message in Chapter 10 exploration
   * in https://fedatamine.com/en-us/monastery/10#event-base-25-0 */
  data class Unknown2690(val unk: UInt, val unk2: UInt) : BsiAction()
  data class Unknown2710(val unk: UInt, val unk2: UInt) : BsiAction()
  /** something related to characters that can be given quest items? first param is a BAI value, second param is 1 to activate or 0 to deactivate (this is only ever used for Flayn during the fishing tournament and trading chain targets during that quest) */
  data class Unknown2720(val bai: BaiSlot, @BooleanOfLength(4) val activate: Boolean) : BsiAction()
  /** sets flags checked by [BsiCondition.Unknown1160], maybe? */
  data class Unknown2730(val slot: UInt, val value: UInt) : BsiAction()
  /** only ever used in a dummied-out scripts in the second Chapter 1 exploration; purpose unknown */
  data class Unknown2340(
    val unk1: UInt,
    val unk2: UInt,
    val unk3: UInt,
    val unk4: UInt,
    val unk5: UInt,
    val unk6: UInt,
    val unk7: UInt,
    val unk8: UInt,
    val unk9: UInt,
    val unk10: UInt,
  ) : BsiAction()
  /** only ever used in a pair of scripts in null explore block 52 that sets it to 1 during chapter 1 and then to 0 during chapter 2 */
  data class Unknown2760(val unk: UInt) : BsiAction()
  /** Same value as in [BsiCondition.CheckPlayerInMonasteryArea] */
  data class MonasteryLocationAvailability(val area: UInt, @BooleanOfLength(4) val available: Boolean) : BsiAction()
  /** only ever used in a set of scripts in null explore block 52 for setting event flags if the player is in certain locations (most of which are placeholder slots) */
  data class Unknown2780(val unk: UInt) : BsiAction()
  /** only ever used in scripts at the start of CF 16/AM 16/VW 17 exploration; purpose unknown */
  data class Unknown2790(val unk: UInt) : BsiAction()
}
