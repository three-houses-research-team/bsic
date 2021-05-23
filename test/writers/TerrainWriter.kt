package writers

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import terrain.TerrainData
import utils.leBuffer
import utils.resourceBytes
import utils.useTemp

class TerrainWriter : StringSpec({
  "should return golden file on round trip" {
    val goldenBytes = resourceBytes("/golden/terrain_366")
    val goldenBuffer = goldenBytes.leBuffer()

    val terrain = TerrainData(goldenBuffer)

    useTemp { TerrainData.write(it, terrain) } shouldBe goldenBytes
  }
})
