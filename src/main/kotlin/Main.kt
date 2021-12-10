import java.util.Scanner

fun main() {
    val input: (Int) -> String = { n -> "src/main/kotlin/input$n.txt" }

    Scanner(System.`in`).use {
        while (true) {
            when (it.next()) {
                "s" -> when (it.nextInt()) {
                    1 -> Day1().apply { println("${partOne(input(1))}\n${partTwo(input(1))}") }
                    2 -> Day2().apply { println("${partOne(input(2))}\n${partTwo(input(2))}") }
                    3 -> Day3().apply { println("${partOne(input(3))}\n${partTwo(input(3))}") }
                    4 -> Day4().apply { println("${partOne(input(4))}\n${partTwo(input(4))}") }
                    5 -> Day5().apply { println("${partOne(input(5))}\n${partTwo(input(5))}") }
                    6 -> Day6().apply { println("${partOne(input(6))}\n${partTwo(input(6))}") }
                    7 -> Day7().apply { println("${partOne(input(7))}\n${partTwo(input(7))}") }
                    8 -> Day8().apply { println("${partOne(input(8))}\n${partTwo(input(8))}") }
                    9 -> Day9().apply { println("${partOne(input(9))}\n${partTwo(input(9))}") }
                    else -> break
                }
                else -> break
            }
        }
    }
}
