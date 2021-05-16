package writers

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import strings.TextSFile
import utils.leBuffer
import utils.resourceBytes
import utils.useTemp

class TextSWriter : StringSpec({
  "should return golden file on round trip" {
    val goldenBytes = resourceBytes("/golden/text_s_10754")
    val goldenBuffer = goldenBytes.leBuffer()

    val textS = TextSFile(goldenBuffer)

    useTemp { TextSFile.write(it, textS.strings) } shouldBe goldenBytes
  }
})
