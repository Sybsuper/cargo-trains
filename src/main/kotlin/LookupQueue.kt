package com.sybsuper

/**
 * Lookup queue with O(1) contains, add and pop.
 */
class LookupQueue<T> {
    private val queue = ArrayDeque<T>()
    private val inQueue = HashSet<T>()

    fun add(e: T): Boolean {
        if (!inQueue.add(e)) {
            return false
        }
        return queue.add(e)
    }

    fun pop(): T? {
        return queue.removeFirstOrNull()?.also { inQueue.remove(it) }
    }

    fun isEmpty(): Boolean = queue.isEmpty()

    operator fun contains(element: T): Boolean = inQueue.contains(element)
}