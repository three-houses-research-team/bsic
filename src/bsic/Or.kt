package bsic

import bsi.blocks.BsiCondition

// helper functions to compose conditions with or
infix fun BsicNode.or(other: BsicNode) = BsicNode.Or(listOf(this, other))
infix fun BsiCondition.or(other: BsicNode) = BsicNode.Or(listOf(this.bsic, other))
infix fun BsiCondition.or(other: BsiCondition) = BsicNode.Or(listOf(this.bsic, other.bsic))
infix fun BsicNode.or(other: BsiCondition) = BsicNode.Or(listOf(this, other.bsic))
fun or(vararg items: BsicNode) = BsicNode.Or(items.toList())
fun or(vararg items: BsiCondition) = BsicNode.Or(items.map { it.bsic })
