import java.io.File

class Day8 {
    fun partOne(filePath: String): Int = File(filePath).useLines { lines ->
        lines.map { line -> line.split("|", " ") }
            .flatMap { it.takeLast(4) }
            .count { it.length in setOf(2, 3, 4, 7) }
    }

    fun partTwo(filePath: String): Int = File(filePath).useLines { lines ->
        lines.map { line -> guessSegmentMapping(parse(line)) }
            .sum()
    }

    private fun parse(line: String): Pair<List<String>, List<String>> =
        line.split("|")
            .let {
                Pair(
                    it[0].split(" ").filter { output -> output.isNotEmpty() },
                    it[1].split(" ").filter { output -> output.isNotEmpty() }
                )
            }

    private fun guessSegmentMapping(patterns: Pair<List<String>, List<String>>): Int {
        val one = patterns.first.find { it.length == 2 }!!
        val four = patterns.first.find { it.length == 4 }!!

        return patterns.second.map {
            when (it.length) {
                2 -> "1"
                3 -> "7"
                4 -> "4"
                7 -> "8"
                6 -> if (it.toSet().containsAll(four.toSet())) {
                    "9"
                } else if (it.toSet().containsAll(one.toSet())) {
                    "0"
                } else {
                    "6"
                }
                5 -> if (it.toSet().containsAll(one.toSet())) {
                    "3"
                } else if ((four.toSet() - it.toSet()).count() == 1) {
                    "5"
                } else {
                    "2"
                }
                else -> ""
            }
        }
            .joinToString("")
            .toInt()
    }
}
