@file:Suppress("unused", "SpellCheckingInspection", "EnumEntryName")
package models

interface InventorySlottable {
  companion object {
    val all = buildList<InventorySlottable> {
      addAll(Weapon.values())
      addAll(Accessory.values())
      addAll(Consumable.values())
    }
  }
}

val InventorySlottable?.index get() = when(this) {
  null -> 0
  is Weapon -> 1 + ordinal
  is Accessory -> 601 + ordinal
  is Consumable -> 1001 + ordinal
  else -> error("Don't know what $this is")
}

enum class Weapon : InventorySlottable {
  BLANK_0,
  BLANK_1,
  BLANK_2,
  BLANK_3,
  BLANK_4,
  BLANK_5,
  BLANK_6,
  BLANK_7,
  BLANK_8,
  BLANK_9,
  BLANK_10,
  Unarmed_11,
  Broken_Sword_12,
  Broken_Lance_13,
  Broken_Axe_14,
  Broken_Bow_15,
  Broken_Gauntlet_16,
  Iron_Sword_17,
  Steel_Sword_18,
  Silver_Sword_19,
  Brave_Sword_20,
  Killing_Edge_21,
  Training_Sword_22,
  Iron_Lance_23,
  Steel_Lance_24,
  Silver_Lance_25,
  Brave_Lance_26,
  Killer_Lance_27,
  Training_Lance_28,
  Iron_Axe_29,
  Steel_Axe_30,
  Silver_Axe_31,
  Brave_Axe_32,
  Killer_Axe_33,
  Training_Axe_34,
  Iron_Bow_35,
  Steel_Bow_36,
  Silver_Bow_37,
  Brave_Bow_38,
  Killer_Bow_39,
  Training_Bow_40,
  Iron_Gauntlets_41,
  Steel_Gauntlets_42,
  Silver_Gauntlets_43,
  Training_Gauntlets_44,
  Levin_Sword_45,
  Bolt_Axe_46,
  Magic_Bow_47,
  Javelin_48,
  Short_Spear_49,
  Spear_50,
  Hand_Axe_51,
  Short_Axe_52,
  Tomahawk_53,
  Longbow_54,
  Mini_Bow_55,
  Armorslayer_56,
  Rapier_57,
  Horseslayer_58,
  Hammer_59,
  Blessed_Lance_60,
  Blessed_Bow_61,
  Devil_Sword_62,
  Devil_Axe_63,
  Wo_Dao_64,
  Crescent_Sickle_65,
  Sword_of_Seiros_66,
  Sword_of_Begalta_67,
  Sword_of_Moralta_68,
  Cursed_Ashiya_Sword_69,
  Sword_of_Zoltan_70,
  Thunderbrand_71,
  Blutgang_72,
  Sword_of_the_Creator_73,
  Lance_of_Zoltan_74,
  Lance_of_Ruin_75,
  Areadbhar_76,
  L_in_77,
  Spear_of_Assal_78,
  Scythe_of_Sariel_79,
  Arrow_of_Indra_80,
  Freikugel_81,
  Crusher_82,
  Axe_of_Ukonvasara_83,
  Axe_of_Zoltan_84,
  Tathlum_Bow_85,
  The_Inexhaustible_86,
  Bow_of_Zoltan_87,
  Failnaught_88,
  Dragon_Claws_89,
  Mace_90,
  Athame_91,
  Ridill_92,
  Aymr_93,
  Dark_Creator_Sword_94,
  Venin_Edge_95,
  Venin_Lance_96,
  Venin_Axe_97,
  Venin_Bow_98,
  Mercurius_99,
  Gradivus_100,
  Hauteclere_101,
  Parthia_102,
  Killer_Knuckles_103,
  Aura_Knuckles_104,
  Rusted_Sword_105,
  Rusted_Sword_106,
  Rusted_Sword_107,
  Rusted_Sword_108,
  Rusted_Sword_109,
  Rusted_Lance_110,
  Rusted_Lance_111,
  Rusted_Lance_112,
  Rusted_Lance_113,
  Rusted_Lance_114,
  Rusted_Axe_115,
  Rusted_Axe_116,
  Rusted_Axe_117,
  Rusted_Axe_118,
  Rusted_Axe_119,
  Rusted_Bow_120,
  Rusted_Bow_121,
  Rusted_Bow_122,
  Rusted_Bow_123,
  Rusted_Bow_124,
  Rusted_Gauntlets_125,
  Rusted_Gauntlets_126,
  Rusted_Gauntlets_127,
  Rusted_Gauntlets_128,
  Iron_SwordPLUS_129,
  Steel_SwordPLUS_130,
  Silver_SwordPLUS_131,
  Brave_SwordPLUS_132,
  Killing_EdgePLUS_133,
  Training_SwordPLUS_134,
  Iron_LancePLUS_135,
  Steel_LancePLUS_136,
  Silver_LancePLUS_137,
  Brave_LancePLUS_138,
  Killer_LancePLUS_139,
  Training_LancePLUS_140,
  Iron_AxePLUS_141,
  Steel_AxePLUS_142,
  Silver_AxePLUS_143,
  Brave_AxePLUS_144,
  Killer_AxePLUS_145,
  Training_AxePLUS_146,
  Iron_BowPLUS_147,
  Steel_BowPLUS_148,
  Silver_BowPLUS_149,
  Brave_BowPLUS_150,
  Killer_BowPLUS_151,
  Training_BowPLUS_152,
  Iron_GauntletsPLUS_153,
  Steel_GauntletsPLUS_154,
  Silver_GauntletsPLUS_155,
  Training_GauntletsPLUS_156,
  Levin_SwordPLUS_157,
  Bolt_AxePLUS_158,
  Magic_BowPLUS_159,
  JavelinPLUS_160,
  Short_SpearPLUS_161,
  SpearPLUS_162,
  Hand_AxePLUS_163,
  Short_AxePLUS_164,
  TomahawkPLUS_165,
  LongbowPLUS_166,
  Mini_BowPLUS_167,
  ArmorslayerPLUS_168,
  RapierPLUS_169,
  HorseslayerPLUS_170,
  HammerPLUS_171,
  Blessed_LancePLUS_172,
  Blessed_BowPLUS_173,
  Devil_SwordPLUS_174,
  Devil_AxePLUS_175,
  Wo_DaoPLUS_176,
  Crescent_SicklePLUS_177,
  Cursed_Ashiya_SwordPLUS_178,
  Sword_of_ZoltanPLUS_179,
  Lance_of_ZoltanPLUS_180,
  Arrow_of_IndraPLUS_181,
  Axe_of_ZoltanPLUS_182,
  Bow_of_ZoltanPLUS_183,
  Dragon_ClawsPLUS_184,
  MacePLUS_185,
  Venin_EdgePLUS_186,
  Venin_LancePLUS_187,
  Venin_AxePLUS_188,
  Venin_BowPLUS_189,
  Killer_KnucklesPLUS_190,
  Aura_KnucklesPLUS_191,
  Sublime_Creator_Sword_192,
  Dark_Thunderbrand_193,
  Dark_Blutgang_194,
  Dark_Lance_of_Ruin_195,
  Areadbhar___196,
  L_in___197,
  Freikugel___198,
  Dark_Crusher_199,
  Failnaught___200,
  Vajra_Mushti_201,
  BLANK_202,
  BLANK_203,
  BLANK_204,
  BLANK_205,
  BLANK_206,
  BLANK_207,
  BLANK_208,
  BLANK_209,
  BLANK_210,
  Crest_Stone__Gautier__211,
  Crest_Stone__Beast__212,
  Cracked_Crest_Stone_213,
  Crest_Stone_Shard_214,
  Artificial_Crest_Stone_S_215,
  Artificial_Crest_Stone_L_216,
  Giant_Art__Crest_Stone_217,
  Pointy_Art__Crest_Stone_218,
  Lance_of_Light_219,
  Lance_of_LightPLUS_220,
  Giant_Katar_221,
  Blest_Crest_Stone_Shard_222,
  Crest_Stone_of_Seiros_223,
  Crest_Stone_of_Seiros_224,
  Real_Seiros_Crest_Stone_225,
  Crest_Stone_of_Macuil_226,
  Crest_Stone_of_Indech_227,
  Dark_Stone__Bird__228,
  Dark_Stone__Crawler__229,
  Dark_Stone__Wolf__230,
  Crest_of_Flames_Power_231,
  Twin_Crest_Power_232,
  Piercing_Light_Lance_233,
  Throwing_Light_Lance_234,
  Sharp_Wings_235,
  Lost_Crest_Stone_236,
  Lost_Crest_Stone__Lg__237,
  Crest_of_Flames_Power_238,
  Crest_of_Flames_Power_239,
  Chalice_of_Blood_240,
  Crest_Stone__Chevalier__241,
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
  BLANK_255,
  BLANK_256,
  BLANK_257,
  BLANK_258,
  BLANK_259,
  BLANK_260,
  BLANK_261,
  BLANK_262,
  BLANK_263,
  BLANK_264,
  BLANK_265,
  BLANK_266,
  BLANK_267,
  BLANK_268,
  BLANK_269,
  BLANK_270,
  BLANK_271,
  BLANK_272,
  BLANK_273,
  BLANK_274,
  BLANK_275,
  BLANK_276,
  BLANK_277,
  BLANK_278,
  BLANK_279,
  BLANK_280,
  BLANK_281,
  BLANK_282,
  BLANK_283,
  BLANK_284,
  BLANK_285,
  BLANK_286,
  BLANK_287,
  BLANK_288,
  BLANK_289,
  BLANK_290,
  BLANK_291,
  BLANK_292,
  BLANK_293,
  BLANK_294,
  BLANK_295,
  BLANK_296,
  BLANK_297,
  BLANK_298,
  BLANK_299,
  BLANK_300,
  BLANK_301,
  BLANK_302,
  BLANK_303,
  BLANK_304,
  BLANK_305,
  BLANK_306,
  BLANK_307,
  BLANK_308,
  BLANK_309,
  BLANK_310,
}

