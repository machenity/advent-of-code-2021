import java.io.File

class Day6 {
    fun partOne(filePath: String): Long {
        val initialTimers = parse(filePath)
        val t0 = (0..8).associateWith { 0L }
        val t = initialTimers.fold(t0) { acc, t -> acc + (t to acc[t]!! + 1) }

        return generateSequence(t, ::step).elementAt(80).values.sum()
    }

    fun partTwo(filePath: String): Long {
        val initialTimers = parse(filePath)
        val t0 = (0..8).associateWith { 0L }
        val t = initialTimers.fold(t0) { acc, t -> acc + (t to acc[t]!! + 1) }

        return generateSequence(t, ::step).elementAt(256).values.sum()
    }

    private fun step(t: Map<Int,Long>): Map<Int,Long> {
        val zeros = t[0]!!
        return ((t - 0).mapKeys { (k, _) -> k - 1 } + (8 to zeros))
            .let { it + (6 to it[6]!! + zeros) }
    }

    private fun parse(filePath: String): List<Int> = File(filePath)
        .readLines()[0]
        .split(",")
        .map { it.toInt() }
}
