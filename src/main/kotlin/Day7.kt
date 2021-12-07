import java.io.File
import kotlin.math.abs

class Day7 {
    fun partOne(filePath: String): Int {
        val positions = parse(filePath)
        val maxPosition = positions.maxOrNull()!!
        val minPosition = positions.minOrNull()!!

        return (minPosition..maxPosition).minOf { costConstant(it, positions) }
    }

    fun partTwo(filePath: String): Int {
        val positions = parse(filePath)
        val maxPosition = positions.maxOrNull()!!
        val minPosition = positions.minOrNull()!!

        return (minPosition..maxPosition).minOf { costChanging(it, positions) }
    }

    private fun parse(filePath: String): List<Int> =
        File(filePath).readLines()[0]
            .split(",")
            .map { it.toInt() }

    private fun costConstant(destination: Int, positions: List<Int>): Int =
        positions.sumOf { abs(destination - it) }

    private fun costChanging(destination: Int, positions: List<Int>): Int {
        val cost: (Int) -> Int = { d -> d * (d + 1) / 2 }
        return positions.sumOf { cost(abs(destination - it)) }
    }

}