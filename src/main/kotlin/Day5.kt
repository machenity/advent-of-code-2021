import java.io.File
import kotlin.math.abs
import kotlin.math.max

class Day5 {
    fun partOne(filePath: String): Int = File(filePath)
        .useLines { lines ->
            val maps = parse(lines)
                .filter { it.isHorizontal() || it.isVertical() }
                .toList()

            val (xMax, yMax) = getMaxCoordinates(maps)

            maps.fold((0..yMax).map { (0..xMax).map { 0 }.toMutableList() }.toMutableList()) { masked, line ->
                when {
                    line.isHorizontal() -> (if (line.x1 < line.x2) line.x1..line.x2 else line.x2..line.x1).forEach {
                        masked[line.y1][it] = masked[line.y1][it].inc()
                    }.let { masked }

                    line.isVertical() -> (if (line.y1 < line.y2) line.y1..line.y2 else line.y2..line.y1).forEach {
                        masked[it][line.x1] = masked[it][line.x1].inc()
                    }.let { masked }

                    else -> masked
                }
            }
                .flatten()
                .count { it >= 2 }
        }

    fun partTwo(filePath: String): Int = File(filePath)
        .useLines { lines ->
            val maps = parse(lines)
                .filter { it.isHorizontal() || it.isVertical() || it.isDiagonal() }
                .map { println(it); it }
                .toList()

            val (xMax, yMax) = getMaxCoordinates(maps)

            maps.fold((0..yMax).map { (0..xMax).map { 0 }.toMutableList() }.toMutableList()) { masked, line ->
                when {
                    line.isHorizontal() -> (rangeIgnoreDirection(line.x1, line.x2)).forEach {
                        masked[line.y1][it] = masked[line.y1][it].inc()
                    }.let { masked }

                    line.isVertical() -> (rangeIgnoreDirection(line.y1, line.y2)).forEach {
                        masked[it][line.x1] = masked[it][line.x1].inc()
                    }.let { masked }

                    line.isDiagonal() -> rangeIgnoreDirection(line.y1, line.y2)
                        .zip(rangeIgnoreDirection(line.x1, line.x2))
                        .forEach { (y, x) -> masked[y][x] = masked[y][x].inc() }
                        .let { masked }

                    else -> masked
                }
            }
                .flatten()
                .count { it >= 2 }
        }

    private fun parse(input: Sequence<String>): Sequence<Line> {
        val format = "(\\d+),(\\d+) -> (\\d+),(\\d+)".toRegex()

        return input.filter { it.isNotEmpty() }
            .map {
                format.matchEntire(it)!!
                    .destructured
                    .let { (x1, y1, x2, y2) -> Line(x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt()) }
            }
    }

    private fun getMaxCoordinates(maps: List<Line>): Pair<Int, Int> = maps.reduce { acc, line ->
        Line(
            x1 = max(acc.x1, line.x1),
            y1 = max(acc.y1, line.y1),
            x2 = max(acc.x2, line.x2),
            y2 = max(acc.y2, line.y2)
        )
    }
        .let { max(it.x1, it.x2) to max(it.y1, it.y2) }

    private fun rangeIgnoreDirection(first: Int, last: Int): IntProgression =
        when (first < last) {
            true -> first..last
            false -> first downTo last
        }

    data class Line(
        val x1: Int,
        val y1: Int,
        val x2: Int,
        val y2: Int
    ) {
        fun isHorizontal(): Boolean = y1 == y2
        fun isVertical(): Boolean = x1 == x2
        fun isDiagonal(): Boolean = abs(x1 - x2) == abs(y1 - y2)

        override fun toString(): String {
            return "Line(($x1, $y1) -> ($x2, $y2))"
        }
    }
}
