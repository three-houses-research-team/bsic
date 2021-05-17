package writers

import bai.BAIWriter
import bai.BaiFile
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import utils.Open010
import utils.leBuffer
import utils.resourceBytes
import utils.useTemp
import java.io.File

private fun testBai(index: Int) {
  val goldenBytes = resourceBytes("/golden/bai_$index")
  val goldenBuffer = goldenBytes.leBuffer()

  val bai = BaiFile(goldenBuffer)
  val list = bai.blocks.flatten()

  useTemp {
    println("Compare with 010editor ${Open010.compare(it, File("testRes/golden/bai_$index"))}")
    BAIWriter.write(it, list) } shouldBe goldenBytes
}

class BaiWriterTest : StringSpec({
  "should return golden file on round trip (83)" {
    testBai(83)
  }
  "should return golden file on round trip (85)" {
    testBai(85)
  }
  "should return golden file on round trip (87)" {
    // this test fails because of two unknown u4s at 0x1148
    testBai(87)
  }
})
