package com.sybsuper

fun main() {
    val problem = readProblem()
    val solver = Solver(problem)
    val solution = solver.solve()
    solution.map { (station, load) ->
        "$station ${load.joinToString(" ")}"
    }.forEach(::println)
    println("Steps: ${solver.steps}")
}