import java.io.File

typealias Grid = List<List<Int>>
typealias Location = Pair<Int, Int>

class Day11 {
    fun partOne(filePath: String): Int =
        generateSequence(0 to parse(filePath)) { (count, grid) -> step(grid).let { count + countZeros(it) to it } }
            .elementAt(100)
            .first

    fun partTwo(filePath: String): Int =
        generateSequence(parse(filePath), ::step).indexOfFirst { countZeros(it) == 100 }

    private fun parse(filePath: String) =
        File(filePath).readLines().map { line -> line.toList().map { it.digitToInt() } }

    private fun countZeros(grid: Grid): Int = traverseGrid().count { (x, y) -> grid[y][x] == 0 }

    private fun step(grid: Grid): Grid {
        val target = grid.map { it.toMutableList() }.toMutableList()
        val locations = traverseGrid()

        // update all octopuses
        locations.forEach { (x, y) -> target[y][x] = target[y][x] + 1 }

        // flash
        flash(locations.filter { (x, y) -> target[y][x] == 10 }, setOf(), target)

        // turn flashed to zero
        locations.forEach { (x, y) -> if (target[y][x] >= 10) target[y][x] = 0 }

        return target.map { it.toList() }.toList()
    }

    private fun flash(tens: List<Location>, flashed: Set<Location>, grid: MutableList<MutableList<Int>>) {
        val readyToFlash = tens.toSet() - flashed
        if (readyToFlash.isEmpty()) return

        readyToFlash.forEach {
            getAdjacentOctopuses(it).forEach { (x, y) -> grid[y][x] = grid[y][x] + 1 }
        }

        flash(traverseGrid().filter { (x, y) -> grid[y][x] >= 10 }, flashed + tens, grid)
    }

    private fun getAdjacentOctopuses(location: Location): Set<Location> {
        val (x, y) = location
        return (-1..1).flatMap { i -> (-1..1).map { i to it } }
            .map { (i, j) -> x + i to y + j }
            .filter { (i, j) -> i in (0..9) && j in (0..9) }
            .toSet()
    }

    private fun traverseGrid(): List<Location> = (0..9).flatMap { x -> (0..9).map { x to it } }
}