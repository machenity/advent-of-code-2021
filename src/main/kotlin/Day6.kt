import java.io.File

class Day6 {
    fun partOne(filePath: String) {
        val initialTimers = parse(filePath)

    }

    private fun parse(filePath: String): List<Int> = File(filePath)
        .readLines()[0]
        .split(",")
        .map { it.toInt() }
}
