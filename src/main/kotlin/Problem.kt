package com.sybsuper

data class Problem(
    /**
     * Set of all stations with their unload and load values.
     */
    val stations: Map<Int, Pair<Int, Int>>,
    /**
     * For each station, a list of neighbours where it can go next.
     */
    val tracks: Map<Int, List<Int>>,
    /**
     * The station where the train starts with an empty load.
     */
    val startingStation: Int
)
