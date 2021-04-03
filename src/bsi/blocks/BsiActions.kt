package bsi.blocks

import bsi.blocks.BsiAction.*
import bsi.models.BaiSlot
import bsi.models.FeCharacter
import utils.BooleanOfLength

val bsiActionIds = mapOf(
  0x2810u to ShowEnding::class,
  2830u to ShowEnding1411::class,
  600u to SpawnUnit::class,
  130u to SpawnUnit130::class,
  750u to Unk750::class,
  2120u to ShowTutorial::class,
  15u to BattleTalk::class,
  140u to Walk::class,
  90u to EndMission::class,
  2370u to BylethChoiceDialog::class,
  2210u to SetOrderThisCharacterAppearsInScript::class,
  2010u to MonasteryTalk::class,
  2100u to SetQuestStatus::class,
  2190u to SupportPoints::class,
  2840u to SetQuestIndicatorOnCharacter::class,
  2480u to RecruitTalk::class,
  50u to IncrementVariableMaybe::class,
  660u to PersuadeKill::class,
  680u to SpareKill::class,
  160u to WriteSomethingToSaveMaybe::class,
  540u to WriteSomethingToSaveMaybe2::class,
  740u to SetDeathString::class,
  430u to PlayMovie::class,
  0u to ShowTextS::class,
  150u to ReplaceUnitBAI::class,
  340u to MoveCameraToCoords::class,
  400u to MakeMapTilesBlue::class,
  620u to SpawnSpooky::class,
  700u to GrantBattalion::class,
  240u to GrantItem::class,
  2070u to SetEventFlag::class,
  440u to SetMovementFlag::class,
)

sealed class BsiAction : BsiBlock() {
  data class UnknownAction(val magic: UInt, val argument: List<UInt>) : BsiAction()

  data class SetDeathString(val who: BaiSlot, val textString: UInt) : BsiAction()

  data class PersuadeKill(val whoMaybe: UInt, val textOnPersuade: UInt, val textOnKill: UInt) : BsiAction()
  data class SpareKill(val textOnKill: UInt, val textOnSpare: UInt) : BsiAction()
  data class WriteSomethingToSaveMaybe(val whoMaybe: UInt, val unk1: UInt, val unk2: UInt, val unk3: UInt, val unk4: UInt) : BsiAction()
  data class WriteSomethingToSaveMaybe2(val whoMaybe: UInt, val unk1: UInt, val unk2: UInt) : BsiAction()
  data class SupportPoints(val unk: UInt, val character: FeCharacter, val upDown: UInt, val unk3: UInt) : BsiAction()
  data class SetQuestStatus(val questId: UInt, val status: UInt) : BsiAction()
  data class SetQuestIndicatorOnCharacter(val bai: BaiSlot, @BooleanOfLength(4) val hasIndicator: Boolean) : BsiAction()
  data class IncrementVariableMaybe(val variableId: UInt, val amount: UInt) : BsiAction()

  data class ShowTextS(val id: UInt) : BsiAction()
  data class ShowEnding(val id: UInt) : BsiAction()
  data class ShowEnding1411(val unk: UInt, val unk2: UInt) : BsiAction()
  data class SpawnUnit(val bai: BaiSlot, val unk2: UInt, val unk3: UInt, val unk4: UInt) : BsiAction()
  data class SpawnUnit130(val unk: UInt, val unk2: UInt, val unk3: UInt, val unk4: UInt, val unk5: UInt, val unk6: UInt, val unk7: UInt) : BsiAction()
  data class Unk750(val unk: UInt) : BsiAction()
  data class ShowTutorial(val tutorialID: UInt) : BsiAction()
  data class BattleTalk(val entryID: UInt, val unk2: UInt, val unk3: UInt, val unk4: UInt, val unk5: UInt) : BsiAction()
  data class BylethChoiceDialog(val bai: BaiSlot, val triggerBlock: UInt, val choiceID: UInt, val questionIndex1: UInt, val questionIndex2: UInt, val questionIndex3: UInt, val answer1Block: UInt, val answer2Block: UInt, val answer3Block: UInt, val answersCompleteBlock: UInt) : BsiAction()
  data class SetOrderThisCharacterAppearsInScript(val bai: BaiSlot, val conversationId: UInt) : BsiAction()
  data class MonasteryTalk(val bai: BaiSlot, val vmesBlockID: UInt) : BsiAction()
  data class RecruitTalk(val chararacterID: UInt, val preTextBlockID: UInt, val answeredYes: UInt, val answeredNo: UInt, val recruitFail: UInt) : BsiAction()
  data class Walk(val bsiWho: UInt, val x: UInt, val y: UInt, val teleport: UInt, val unk2: UInt, val unk3: UInt, val unk4: UInt, val unk5: UInt, val unk6: UInt, val unk7: UInt) : BsiAction()
  data class ReplaceUnitBAI(val into: BaiSlot, val who: BaiSlot, val unk0: UInt, val unk1: UInt, val unk2: UInt, val unk3: UInt, val unk4: UInt, val unk5: UInt, val unk6: UInt) : BsiAction()
  data class MoveCameraToCoords(val x: UInt, val y: UInt, val unk0: UInt, val unk1: UInt, val unk2: UInt) : BsiAction()
  data class MakeMapTilesBlue(val left: UInt, val top: UInt, val right: UInt, val bottom: UInt, val unk0: UInt, val unk1: UInt, val unk2: UInt) : BsiAction()
  data class SpawnSpooky(val bai: BaiSlot, val x: UInt, val y: UInt, val unk: UInt, val unk1: UInt) : BsiAction()
  data class GrantBattalion(val bettalionId: UInt) : BsiAction()
  data class SetMovementFlag(val bai: BaiSlot, val movementValue: UInt) : BsiAction()
  data class SetEventFlag(val id: UInt, val value: UInt) : BsiAction()
  // (it looks like it calls from weapon list by default, and you add 600 to the number to make it call from the Equipment list, or add 1000 to call from the Items list)
  data class GrantItem(val who: BaiSlot, val item: UInt) : BsiAction()
  data class EndMission(@BooleanOfLength(4) val failure: Boolean) : BsiAction()
  data class PlayMovie(val movieID: UInt, val unk: UInt) : BsiAction()
}
