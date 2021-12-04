fun main() {
    val input1 = "src/main/kotlin/input.txt"
    val input2 = "src/main/kotlin/input2.txt"
    val input3 = "src/main/kotlin/input3.txt"
    val input4 = "src/main/kotlin/input4.txt"

    val day1 = Day1()
    println(day1.partOne(input1))
    println(day1.partTwo(input1))

    val day2 = Day2()
    println(day2.partOne(input2))
    println(day2.partTwo(input2))

    val day3 = Day3()
    println(day3.partOne(input3))
    println(day3.partTwo(input3))

    val day4 = Day4()
    println(day4.partOne(input4))
    println(day4.partTwo(input4))
}
