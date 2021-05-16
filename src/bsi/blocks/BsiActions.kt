package bsi.blocks

import bsi.Coordinates
import bsi.ItemSlot
import bsi.Rectangle
import bsi.Team
import models.BaiSlot
import models.Battalion
import models.FeCharacter
import utils.BooleanOfLength
import utils.OfLength
import kotlin.reflect.KClass

sealed class BsiAction : BsiBlock()  {
  companion object {
    operator fun get(id: UInt) = idMap[id]
    val idMap: Map<UInt, KClass<out BsiAction>> = mapOf(
      0u to ShowTextS::class,
      15u to BattleTalk::class,
      20u to PlayBGM::class,
      30u to MusicSomething::class,
      50u to IncrementVariable::class,
      70u to SetFlag70::class,
      90u to EndMission::class,
      130u to SpawnUnit130::class,
      140u to Walk::class,
      150u to ReplaceUnitBAI::class,
      160u to RemoveFromMap::class,
      170u to Unknown170::class,
      240u to GrantItem::class,
      270u to SetupTerrainEffects::class,
      330u to MoveCameraToBai::class,
      320u to Unknown320::class,
      340u to MoveCameraToCoords::class,
      370u to Unknown370::class,
      380u to MoveToRectangleAI::class,
      400u to MakeMapTilesBlue::class,
      430u to PlayMovie::class,
      440u to SetMovementFlag::class,
      460u to ApplyStatusToUnit::class,
      470u to ApplyStatusToUnitAOE::class,

      540u to WriteSomethingToSaveMaybe2::class,
      570u to OpenDoor::class,
      590u to SetupWorkTilePair::class,

      600u to SpawnUnit::class,
      610u to UnknownAction610::class,
      620u to SpawnSpooky::class,
      650u to Unknown650::class,
      660u to PersuadeKill::class,
      680u to SpareKill::class,
      690u to Unknown690::class,
      700u to GrantBattalion::class,
      730u to Unknown730::class,
      740u to SetDeathString::class,
      750u to Unk750::class,

      2010u to MonasteryTalk::class,
      2030u to QuestSomething::class,
      2070u to SetEventFlag::class,

      2100u to SetQuestStatus::class,
      2120u to ShowTutorial::class,
      2190u to SupportPoints::class,

      2200u to RemoveItemMonasteryGift::class,
      2210u to SetOrderThisCharacterAppearsInScript::class,
      2260u to GrantItemMonasteryGift::class,

      2370u to BylethChoiceDialog::class,

      2480u to RecruitTalk::class,

      2500u to SetupAuxBattleMapAsQuestMap::class,
      2520u to GrantItemMonastery::class,
      2530u to RemoveItemMonastery::class,

      2650u to SetMonasteryTodoMessage::class,
      2660u to RemoveMonasteryTodoMessage::class,

      2740u to QuestSomething2::class,

      2810u to ShowEnding::class,
      2830u to ShowEnding1411::class,
      2840u to SetQuestIndicatorOnCharacter::class,
    )
    val actionUnknownBlockLengths = mapOf(
      0u to 1,
      15u to 5,
      20u to 2,
      30u to 1,
      40u to 2,
      50u to 2,
      60u to 2,
      70u to 2,
      80u to 1,
      130u to 3,
      140u to 10,
      150u to 9,
      160u to 5,
      170u to 6,
      220u to 2,
      240u to 2,
      250u to 2,
      270u to 1,
      320u to 1,
      330u to 4,
      340u to 5,
      370u to 7,
      380u to 7,
      400u to 7,
      430u to 2,
      440u to 2,
      450u to 4,
      460u to 3,
      470u to 8,
      530u to 2,
      540u to 3,
      550u to 1,
      560u to 1,
      570u to 2,
      590u to 4,
      580u to 7,
      610u to 3,
      620u to 5,
      630u to 2,
      640u to 1,
      650u to 0,
      660u to 3,
      670u to 2,
      680u to 2,
      690u to 7,
      700u to 1,
      710u to 2,
      720u to 2,
      730u to 1,
      740u to 2,
      760u to 2,
      770u to 1,
      780u to 1,
      790u to 2,
      800u to 0,
      810u to 3,
      820u to 7,
      830u to 2,
      840u to 6,
      850u to 3,
      860u to 1,
      2030u to 1,
      2040u to 1,
      2050u to 1,
      2060u to 1,
      2070u to 2,
      2100u to 2,
      2120u to 1,
      2180u to 4,
      2200u to 3,
      2210u to 2,
      2260u to 3,
      2280u to 1,
      2300u to 4,
      2340u to 10,
      2360u to 1,
      2420u to 2,
      2430u to 1,
      2440u to 1,
      2450u to 1,
      2460u to 1,
      2470u to 0,
      2480u to 5,
      2500u to 2,
      2510u to 2,
      2520u to 3,
      2530u to 3,
      2540u to 1,
      2550u to 2,
      2560u to 2,
      2570u to 3,
      2580u to 2,
      2590u to 2,
      2600u to 0,
      2610u to 9,
      2640u to 2,
      2650u to 2,
      2660u to 2,
      2690u to 2,
      2700u to 2,
      2710u to 2,
      2720u to 2,
      2730u to 2,
      2740u to 2,
      2750u to 2,
      2760u to 1,
      2770u to 2,
      2780u to 1,
      2790u to 1,
      2800u to 1,
      2840u to 2,
      2860u to 4,
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
  data class QuestSomething(val target: BaiSlot) : BsiAction()
  data class QuestSomething2(val target: BaiSlot, @BooleanOfLength(4) val unk: Boolean) : BsiAction()
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
  data class Unknown370(val target: BaiSlot, val unk1: UInt, val unk2: UInt, val unk3: UInt, val unk4: UInt, val unk5: UInt, val unk6: UInt) : BsiAction()
  data class MoveToRectangleAI(val target: BaiSlot, val rect: Rectangle, val unk: UInt, val unk2: UInt) : BsiAction()
  data class Unknown730(val bai: BaiSlot) : BsiAction()
  data class Unknown170(val rect: Rectangle, val unk: UInt, val unk2: UInt) : BsiAction()
  object Unknown650 : BsiAction() {
    override fun toString() = "Unknown650"
  }
  data class Unknown690(val bai1: BaiSlot, val bai2: BaiSlot, val bai3: BaiSlot, val bai4: BaiSlot, val bai5: BaiSlot, val bai6: BaiSlot, val unk5: UInt) : BsiAction()
  data class Unknown320(val bai: BaiSlot) : BsiAction()
  data class UnknownAction610(val bai: BaiSlot, val unk: UInt, val unk2: UInt) : BsiAction()
}
