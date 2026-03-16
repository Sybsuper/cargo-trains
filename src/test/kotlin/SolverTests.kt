import com.sybsuper.Problem
import com.sybsuper.Solver
import kotlin.test.Test
import kotlin.test.assertEquals

class SolverTests {
    @Test
    fun `base case`() {
        //           /--> --> --> -->\
        // nodes:    1    -> 2    -> 3
        // changes:  -0+0 -> +1-0 -> +0-0
        // outgoing: {0}  -> {1}  -> {0,1}
        // incoming: {}   -> {0}  -> {0,1}
        val problem = Problem(
            stations = mapOf(
                1 to (0 to 0),
                2 to (0 to 1),
                3 to (0 to 0)
            ),
            tracks = mapOf(
                1 to listOf(2, 3),
                2 to listOf(3)
            ),
            startingStation = 1
        )
        val solver = Solver(problem)
        val solution = solver.solve()
        assertEquals(setOf(), solution[1])
        assertEquals(setOf(0), solution[2])
        assertEquals(setOf(0, 1), solution[3])
    }

    @Test
    fun `three node loop`() {
        val problem = Problem(
            stations = mapOf(
                1 to (0 to 0),
                2 to (2 to 1),
                3 to (1 to 2)
            ),
            tracks = mapOf(
                1 to listOf(2),
                2 to listOf(3),
                3 to listOf(1)
            ),
            startingStation = 1
        )
        val solver = Solver(problem)
        val solution = solver.solve()
        assertEquals(setOf(0, 2), solution[1])
        assertEquals(setOf(0, 2), solution[2])
        assertEquals(setOf(0, 1), solution[3])
    }

    @Test
    fun `one node loop`() {
        val problem = Problem(
            stations = mapOf(
                1 to (0 to 0)
            ),
            tracks = mapOf(
                1 to listOf(1)
            ),
            startingStation = 1
        )
        val solver = Solver(problem)
        val solution = solver.solve()
        assertEquals(setOf(0), solution[1])
    }

    @Test
    fun `two node loop with opposite load and unload`() {
        val problem = Problem(
            stations = mapOf(
                1 to (0 to 1),
                2 to (1 to 0),
            ),
            tracks = mapOf(
                1 to listOf(2),
                2 to listOf(1)
            ),
            startingStation = 1
        )
        val solver = Solver(problem)
        val solution = solver.solve()
        assertEquals(setOf(0), solution[1])
        assertEquals(setOf(1), solution[2])
    }
}
