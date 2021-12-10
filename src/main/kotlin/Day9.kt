import java.io.File

typealias Point = Pair<Int, Int>

class Day9 {
    val allPointsInMap: (Int, Int) -> Sequence<Point> =
        { w, h -> (0 until w).flatMap { x -> (0 until h).map { y -> x to y } }.asSequence() }

    fun partOne(filepath: String): Int {
        val (maps, widthOfMap, heightOfMap) = parse(File(filepath).readLines())

        return allPointsInMap(widthOfMap, heightOfMap)
            .sumOf { (x, y) -> if (isLowest(x to y, maps)) maps[y][x] + 1 else 0 }
    }

    fun partTwo(filepath: String): Int {
        val (maps, widthOfMap, heightOfMap) = parse(File(filepath).readLines())

        return allPointsInMap(widthOfMap, heightOfMap)
            .filter { isLowest(it, maps) }
            .map { findBasin(setOf(it), setOf(), maps, widthOfMap, heightOfMap).size }
            .sortedDescending()
            .take(3)
            .reduce { acc, size -> acc * size }
    }

    private fun parse(lines: List<String>): Triple<List<List<Int>>, Int, Int> =
        Triple(
            lines.map { line -> line.toList().map { it.digitToInt() } },
            lines[0].length,
            lines.size
        )

    private fun isLowest(location: Pair<Int, Int>, map: List<List<Int>>): Boolean {
        val widthOfMap = map[0].size
        val heightOfMap = map.size

        val (x, y) = location

        return (x == 0 || map[y][x] < map[y][x - 1])
            && (x == widthOfMap - 1 || map[y][x] < map[y][x + 1])
            && (y == 0 || map[y][x] < map[y - 1][x])
            && (y == heightOfMap - 1 || map[y][x] < map[y + 1][x])
    }

    private fun getAdjacentPoints(p: Point, xMax: Int, yMax: Int): Set<Point> {
        val (x, y) = p
        return setOf(x - 1 to y, x + 1 to y, x to y - 1, x to y + 1)
            .filter { (nx, ny) -> nx in 0 until xMax && ny in 0 until yMax }
            .toSet()
    }

    private tailrec fun findBasin(
        targets: Set<Point>,
        found: Set<Point>,
        maps: List<List<Int>>,
        xMax: Int,
        yMax: Int
    ): Set<Point> {
        val newTargets = (targets.flatMap { getAdjacentPoints(it, xMax, yMax) }.toSet() - found)
            .filter { (x, y) -> maps[y][x] != 9 }
            .toSet()

        if (newTargets.isEmpty()) {
            return targets + found
        }
        return findBasin(newTargets, targets + found, maps, xMax, yMax)
    }
}
