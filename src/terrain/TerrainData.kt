package terrain

import utils.byteBufferWriter
import utils.readClass
import utils.times
import utils.u1
import java.io.File
import java.nio.ByteBuffer

typealias TerrainTiles = List<List<Pair<TerrainTileType, TerrainTileType>>>

/**
 * TODO: rewrite, this is so terrible
 */
class TerrainGrid(val map: Map<Pair<Int, Int>, Pair<TerrainTileType, TerrainTileType>>) : Map<Pair<Int, Int>, Pair<TerrainTileType, TerrainTileType>> by map {
  operator fun get(x: Int, y: Int) = map[x to y]

  fun rowOrderIterator(): Iterator<Pair<Pair<Int, Int>, Pair<TerrainTileType, TerrainTileType>>> = iterator {
    for (y in 0 until 32) {
      for (x in 0 until 32) {
        yield((x to y) to (this@TerrainGrid[x, y]!!))
      }
    }
  }

  fun findMinimumMapSize(): Pair<Int, Int> {
    var rows = 0
    var cols = 0
    for ((coords, tile) in rowOrderIterator()) {
      val (x, y) = coords
      if (tile.first != TerrainTileType.Impassable_0) {
        if (rows < y) rows = y
        if (cols < x) cols = x
      }
    }
    return cols + 1 to rows + 1
  }

  fun minimalRowOrderIterator(): Iterator<Pair<Pair<Int, Int>, Pair<TerrainTileType, TerrainTileType>>> = iterator<Pair<Pair<Int, Int>, Pair<TerrainTileType, TerrainTileType>>> {
    val (width, height) = findMinimumMapSize()

    yieldAll(rowOrderIterator().asSequence().filter { (coords, _) -> coords.first < width && coords.second < height})
  }

}

class TerrainData(val header: Header, val grid: TerrainGrid) {

  constructor(buffer: ByteBuffer) : this(buffer.readClass(Header::class), TerrainGrid(buildMap {
    32.times { y ->
      32.times { x ->
        put(x to y, TerrainTileType.values()[buffer.u1.toInt()] to TerrainTileType.values()[buffer.u1.toInt()])
      }
    }
  }))
  data class Header(val unk1: UByte, val unk2: UByte, val unk3: UByte, val unk4: UByte, val unk5: UByte, val unk6: UByte, val unk7: UByte, val unk8: UByte)

  companion object {
    fun write(file: File, tiles: TerrainData) = file.byteBufferWriter {
      u1(tiles.header.unk1)
      u1(tiles.header.unk2)
      u1(tiles.header.unk3)
      u1(tiles.header.unk4)
      u1(tiles.header.unk5)
      u1(tiles.header.unk6)
      u1(tiles.header.unk7)
      u1(tiles.header.unk8)

      for (y in 0 until 32) {
        for (x in 0 until 32) {
          val tileAt = tiles.grid[x to y]!!
          u1(tileAt.first.ordinal.toUByte())
          u1(tileAt.second.ordinal.toUByte())
        }
      }
    }
  }
}

