import java.io.File

class Day4 {
    fun partOne(filePath: String): Int {
        val (drawns, boards) = parse(File(filePath).bufferedReader().lineSequence())

        val currentDrawns = mutableSetOf<Int>()
        var lastDrawn: Int = -1
        var bingoBoardIndex: Int = -1
        for (drawn in drawns) {
            currentDrawns.add(drawn)
            val isBingo = boards.foldIndexed(-1 to false) { index, acc, board ->
                when (acc.second) {
                    true -> acc
                    else -> when(checkBingo(board, currentDrawns)) {
                        true -> index to true
                        false -> acc
                    }
                }
            }
            if (isBingo.second) {
                lastDrawn = drawn
                bingoBoardIndex = isBingo.first
                break
            }
        }

        return lastDrawn * (boards[bingoBoardIndex].toSet() - currentDrawns).sum()
    }

    fun partTwo(filePath: String): Int {
        val (drawns, boards) = parse(File(filePath).bufferedReader().lineSequence())
        val isBoardBingos = boards.map { false }.toMutableList()

        val currentDrawns = mutableSetOf<Int>()
        var lastDrawn: Int = -1
        var bingoBoardIndex: Int = -1
        for (drawn in drawns) {
            currentDrawns.add(drawn)
            boards.forEachIndexed { index, board ->
                if (!isBoardBingos[index]) {
                    isBoardBingos[index] = checkBingo(board, currentDrawns)
                        .also { if (it) bingoBoardIndex = index }
                }
            }
            if (isBoardBingos.find { !it } == null) {
                lastDrawn = drawn
                break
            }
        }

        return lastDrawn * (boards[bingoBoardIndex].toSet() - currentDrawns).sum()
    }

    private fun parse(input: Sequence<String>): Pair<List<Int>, List<List<Int>>> {
        val lines = input.iterator()

        val drawns = lines.next().split(",").map { it.toInt() }
        val boards =  lines.asSequence()
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