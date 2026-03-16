package com.sybsuper

fun readInts(n: Int) = readln().split(' ', limit = n).map { it.toInt() }

fun readProblem(): Problem {
    val (s, t) = readInts(2)
    val stations = (1..s).associate {
        val (id, unload, load) = readInts(3)
        id to (unload to load)
    }
    val tracks = (1..t).map {
        val (from, to) = readInts(2)
        from to to
    }.groupBy { it.first }.mapValues { it.value.map { it.second } }
    val startingStation = readln().toInt()
    val problem = Problem(stations, tracks, startingStation)
    return problem
}