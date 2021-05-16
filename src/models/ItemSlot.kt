package models

data class ItemSlotU2(val raw: UShort) {
  val slotted: InventorySlottable? = when(val raw = raw.toInt()) {
    0 -> null
    in 1..311 -> Weapon.values()[raw]
    in 600..999 -> Accessory.values()[raw - 601]
    in 1000..1226 -> Consumable.values()[raw - 1001]
    else -> error("Don't know what $raw is")
  }
  override fun toString() = "ItemSlot(raw=$raw=${slotted})"
}

data class ItemSlot(val raw: UInt) {
  val slotted: InventorySlottable? = when(val raw = raw.toInt()) {
    in 0..310 -> Weapon.values()[raw-1]
    in 600..999 -> Accessory.values()[raw - 600]
    in 1000..1226 -> Consumable.values()[raw - 1000]
    else -> error("Don't know what $raw is")
  }
  override fun toString() = "ItemSlot(raw=$raw=${slotted})"
}
