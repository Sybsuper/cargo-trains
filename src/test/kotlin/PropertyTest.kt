import com.sybsuper.Problem
import com.sybsuper.Solver
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertContains

class PropertyTest {
    @Test
    fun `property tests`() {
        val rng = Random(42)
        var asserts = 0
        val loops = 10000
        repeat(loops) {
            val problem = randomGraph(rng)
            val solver = Solver(problem)
            val solution = solver.solve()
            val assertVerified = verifySolution(problem, solution, rng)
            asserts += assertVerified
        }
        val requiredAsserts = 100
        assert(asserts > requiredAsserts) { "At least $requiredAsserts asserts required, only $asserts performed." }
    }

    private fun verifySolution(problem: Problem, solution: MutableMap<Int, Set<Int>>, rng: Random): Int {
        // pick some random nodes in the problem,
        // check where their load goes and verify if that path is valid, according to the solution
        // this kind of checks assignment to deletion paths
        val reachableNodesFromStart = mutableSetOf<Int>()
        fun dfs(node: Int) {
            if (!reachableNodesFromStart.add(node)) return
            for (neighbour in problem.tracks[node] ?: return) {
                dfs(neighbour)
            }
        }
        dfs(problem.startingStation)
        var asserts = 0
        repeat(20) {
            asserts += validateNode(reachableNodesFromStart, rng, problem, solution)
        }
        return asserts
    }

    private fun validateNode(
        reachableNodesFromStart: MutableSet<Int>,
        rng: Random,
        problem: Problem,
        solution: MutableMap<Int, Set<Int>>
    ): Int {
        var asserts = 0
        val seenNodes = mutableSetOf<Int>()
        val stationId = reachableNodesFromStart.random(rng)
        val station = problem.stations[stationId]!!
        val load = station.second
        run {
            var current = (problem.tracks[stationId] ?: return@run).random(rng)
            while (seenNodes.add(current)) {
                val stationInfo = problem.stations[current] ?: return@run
                val unload = stationInfo.first

                assertContains(
                    solution[current].orEmpty(),
                    load,
                    "$load should be contained in station $stationId in solution, reachable from $stationId"
                )
                asserts++

                current = (problem.tracks[stationId] ?: return@run).random(rng)

                if (unload == load) {
                    // if the load is unloaded at this station, then we are done
                    break
                }
            }
        }
        return asserts
    }

    private fun randomGraph(rng: Random): Problem {
        val stationCount = (5..20).random(rng)
        val loadTypes = (5..30).random(rng)
        val trackCount = (stationCount..stationCount * 3).random(rng)
        val stations = (1..stationCount).associateWith { (1..loadTypes).random(rng) to (1..loadTypes).random(rng) }
        val tracks =
            (1..trackCount).map { (1..stationCount).random(rng) to (1..stationCount).random(rng) }
                .distinct()
                .groupBy { it.first }
                .mapValues { it.value.map { it.second } }
        val startingStation = (1..stationCount).random(rng)
        return Problem(stations, tracks, startingStation)
    }
}