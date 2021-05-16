package models

import utils.OfLength

@OfLength(1)
enum class ScriptSpawn {
  SCRIPTED_SPAWN,
  FORCED_SPAWN,
  UNK2,
  UNK3,
}
