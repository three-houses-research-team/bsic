package writers

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import strings.MapStrings
import strings.MapStringsParser
import utils.leBuffer
import utils.resourceBytes
import utils.useTemp

class MapStringWriter : StringSpec({
  "should return golden file on round trip" { // this fails but ONLY because KT is stupid
    val goldenBytes = resourceBytes("/golden/text_v_10652")
    val goldenBuffer = goldenBytes.leBuffer()

    val textV = MapStringsParser(goldenBuffer)

    useTemp { MapStrings.write(it, textV) } shouldBe goldenBytes
  }
})
