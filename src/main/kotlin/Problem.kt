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
) {
    fun toMermaidString(): String {
        return buildString {
            appendLine("graph TD")
            stations.keys.sorted().forEach { id ->
                val (unload, load) = stations[id]!!
                appendLine("    $id(\"$id<br>-$unload +$load\")")
            }
            tracks.keys.sorted().forEach { from ->
                tracks[from]?.sorted()?.forEach { to ->
                    appendLine("    $from --> $to")
                }
            }
            appendLine("    style $startingStation fill:#f9f,stroke:#333,stroke-width:4px")
        }
    }

    fun toInputString(): String {
        return buildString {
            val s = stations.size
            val t = tracks.values.sumOf { it.size }
            appendLine("$s $t")
            stations.keys.sorted().forEach { id ->
                val (unload, load) = stations[id]!!
                appendLine("$id $unload $load")
            }
            tracks.keys.sorted().forEach { from ->
                tracks[from]?.sorted()?.forEach { to ->
                    appendLine("$from $to")
                }
            }
            appendLine("$startingStation")
        }
    }
}
