## BSIC: BSI compiler

Converts binary BSI scripts to/from a nice DSL.

### Binary BSI -> BSIC
1. Have a dump of fe3h.
2. Locate a BSI file. 
    * Base game battles and stuff: [84](https://fedatamine.com/en-us/battles/0/a-skirmish-at-dawn), [86](https://fedatamine.com/en-us/battles/1/rivalry-of-the-houses), 88, ..., 246
    * Base game monastery: [256](https://fedatamine.com/en-us/monastery/0), [258](https://fedatamine.com/en-us/monastery/1), ... 364
    
```
$ <bsic> parse -i /data/fe3h/base/84
```

Output (sample):
```
bsic {
  event {
    trigger = Trigger(type=2000, param1=0, param2=0, param3=0)
    conditions = Difficulty(unk=0, difficulty=NORMAL, unk2=2)
    actions += SpawnUnit(bai=BaiSlot(raw=20), unk2=19, unk3=0, unk4=0)
    actions += SpawnUnit(bai=BaiSlot(raw=34), unk2=17, unk3=0, unk4=0)
    actions += SpawnUnit(bai=BaiSlot(raw=35), unk2=18, unk3=1, unk4=0)
    actions += SpawnUnit(bai=BaiSlot(raw=30), unk2=16, unk3=0, unk4=0)
    // you get the idea
  }
  event {
    trigger = Trigger(type=2000, param1=0, param2=0, param3=0)
    conditions = Difficulty(unk=0, difficulty=HARD, unk2=2)
    actions += SpawnUnit(bai=BaiSlot(raw=20), unk2=19, unk3=0, unk4=0)
    actions += SpawnUnit(bai=BaiSlot(raw=44), unk2=17, unk3=0, unk4=0)
    actions += SpawnUnit(bai=BaiSlot(raw=45), unk2=18, unk3=1, unk4=0)
    actions += SpawnUnit(bai=BaiSlot(raw=30), unk2=16, unk3=0, unk4=0)
    actions += SpawnUnit(bai=BaiSlot(raw=31), unk2=15, unk3=0, unk4=0)
    // you get the idea
  }
  
  event { // author's note: bai slot 20 is Kostas in this map
    trigger = Trigger(type=2000, param1=0, param2=34, param3=0)
    conditions = CheckDeathStatus(unk=0, bai=BaiSlot(raw=20), isDead=true)
    actions += PlayMovie(movieID=1, unk=1)
  }
  
  event { // jeralt: Yes, good. If we're in the forest, we can sustain their attacks without losing the advantage.
    trigger = Trigger(type=2000, param1=0, param2=13, param3=0)
    conditions = (HasOtherScriptRun(unk=0, scriptIndex=14, didRun=true) and (MovedAnyUnitInRectangle(unk0=0, unk1=0, left=4, top=9, right=4, bottom=9, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=5, top=7, right=5, bottom=11, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=6, top=8, right=6, bottom=12, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=7, top=10, right=7, bottom=12, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=8, top=11, right=8, bottom=11, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=9, top=10, right=9, bottom=10, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=11, top=10, right=11, bottom=10, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=12, top=8, right=12, bottom=8, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=13, top=7, right=13, bottom=7, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=14, top=8, right=14, bottom=8, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=15, top=7, right=20, bottom=9, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=16, top=10, right=20, bottom=11, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=17, top=12, right=20, bottom=12, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=19, top=13, right=20, bottom=13, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=20, top=14, right=20, bottom=14, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=0, top=3, right=0, bottom=12, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=1, top=4, right=1, bottom=11, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=2, top=5, right=2, bottom=5, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=4, top=0, right=12, bottom=0, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=6, top=1, right=9, bottom=1, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=11, top=1, right=11, bottom=1, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=8, top=2, right=8, bottom=2, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=10, top=2, right=10, bottom=3, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=16, top=6, right=20, bottom=6, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=14, top=6, right=14, bottom=6, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=15, top=5, right=15, bottom=5, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=17, top=5, right=20, bottom=5, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=18, top=4, right=18, bottom=4, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=19, top=2, right=20, bottom=4, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=20, top=0, right=20, bottom=1, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=8, top=5, right=8, bottom=5, unk2=0) or MovedAnyUnitInRectangle(unk0=0, unk1=0, left=9, top=6, right=9, bottom=6, unk2=0) or UnknownCondition(magic=310, argument=[0, 0, 5, 7])))
    actions += BattleTalk(entryID=10, unk2=1, unk3=0, unk4=0, unk5=1)
    actions += ShowTutorial(tutorialID=21)
  }
}
```

### BSIC to BSI

TODO. this `bsic` block is just a kotlin code file so it (almost) compiles into a struct


### Usage

#### From source
```
./gradlew run --args="parse -i /data/fe3h/base/84"
```

#### From binary distribution or whatever
```
java -jar bsic.jar parse -i /data/fe3h/base/84
```

### FAQ
#### How open?
https://www.jetbrains.com/help/idea/gradle.html#gradle_import_project_start

#### Why Kotlin?
fedatamine is already in kotlin