enum class Accessory : InventorySlottable {
  Leather_Shield_0,
  Iron_Shield_1,
  Steel_Shield_2,
  Silver_Shield_3,
  Talisman_Shield_4,
  Hexlock_Shield_5,
  Aegis_Shield_6,
  Ochain_Shield_7,
  Seiros_Shield_8,
  Aurora_Shield_9,
  Kadmos_Shield_10,
  Lampos_Shield_11,
  Accuracy_Ring_12,
  Critical_Ring_13,
  Evasion_Ring_14,
  Speed_Ring_15,
  March_Ring_16,
  Goddess_Ring_17,
  Prayer_Ring_18,
  Magic_Staff_19,
  Healing_Staff_20,
  Caduceus_Staff_21,
  Thyrsus_22,
  Rafail_Gem_23,
  Experience_Gem_24,
  Knowledge_Gem_25,
  Circe_Staff_26,
  Tomas_s_Staff_27,
  Armored_Soldier_Shield_28,
  Armored_Knight_Shield_29,
  Fortress_Soldier_Shield_30,
  Asclepius_31,
  Dark_Aegis_Shield_32,
  Dark_Thyrsus_33,
  Dark_Rafail_Gem_34,
  Flame_Shield_35,
  Emperor_Shield_36,
  Black_Eagle_Pendant_37,
  Blue_Lion_Brooch_38,
  Golden_Deer_Bracelet_39,
  White_Dragon_Scarf_40,
  Chalice_of_Beginnings_41,
  Fetters_of_Dromi_42,
  Item_43_43,
  Item_44_44,
  Item_45_45,
  Item_46_46,
  Item_47_47,
  Item_48_48,
  Item_49_49,
}

