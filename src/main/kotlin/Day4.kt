import java.io.File

class Day4 {
    fun partOne(filePath: String): Int {
        val (drawns, boards) = parse(File(filePath).bufferedReader().lineSequence())

        return drawns.asSequence()
            .runningFold(linkedSetOf<Int>()) { acc, currentDrawn -> acc.add(currentDrawn).let { acc } }
            .map {
                boards.foldIndexed(-1 to false) { index, acc, board ->
                    when (acc.second) {
                        true -> acc
                        else -> when (checkBingo(board, it)) {
                            true -> index to true
                            false -> acc
                        }
                    }
                }.let { (index, isBingo) -> Triple(it, index, isBingo) }
            }
            .find { it.third }!!
            .let { it.first.last() * (boards[it.second].toSet() - it.first).sum() }
    }

    fun partTwo(filePath: String): Int {
        val (drawns, boards) = parse(File(filePath).bufferedReader().lineSequence())
        val isBoardBingos = boards.map { false }.toMutableList()

        return drawns.asSequence()
            .runningFold(linkedSetOf<Int>()) { acc, currentDrawn -> acc.add(currentDrawn).let { acc } }
            .map {
                it to boards.foldIndexed(-1) { index, acc, board ->
                    when (isBoardBingos[index]) {
                        true -> acc
                        false -> checkBingo(board, it)
                            .also { if (it) isBoardBingos[index] = true }
                            .let { if (it) index else acc }
                    }
                }
            }
            .find { _ -> isBoardBingos.all { it } }!!
            .let { it.first.last() * (boards[it.second].toSet() - it.first).sum() }
    }

    private fun parse(input: Sequence<String>): Pair<List<Int>, List<List<Int>>> {
        val lines = input.iterator()

        val drawns = lines.next().split(",").map { it.toInt() }
        val boards = lines.asSequence()
            .filter { it.isNotEmpty() }
            .chunked(5)
            .map { board ->
                board.map { line ->
                    line.split(" ")
                        .filter { it.isNotEmpty() }
                        .map { it.toInt() }
                }
                    .flatten()
            }
            .toList()

        return drawns to boards
    }

    private fun checkBingo(board: List<Int>, drawns: Set<Int>): Boolean {
        // check rows
        val isRowBingo = board.chunked(5)
            .map { drawns.containsAll(it) }
            .reduce { acc, b -> acc or b }

        if (isRowBingo) return true

        // check columns
        (0..4).forEach {
            val column = board.filterIndexed { index, _ -> index.mod(5) == it }
            if (drawns.containsAll(column)) {
                return true
            }
        }

        return false
    }
}
