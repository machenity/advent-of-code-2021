import java.io.File
import java.util.Stack

class Day10 {
    fun partOne(filePath: String): Int =
        File(filePath).useLines { lines -> lines.map { check(it.toList()) }.sum() }

    fun partTwo(filePath: String): Long =
        File(filePath).useLines { lines ->
            lines.map { cleanClosedPairs(it.toList()) }
                .filter { it.isNotEmpty() }
                .map { autoComplete(it) }
                .map { it.fold(0L) { acc, c -> acc * 5 + getScoreForAutoComplete(c) } }
                .toList()
        }
            .sorted()
            .let { it[it.size / 2] }

    private fun check(line: List<Char>): Int {
        val stack = Stack<Char>()
        for (c in line) {
            if (c in "({[<".toSet()) {
                stack.push(c)
            } else if (stack.peek().isOpeningPairOf(c)) {
                stack.pop()
            } else {
                return getScoreForCheck(c)
            }
        }
        return 0
    }

    private fun cleanClosedPairs(line: List<Char>): List<Char> {
        val stack = mutableListOf<Char>()
        for (c in line) {
            if (c in "({[<".toSet()) {
                stack.add(c)
            } else if (stack.last().isOpeningPairOf(c)) {
                stack.removeLast()
            } else {
                return emptyList()
            }
        }
        return stack.toList()
    }

    private fun autoComplete(remains: List<Char>): List<Char> =
        remains.foldRight("") { c, acc ->
            when (c) {
                '(' -> "$acc)"
                '{' -> "$acc}"
                '[' -> "$acc]"
                '<' -> "$acc>"
                else -> throw IllegalCallerException()
            }
        }.toList()

    private fun getScoreForCheck(c: Char) =
        when (c) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> 0
        }

    private fun getScoreForAutoComplete(c: Char) =
        when (c) {
            ')' -> 1
            ']' -> 2
            '}' -> 3
            '>' -> 4
            else -> 0
        }
}

fun Char.isOpeningPairOf(c: Char): Boolean =
    when (this) {
        '(' -> c == ')'
        '{' -> c == '}'
        '[' -> c == ']'
        '<' -> c == '>'
        else -> throw IllegalCallerException()
    }
