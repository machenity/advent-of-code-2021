import Day2.Command.DOWN
import Day2.Command.FORWARD
import Day2.Command.UP
import java.io.File

class Day2 {
    fun partOne(filePath: String): Int =
        File(filePath).useLines { lines ->
            lines.map { it.split(" ") }
                .map { Command.valueOf(it[0].uppercase()) to it[1].toInt() }
                .fold(0 to 0) { (horizontal, depth), (command, value) ->
                    when (command) {
                        FORWARD -> horizontal + value to depth
                        UP -> horizontal to depth - value
                        DOWN -> horizontal to depth + value
                    }
                }
                .let { it.first * it.second }
        }

    fun partTwo(filePath: String): Int =
        File(filePath).useLines { lines ->
            lines.map { it.split(" ") }
                .map { Command.valueOf(it[0].uppercase()) to it[1].toInt() }
                .fold(Triple(0, 0, 0)) { (horizontal, depth, aim), (command, value) ->
                    when (command) {
                        FORWARD -> Triple(horizontal + value, depth + aim * value, aim)
                        UP -> Triple(horizontal, depth, aim - value)
                        DOWN -> Triple(horizontal, depth, aim + value)
                    }
                }
                .let { it.first * it.second }
        }

    private enum class Command {
        FORWARD,
        UP,
        DOWN
    }
}
