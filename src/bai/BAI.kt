package bai

import bsi.Team
import models.*
import utils.OfLength
import utils.OfType
import utils.readClass
import utils.times
import java.nio.ByteBuffer

class BaiFile(bytes: ByteBuffer) {
  val header = bytes.readClass(BaiFileHeader::class)
  val entriesInRoute = (bytes.limit() - 0x18) / 0x2c / 3

  data class CharacterBlock(
    val characterIDPlus1: UShort,
    val unk: UShort,
    val classIDPlusOne: UShort,
    @OfType(ItemSlotU2::class) @OfLength(6) val inventory: List<ItemSlotU2>,
    val itemsFlags: UShort,
    @OfType(UByte::class) @OfLength(5) val abilitiesPlusOne: List<UByte>,
    val xCoord: UByte,
    val yCoord: UByte,
    val rotation: UByte,
    val level: UByte,
    @OfLength(1)
    val team: Team,
    val scriptSpawn: ScriptSpawn,
    val movementFlags: UByte,
    val unk1: UByte,
    val commanderStatus: UByte,
    val battalionPlusOne: UByte,
    val battalionLevel: UByte,
    val spellIDPlusOne: UByte,
    val spellID2PlusOne: UByte,
    val combatArt: UByte,
    val gender: UByte,
    val padding: UInt
  ) {
    val character = (characterIDPlus1.toInt() - 1).let { if (it == -1) null else FeCharacter.values().getOrNull(it) }
    val battalion = (battalionPlusOne.toInt() - 1).let { Battalion.values().getOrNull(it) }
    val spell = SpellID.values().getOrNull(spellIDPlusOne.toInt() - 1)
    val spell2 = SpellID.values().getOrNull(spellID2PlusOne.toInt() - 1)
    val feClass = FeClass.values().getOrNull(classIDPlusOne.toInt() - 1)
    val abilities = abilitiesPlusOne.mapNotNull { AbilityID.values().getOrNull(it.toInt() - 1) }
  }

  val blocks = 3.times { id ->
    bytes.position(header.offsets[id] + 24)
    (0 until 100).mapNotNull { _ ->
      runCatching { bytes.readClass(CharacterBlock::class) }.getOrNull()
    }
  }

  operator fun get(slot: BaiSlot) = blocks.getOrNull(slot.version.toInt())?.getOrNull(slot.slot.toInt())
}

data class BaiFileHeader(
  val header: UInt,
  val unk1: UInt,
  val unk2: UInt,
  @OfType(Int::class)
  @OfLength(3)
  val offsets: List<Int>
)