enum class TerrainTileType {
  Impassable_0,
  Grassland_1,
  Plain_2,
  Forest_3,
  Woods_4,
  Desert_5,
  River_6,
  Waterfall_7,
  Rock_8,
  Mountain_9,
  Bridge_10,
  Sea_11,
  Floor_12,
  Wall_13,
  Fence_14,
  Stairs_15,
  Rampart_16,
  Door_17,
  Heal_Tile_18,
  Rubble_19,
  Wall_20,
  Lava_21,
  Stronghold_22,
  Peak_23,
  Thicket_24,
  Thicket_25,
  Flier_26,
  Tile_27,
  Swamp_28,
  Cavity_29,
  Stones_30,
  Railing_31,
  Bonfire_32,
  Flames_33,
  Pillar_34,
  Throne_35,
  Sky_36,
  Cliff_37,
  Wall_38,
  Snowfield_39,
  Heal_Tile_Plus_40,
  Tower_41,
  House_42,
  Valley_43,
  Grave_44,
  Coffin_45,
  Chest_46,
  Holy_Tomb_47,
  Deck_48,
  Ship_49,
  Floor_Trap_50,
  Lever_51,
  Fiery_Floor_52,
  Healstone_53,
  Crater_54,
  Crater_55,
  Wardwood_56,
  Cover_57,
  Avo_Floor_58,
  Pray_Stone_59,
  Ballista_60,
  Fire_Orb_61,
  Healstone_62,
  Altar_63,
  Viskam_64,
  Edifice_65,
  QuestionMarks_66,
  Onager_67,
  Iron_Fence_68,
  Warp_Floor_69,
  Shoal_70,
  Lake_71,
  Bridge_72,
  Beach_73,
  Ruins_74,
  Statue_75,
  Flame_76,
  Trench_77,
  Crest_Stone_78,
  Floor_79,
  Poison_80,
  Door_81,
  Stair_82,
  Pond_83,
  Church_84,
  Opera_Hall_85,
  Channel_86,
  Altar_87,
  Hedge_88,
  Gate_89,
  Low_wall_90,
  QuestionMark_Floor_91,
  Floor_92,
  Windmill_93,
  iron_untranslated_94,
  Wall_95,
  iron_untranslated_96,
  iron_untranslated_97,
  Odd_Item_98,
  Odd_Door_99,
  Mast_100,
  Bow_101,
  Pillar_102,
  BLANK_103,
  BLANK_104,
  BLANK_105,
  Wasteland_106,
  Abyss_Gate_107,
  Magic_Seal_108,
  Statue_109,
  Statue_110,
  Statue_111,
  Statue_112,
  Statue_113,
  Vortex_114,
  BLANK_115,
  BLANK_116,
  BLANK_117,
  BLANK_118,
  BLANK_119,
  BLANK_120,
  BLANK_121,
  BLANK_122,
  BLANK_123,
  BLANK_124,
  BLANK_125,
  BLANK_126,
  BLANK_127,
  BLANK_128,
  BLANK_129,
  BLANK_130,
  BLANK_131,
  BLANK_132,
  BLANK_133,
  BLANK_134,
  BLANK_135,
  BLANK_136,
  BLANK_137,
  BLANK_138,
  BLANK_139,
  BLANK_140,
  BLANK_141,
  BLANK_142,
  BLANK_143,
  BLANK_144,
  BLANK_145,
  BLANK_146,
  BLANK_147,
  BLANK_148,
  BLANK_149,
  BLANK_150,
  BLANK_151,
  BLANK_152,
  BLANK_153,
  BLANK_154,
  BLANK_155,
  BLANK_156,
  BLANK_157,
  BLANK_158,
  BLANK_159,
  BLANK_160,
  BLANK_161,
  BLANK_162,
  BLANK_163,
  BLANK_164,
  BLANK_165,
  BLANK_166,
  BLANK_167,
  BLANK_168,
  BLANK_169,
  BLANK_170,
  BLANK_171,
  BLANK_172,
  BLANK_173,
  BLANK_174,
  BLANK_175,
  BLANK_176,
  BLANK_177,
  BLANK_178,
  BLANK_179,
  BLANK_180,
  BLANK_181,
  BLANK_182,
  BLANK_183,
  BLANK_184,
  BLANK_185,
  BLANK_186,
  BLANK_187,
  BLANK_188,
  BLANK_189,
  BLANK_190,
  BLANK_191,
  BLANK_192,
  BLANK_193,
  BLANK_194,
  BLANK_195,
  BLANK_196,
  BLANK_197,
  BLANK_198,
  BLANK_199,
  BLANK_200,
  BLANK_201,
  BLANK_202,
  BLANK_203,
  BLANK_204,
  BLANK_205,
  BLANK_206,
  BLANK_207,
  BLANK_208,
  BLANK_209,
  BLANK_210,
  BLANK_211,
  BLANK_212,
  BLANK_213,
  BLANK_214,
  BLANK_215,
  BLANK_216,
  BLANK_217,
  BLANK_218,
  BLANK_219,
  BLANK_220,
  BLANK_221,
  BLANK_222,
  BLANK_223,
  BLANK_224,
  BLANK_225,
  BLANK_226,
  BLANK_227,
  BLANK_228,
  BLANK_229,
  BLANK_230,
  BLANK_231,
  BLANK_232,
  BLANK_233,
  BLANK_234,
  BLANK_235,
  BLANK_236,
  BLANK_237,
  BLANK_238,
  BLANK_239,
  BLANK_240,
  BLANK_241,
  BLANK_242,
  BLANK_243,
  BLANK_244,
  BLANK_245,
  BLANK_246,
  BLANK_247,
  BLANK_248,
  BLANK_249,
  BLANK_250,
  BLANK_251,
  BLANK_252,
  BLANK_253,
  BLANK_254,
  BLANK_255
}
