package models

enum class Language {
  JP,
  ENG_U,
  ENG_E,
  GER,
  FRA_E,
  FRA_U,
  ESP_E,
  ESP_U,
  ITA,
  KOR,
  TWN,
  CHN;

  companion object {
    val EnglishFirst = arrayOf(
      ENG_U,
      JP,
      ENG_E,
      GER,
      FRA_E,
      FRA_U,
      ESP_E,
      ESP_U,
      ITA,
      KOR,
      TWN,
      CHN
    )
  }
}
