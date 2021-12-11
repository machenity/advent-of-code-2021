import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day6Test {
    val sut = Day6()

    @Test
    fun partOne() {
        val testFilePath = "src/test/kotlin/input6_test.txt"
        val actual = sut.partOne(testFilePath)
        assertEquals(actual, 5934)
    }
}
