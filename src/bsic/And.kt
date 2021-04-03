package bsic

import bsi.blocks.BsiCondition

// helper functions to compose conditions with and
infix fun BsicNode.and(other: BsicNode) = BsicNode.And(listOf(this, other))
infix fun BsiCondition.and(other: BsicNode) = BsicNode.And(listOf(this.bsic, other))
infix fun BsiCondition.and(other: BsiCondition) = BsicNode.And(listOf(this.bsic, other.bsic))
infix fun BsicNode.and(other: BsiCondition) = BsicNode.And(listOf(this, other.bsic))
fun and(vararg items: BsicNode) = BsicNode.And(items.toList())
fun and(vararg items: BsiCondition) = BsicNode.And(items.map { it.bsic })
