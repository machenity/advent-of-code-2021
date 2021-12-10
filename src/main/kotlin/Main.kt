import java.util.*

fun main() {
    val input1 = "src/main/kotlin/input.txt"
    val input2 = "src/main/kotlin/input2.txt"
    val input3 = "src/main/kotlin/input3.txt"
    val input4 = "src/main/kotlin/input4.txt"
    val input5 = "src/main/kotlin/input5.txt"
    val input6 = "src/main/kotlin/input6.txt"
    val input7 = "src/main/kotlin/input7.txt"

    Scanner(System.`in`).use {
        while (true) {
            when(it.next()) {
                "s" -> when (it.nextInt()) {
                    1 -> Day1().apply { println("${partOne(input1)}\n${partTwo(input1)}") }
                    2 -> Day2().apply { println("${partOne(input2)}\n${partTwo(input2)}") }
                    3 -> Day3().apply { println("${partOne(input3)}\n${partTwo(input3)}") }
                    4 -> Day4().apply { println("${partOne(input4)}\n${partTwo(input4)}") }
                    5 -> Day5().apply { println("${partOne(input5)}\n${partTwo(input5)}") }
                    6 -> Day6().apply { println("${partOne(input6)}\n${partTwo(input6)}") }
                    7 -> Day7().apply { println("${partOne(input7)}\n${partTwo(input7)}") }
                    else -> break
                }
                else -> break
            }
        }
    }
}
