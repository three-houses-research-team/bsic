@file:Suppress("unused")

package models

import terrain.TerrainTileType.*


val terrainColors = mapOf(
  Impassable_0 to "black",
  Grassland_1 to "lightgreen",
  Plain_2 to "#ffff9e",
  Forest_3 to "forestgreen",
  Woods_4 to "#335319",
  Desert_5 to "#c1a55e",
  River_6 to "PALETURQUOISE",
  Waterfall_7 to "rgba(4, 33, 90, 0.6)",
  Rock_8 to "DARKGOLDENROD",
  Mountain_9 to "#3d2200",
  Bridge_10 to "peru",
  Sea_11 to "#001665",
  Floor_12 to "white",
  Wall_13 to "lightgray",
  Fence_14 to "#b1913f",
  Stairs_15 to "#c6c6c6",
  Rampart_16 to "white",
  Door_17 to "#841515",
  Heal_Tile_18 to "#27ff00",
  Rubble_19 to "#909090",
  Wall_20 to "lightgray",
  Lava_21 to "#ff8500",
  Stronghold_22 to "white",
  Peak_23 to "white",
  Thicket_24 to "#2d5812",
  Thicket_25 to "#1e4c15",
  Flier_26 to "white",
  Tile_27 to "#a3a3a3",
  Swamp_28 to "#32402c",
  Cavity_29 to "white",
  Stones_30 to "#505050",
  Railing_31 to "white",
  Bonfire_32 to "white",
  Flames_33 to "white",
  Pillar_34 to "#361600",
  Throne_35 to "white",
  Sky_36 to "white",
  Cliff_37 to "#939393",
  Wall_38 to "white",
  Snowfield_39 to "white",
  Heal_Tile_Plus_40 to "#27ff00",
  Tower_41 to "white",
  House_42 to "white",
  Valley_43 to "white",
  Grave_44 to "white",
  Coffin_45 to "white",
  Chest_46 to "white",
  Holy_Tomb_47 to "white",
  Deck_48 to "white",
  Ship_49 to "white",
  Floor_Trap_50 to "white",
  Lever_51 to "white",
  Fiery_Floor_52 to "white",
  Healstone_53 to "white",
  Crater_54 to "white",
  Crater_55 to "white",
  Wardwood_56 to "white",
  Cover_57 to "white",
  Avo_Floor_58 to "white",
  Pray_Stone_59 to "white",
  Ballista_60 to "white",
  Fire_Orb_61 to "white",
  Healstone_62 to "white",
  Altar_63 to "white",
  Viskam_64 to "white",
  Edifice_65 to "white",
  QuestionMarks_66 to "white",
  Onager_67 to "white",
  Iron_Fence_68 to "white",
  Warp_Floor_69 to "white",
  Shoal_70 to "white",
  Lake_71 to "white",
  Bridge_72 to "white",
  Beach_73 to "#ffdd85",
  Ruins_74 to "white",
  Statue_75 to "white",
  Flame_76 to "white",
  Trench_77 to "white",
  Crest_Stone_78 to "white",
  Floor_79 to "white",
  Poison_80 to "white",
  Door_81 to "white",
  Stair_82 to "white",
  Pond_83 to "white",
  Church_84 to "white",
  Opera_Hall_85 to "white",
  Channel_86 to "white",
  Altar_87 to "white",
  Hedge_88 to "white",
  Gate_89 to "white",
  Low_wall_90 to "white",
  QuestionMark_Floor_91 to "white",
  Floor_92 to "white",
  Windmill_93 to "white",
  iron_untranslated_94 to "white",
  Wall_95 to "white",
  iron_untranslated_96 to "white",
  iron_untranslated_97 to "white",
  Odd_Item_98 to "white",
  Odd_Door_99 to "white",
  Mast_100 to "white",
  Bow_101 to "white",
  Pillar_102 to "white",
  BLANK_103 to "white",
  BLANK_104 to "white",
  BLANK_105 to "white",
  Wasteland_106 to "#93866f",
  Abyss_Gate_107 to "white",
  Magic_Seal_108 to "white",
  Statue_109 to "white",
  Statue_110 to "white",
  Statue_111 to "white",
  Statue_112 to "white",
  Statue_113 to "white",
  Vortex_114 to "white",
  BLANK_115 to "white",
  BLANK_116 to "white",
  BLANK_117 to "white",
  BLANK_118 to "white",
  BLANK_119 to "white",
  BLANK_120 to "white",
  BLANK_121 to "white",
  BLANK_122 to "white",
  BLANK_123 to "white",
  BLANK_124 to "white",
  BLANK_125 to "white",
  BLANK_126 to "white",
  BLANK_127 to "white",
  BLANK_128 to "white",
  BLANK_129 to "white",
  BLANK_130 to "white",
  BLANK_131 to "white",
  BLANK_132 to "white",
  BLANK_133 to "white",
  BLANK_134 to "white",
  BLANK_135 to "white",
  BLANK_136 to "white",
  BLANK_137 to "white",
  BLANK_138 to "white",
  BLANK_139 to "white",
  BLANK_140 to "white",
  BLANK_141 to "white",
  BLANK_142 to "white",
  BLANK_143 to "white",
  BLANK_144 to "white",
  BLANK_145 to "white",
  BLANK_146 to "white",
  BLANK_147 to "white",
  BLANK_148 to "white",
  BLANK_149 to "white",
  BLANK_150 to "white",
  BLANK_151 to "white",
  BLANK_152 to "white",
  BLANK_153 to "white",
  BLANK_154 to "white",
  BLANK_155 to "white",
  BLANK_156 to "white",
  BLANK_157 to "white",
  BLANK_158 to "white",
  BLANK_159 to "white",
  BLANK_160 to "white",
  BLANK_161 to "white",
  BLANK_162 to "white",
  BLANK_163 to "white",
  BLANK_164 to "white",
  BLANK_165 to "white",
  BLANK_166 to "white",
  BLANK_167 to "white",
  BLANK_168 to "white",
  BLANK_169 to "white",
  BLANK_170 to "white",
  BLANK_171 to "white",
  BLANK_172 to "white",
  BLANK_173 to "white",
  BLANK_174 to "white",
  BLANK_175 to "white",
  BLANK_176 to "white",
  BLANK_177 to "white",
  BLANK_178 to "white",
  BLANK_179 to "white",
  BLANK_180 to "white",
  BLANK_181 to "white",
  BLANK_182 to "white",
  BLANK_183 to "white",
  BLANK_184 to "white",
  BLANK_185 to "white",
  BLANK_186 to "white",
  BLANK_187 to "white",
  BLANK_188 to "white",
  BLANK_189 to "white",
  BLANK_190 to "white",
  BLANK_191 to "white",
  BLANK_192 to "white",
  BLANK_193 to "white",
  BLANK_194 to "white",
  BLANK_195 to "white",
  BLANK_196 to "white",
  BLANK_197 to "white",
  BLANK_198 to "white",
  BLANK_199 to "white",
  BLANK_200 to "white",
  BLANK_201 to "white",
  BLANK_202 to "white",
  BLANK_203 to "white",
  BLANK_204 to "white",
  BLANK_205 to "white",
  BLANK_206 to "white",
  BLANK_207 to "white",
  BLANK_208 to "white",
  BLANK_209 to "white",
  BLANK_210 to "white",
  BLANK_211 to "white",
  BLANK_212 to "white",
  BLANK_213 to "white",
  BLANK_214 to "white",
  BLANK_215 to "white",
  BLANK_216 to "white",
  BLANK_217 to "white",
  BLANK_218 to "white",
  BLANK_219 to "white",
  BLANK_220 to "white",
  BLANK_221 to "white",
  BLANK_222 to "white",
  BLANK_223 to "white",
  BLANK_224 to "white",
  BLANK_225 to "white",
  BLANK_226 to "white",
  BLANK_227 to "white",
  BLANK_228 to "white",
  BLANK_229 to "white",
  BLANK_230 to "white",
  BLANK_231 to "white",
  BLANK_232 to "white",
  BLANK_233 to "white",
  BLANK_234 to "white",
  BLANK_235 to "white",
  BLANK_236 to "white",
  BLANK_237 to "white",
  BLANK_238 to "white",
  BLANK_239 to "white",
  BLANK_240 to "white",
  BLANK_241 to "white",
  BLANK_242 to "white",
  BLANK_243 to "white",
  BLANK_244 to "white",
  BLANK_245 to "white",
  BLANK_246 to "white",
  BLANK_247 to "white",
  BLANK_248 to "white",
  BLANK_249 to "white",
  BLANK_250 to "white",
  BLANK_251 to "white",
  BLANK_252 to "white",
  BLANK_253 to "white",
  BLANK_254 to "white",
  BLANK_255 to "white",
)
