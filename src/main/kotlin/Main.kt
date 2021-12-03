fun main(args: Array<String>) {
    val input1 = "src/main/kotlin/input.txt"
    val input2 = "src/main/kotlin/input2.txt"

    val day1 = Day1()
    print(day1.partOne(input1))
    print(day1.partTwo(input1))

    val day2 = Day2()
    print(day2.partOne(input2))
    print(day2.partTwo(input2))
}