enum class Consumable : InventorySlottable {
  Vulnerary_0,
  Concoction_1,
  Elixir_2,
  Intermediate_Seal_3,
  Advanced_Seal_4,
  Combined_Seal_5,
  Master_Seal_6,
  Torch_7,
  Bullion_8,
  Large_Bullion_9,
  Extra_Large_Bullion_10,
  Antitoxin_11,
  Pure_Water_12,
  Door_Key_13,
  Chest_Key_14,
  Master_Key_15,
  Seraph_Robe_16,
  Energy_Drop_17,
  Spirit_Dust_18,
  Secret_Book_19,
  Speedwing_20,
  Goddess_Icon_21,
  Giant_Shell_22,
  Talisman_23,
  Black_Pearl_24,
  Shoes_of_the_Wind_25,
  Grilled_Duck_26,
  Fried_Boar_27,
  Magical_Herb_Salad_28,
  Dried_Plums_29,
  Pike_Casserole_30,
  Bear___Vegetable_Soup_31,
  Fried_Gar_32,
  Fried_Queen_Koi_33,
  Sculpin_Gratin_34,
  Grilled_Wolverine___Herbs_35,
  Grilled_Duck___36,
  Fried_Boar___37,
  Magical_Herb_Salad___38,
  Dried_Plums___39,
  Pike_Casserole___40,
  Bear___Vegetable_Soup___41,
  Fried_Gar___42,
  Fried_Queen_Koi___43,
  Sculpin_Gratin___44,
  Grilled_Wolverine___Herbs___45,
  Albinea_Juice_46,
  Grilled_Chicken___Herbs_47,
  Roast_Duck_48,
  Grilled_Chicken___Herbs___49,
  Roast_Duck___50,
  Sacred_Galewind_Shoes_51,
  Sacred_Floral_Robe_52,
  Sacred_Snowmelt_Drop_53,
  Sacred_Moonstone_54,
  BLANK_55,
  BLANK_56,
  BLANK_57,
  BLANK_58,
  BLANK_59,
  BLANK_60,
  BLANK_61,
  BLANK_62,
  BLANK_63,
  BLANK_64,
  BLANK_65,
  BLANK_66,
  BLANK_67,
  BLANK_68,
  BLANK_69,
  BLANK_70,
  BLANK_71,
  BLANK_72,
  BLANK_73,
  BLANK_74,
  BLANK_75,
  BLANK_76,
  BLANK_77,
  BLANK_78,
  BLANK_79,
  BLANK_80,
  BLANK_81,
  BLANK_82,
  BLANK_83,
  BLANK_84,
  BLANK_85,
  BLANK_86,
  BLANK_87,
  BLANK_88,
  Dish_63_89,
  Dish_64_90,
  Dish_65_91,
  Dish_66_92,
  Dish_67_93,
  Dish_68_94,
  Dish_69_95,
  Dish_70_96,
  Dish_71_97,
  Dish_72_98,
  Dish_73_99,
  Dish_74_100,
  Dish_75_101,
  Dish_76_102,
  Dish_77_103,
  Dish_78_104,
  Dish_79_105,
  Dish_80_106,
  Dish_81_107,
  Dish_82_108,
  Dish_83_109,
  Dish_84_110,
  Dish_85_111,
  Dish_86_112,
  Dish_87_113,
  Dish_88_114,
  Dish_89_115,
  Dish_90_116,
  Dish_91_117,
  Dish_92_118,
  Dish_93_119,
  Dish_94_120,
  Dish_95_121,
  Dish_96_122,
  Dish_97_123,
  Dish_98_124,
  Dish_99_125,
  Thorn_Dragon_Sign_126,
  Wind_Dragon_Sign_127,
  Sky_Dragon_Sign_128,
  Crusher_Dragon_Sign_129,
  Shield_Dragon_Sign_130,
  Bloom_Dragon_Sign_131,
  Light_Dragon_Sign_132,
  Flame_Dragon_Sign_133,
  Grim_Dragon_Sign_134,
  Craft_Dragon_Sign_135,
  Kalpa_Dragon_Sign_136,
  Earth_Dragon_Sign_137,
  Ice_Dragon_Sign_138,
  Fissure_Dragon_Sign_139,
  Water_Dragon_Sign_140,
  Storm_Dragon_Sign_141,
  Lightning_Dragon_Sign_142,
  Dark_Dragon_Sign_143,
  Star_Dragon_Sign_144,
  Snow_Dragon_Sign_145,
  Aegis_Dragon_Sign_146,
  Crest_Stone_147,
  Rocky_Burdock_148,
  Premium_Magic_Herbs_149,
  Ailell_Pomegranate_150,
  Speed_Carrot_151,
  Miracle_Bean_152,
  Ambrosia_153,
  White_Verona_154,
  Golden_Apple_155,
  Fruit_of_Life_156,
  Dark_Seal_157,
  Beginner_Seal_158,
  Abyssian_Exam_Pass_159,
  Spellbreak_Key_160,
  Trade_Secret_161,
  Spare_slot_15_162,
  Spare_slot_16_163,
  Spare_slot_17_164,
  Spare_slot_18_165,
  Spare_slot_19_166,
  Spare_slot_20_167,
  Spare_slot_21_168,
  Spare_slot_22_169,
  Spare_slot_23_170,
  Spare_slot_24_171,
  Spare_slot_25_172,
  Spare_slot_26_173,
  Spare_slot_27_174,
  Spare_slot_28_175,
  Spare_slot_29_176,
  Spare_slot_30_177,
  Spare_slot_31_178,
  Spare_slot_32_179,
  Spare_slot_33_180,
  Spare_slot_34_181,
  Spare_slot_35_182,
  Spare_slot_36_183,
  Spare_slot_37_184,
  Spare_slot_38_185,
  Spare_slot_39_186,
  Spare_slot_40_187,
  Spare_slot_41_188,
  Spare_slot_42_189,
  Spare_slot_43_190,
  Spare_slot_44_191,
  Spare_slot_45_192,
  Spare_slot_46_193,
  Spare_slot_47_194,
  Spare_slot_48_195,
  Spare_slot_49_196,
  Spare_slot_50_197,
  Spare_slot_51_198,
  Spare_slot_52_199,
}
