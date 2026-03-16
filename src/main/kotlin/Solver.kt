package com.sybsuper

typealias Track = Pair<Int, Int>

class Solver(val problem: Problem) {
    var steps = 0
        private set
    val queue = LookupQueue<Track>()
    val visitedTracks = mutableSetOf<Track>()

    /**
     * Deltas are the changes in the load of the train at each station compared to the previous step.
     * These are processed when the node is first in the queue.
     */
    val deltas = mutableMapOf<Int, Set<Int>>()

    /**
     * Current solution state
     */
    val solution = mutableMapOf<Int, Set<Int>>()

    fun solve(): MutableMap<Int, Set<Int>> = with(problem) {
        queue.add(-1 to startingStation)
        while (!queue.isEmpty()) {
            step()
        }
        return solution
    }

    private fun step(): Unit = with(problem) {
        steps++
        val track = queue.pop() ?: return
        visitedTracks.add(track)
        val current = track.second
        val currentSolution = solution[current] ?: emptySet()
        val station = stations[current] ?: return
        // if we already have changes stored for this station from previous steps,
        // then we add new changes to them, otherwise we just use the new changes
        val delta = deltas[current] ?: emptySet()

        // currently known incoming load at this station
        val incoming = currentSolution + delta

        // the train going out of the station has a load equal to the
        // incoming load, after unloading and then loading at the station
        val outgoing = incoming - station.first + station.second
        // compute change in the outgoing load of the current node
        val deltaOutgoing = outgoing - currentSolution
        solution[current] = currentSolution + incoming

        val neighbours = tracks[current] ?: return
        if (deltaOutgoing.isEmpty()) {
            // make sure each track is visited at least once even with an empty loaded train
            for (neighbour in neighbours) {
                val track = current to neighbour
                if (track in visitedTracks) continue
                queue.add(track)
            }
            return
        }
        for (neighbour in neighbours) {
            // only queue neighbour if their delta value changes
            if (deltas[neighbour].orEmpty().containsAll(deltaOutgoing)) continue
            deltas[neighbour] = (deltas[neighbour] ?: emptySet()) + deltaOutgoing
            queue.add(current to neighbour)
        }
    }
}
