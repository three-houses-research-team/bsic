package convert

import bsi.BsiTree
import bsi.blocks.BsiCondition
import bsic.BsicNode

fun List<BsiTree>.toBsic() = map { it.toBsic() }

fun BsiTree.toBsic(): BsicNode = when(this) {
  is BsiTree.Leaf -> BsicNode.Wraps(item)
  is BsiTree.Tree -> when(item) {
    is BsiCondition.AndContainer -> BsicNode.And(children.toBsic())
    is BsiCondition.OrContainer -> BsicNode.Or(children.toBsic())
    else -> TODO()
  }
  is BsiTree.TList -> BsicNode.And(items.toBsic())
}
