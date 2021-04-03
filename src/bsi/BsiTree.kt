package bsi

import bsi.blocks.BsiCondition
import utils.times

/**
 * Represents a list of flat conditions as a condition tree
 */
sealed class BsiTree {
  var parent: BsiTree? = null

  data class Leaf(val item: BsiCondition) : BsiTree()
  data class Tree(val item: BsiCondition, val children: List<BsiTree>) : BsiTree(), Iterable<BsiTree> {
    override fun iterator() = iterator {
      yieldAll(children)
    }
  }

  data class TList(val items: List<BsiTree>) : BsiTree(), Iterable<BsiTree> {
    override fun iterator(): Iterator<BsiTree> = items.iterator()
  }

  fun flatten(): Sequence<BsiCondition> = sequence {
    when (val tree = this@BsiTree) {
      is Leaf -> yield(tree.item)
      is Tree -> {
        yield(tree.item)
        yieldAll(sequence {
          for (item in tree.children) yieldAll(item.flatten())
        })
      }
      is TList -> yieldAll(sequence {
        for (item in tree.items) yieldAll(item.flatten())
      })
    }
  }

  fun simplify(): BsiTree = when {
    this is Tree && children.size == 1 -> children[0].simplify() // implied
    this is Tree -> {
      val item = this.item
      val newItems = buildList {
        for (child in this@BsiTree.children) {
          if (child is Tree && child.item is BsiCondition.AndContainer) { // merge & > &
            addAll(child.children.map { it.simplify() })
          } else add(child.simplify())
        }
      }
      Tree(item, newItems)
    }
    this is TList ->
      if (items.size == 1) {
        val item = items[0]
        if (item is Tree && item.children.isEmpty()) TList(listOf())
        else items[0].simplify()
      } else TList(items.map { it.simplify() })
    else -> this
  }

  fun isEmpty(): Boolean = when(this) {
    is Leaf -> false
    is Tree -> this.children.isEmpty()
    is TList -> this.items.isEmpty()
  }

  inline fun <reified T> isLeafOrGroup() = (this is Leaf && this.item is T) || this !is Leaf
}


fun List<BsiCondition>.parseToTree(): BsiTree.TList {
  val deque = ArrayDeque(this)
  fun parseTree(): BsiTree = when (val head = deque.removeFirst()) {
    is BsiCondition.HasChildren -> BsiTree.Tree(head, buildList<BsiTree> {
      head.children.times { add(parseTree()) }
    }).also { it.children.forEach { c -> c.parent = it } }
    else -> BsiTree.Leaf(head)
  }
  return BsiTree.TList(buildList {
    while (deque.isNotEmpty()) add(parseTree())
  })
}
