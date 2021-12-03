import java.io.File

class Day1 {
    fun partOne(filePath: String): Int =
        File(filePath).useLines { line ->
            line.map { it.toInt() }
                .zipWithNext()
                .filter { it.first < it.second }
                .count()
        }

    fun partTwo(filePath: String): Int =
        File(filePath).useLines { line ->
            line.map { it.toInt() }
                .windowed(3)
                .map { it.sum() }
                .zipWithNext()
                .filter { it.first < it.second }
                .count()
        }
}
