package bai

import strings.MapString
import strings.serialize
import utils.byteBufferWriter
import utils.u1
import utils.u2
import utils.u4
import java.io.File
import java.nio.ByteBuffer

object BAIWriter {
  private fun BaiFile.CharacterBlock.serialize(buffer: ByteBuffer) {
    buffer.apply {
      val start = position()
      u2(characterIDPlus1)
      u2(unk)
      u2(classIDPlusOne)
      inventory.forEach { u2(it.raw) }
      u2(itemsFlags)
      abilitiesPlusOne.forEach { u1(it) }
      u1(xCoord)
      u1(yCoord)
      u1(rotation)
      u1(level)
      u1(team.ordinal.toUByte())
      u1(scriptSpawn.ordinal.toUByte())
      u1(movementFlags)
      u1(unk1)
      u1(commanderStatus)
      u1(battalionPlusOne)
      u1(battalionLevel)
      u1(spellIDPlusOne)
      u1(spellID2PlusOne)
      u1(combatArt)
      u1(gender)
      u4(padding)
      assert(position() - start == 0x2c) { "didn't write exactly 0x2c" }
    }
  }
  fun write(destination: File, blocks: List<BaiFile.CharacterBlock>) = destination.byteBufferWriter {
    u4(0x00002717u)
    u4(0u) // ???
    u4(0u) // ???
    u4(0u) // offset 1
    u4(0x00001130u) // offset 2
    u4(0x00002260u) // offset 3
    for (block in blocks) block.serialize(this)
  }
}
