import java.io.File

class Day3 {
    fun partOne(filePath: String): Int {
        val gammaBuilder = StringBuilder()
        val epsilonBuilder = StringBuilder()

        File(filePath).useLines { lines ->
            lines.map { it.asIterable().map { bit -> bit.digitToInt() } }
                .fold(listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)) { acc, next ->
                    acc.mapIndexed { index, i ->
                        when (next[index]) {
                            1 -> i + 1
                            0 -> i - 1
                            else -> i
                        }
                    }
                }
                .forEach {
                    if (it >= 0) {
                        gammaBuilder.append('1')
                        epsilonBuilder.append('0')
                    } else {
                        gammaBuilder.append('0')
                        epsilonBuilder.append('1')
                    }
                }
        }

        val gamma = gammaBuilder.toString().toInt(2)
        val epsilon = epsilonBuilder.toString().toInt(2)

        return gamma * epsilon
    }

    fun partTwo(filePath: String): Int {
        val oxygenGeneratorRating = File(filePath).useLines { lines ->
            var reports = lines
            for (i in 0..12) {
                val temp = reports.fold(listOf<String>() to listOf<String>()) { (zeros, ones), next ->
                    when (next[i]) {
                        '0' -> (zeros + next) to ones
                        '1' -> zeros to (ones + next)
                        else -> zeros to ones
                    }
                }
                    .run { if (first.size > second.size) first else second }

                reports = temp.asSequence()

                if (temp.size == 1) {
                    break
                }
            }
            reports.joinToString().toInt(2)
        }

        val co2ScrubberRating = File(filePath).useLines { lines ->
            var reports = lines
            for (i in 0..12) {
                val temp = reports.fold(listOf<String>() to listOf<String>()) { (zeros, ones), next ->
                    when (next[i]) {
                        '0' -> (zeros + next) to ones
                        '1' -> zeros to (ones + next)
                        else -> zeros to ones
                    }
                }
                    .run { if (first.size <= second.size) first else second }

                reports = temp.asSequence()

                if (temp.size == 1) {
                    break
                }
            }
            reports.joinToString().toInt(2)
        }

        return oxygenGeneratorRating * co2ScrubberRating
    }
}
