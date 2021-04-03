package bsic

import bsi.blocks.BsiCondition

sealed class BsicNode {
  class Wraps(val condition: BsiCondition) : BsicNode()
  class And(val items: List<BsicNode>) : BsicNode()
  class Or(val items: List<BsicNode>) : BsicNode()
}
