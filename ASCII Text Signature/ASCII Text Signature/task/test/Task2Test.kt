
import org.hyperskill.hstest.testcase.CheckResult


class Task2Test : GeneratorTest<InputClue>() {

    override fun generate() = listOf(
            inputCase("Albert Einstein"),
            inputCase("Nikola Tesla"),
            inputCase("XXXX yyyyyyyyyy", true),
            inputCase("Ivan Ivanov", true),
            inputCase("a a", true)
    )

    private inline fun checkBadgeBorder(badge: String, onFailure: (msg: String) -> Unit) {
        val lines = badge.split("\n")

        if (lines.map { it.length }.toSet().size != 1) {
            onFailure("Your border is not rectangular.")
            return
        }
        if (lines.size < 2) {
            onFailure("Your border is not rectangular.")
            return
        }
        if (lines.first().any { it != '*' } || lines.last() != lines.first()) {
            onFailure("Your top and bottom edges don't consist of '*'.")
            return
        }
        if (!lines.drop(1).dropLast(1).all { it.startsWith("* ") && it.endsWith(" *") }) {
            onFailure("Your left and right edges don't consist of '*' with one space padding.")
            return
        }
    }


    override fun check(reply: String, clue: InputClue): CheckResult {
        val badgeStart = reply.indexOf('*')
        if (badgeStart == -1) {
            return CheckResult.wrong("Your output doesn't contain a badge, wrapped in '*' symbols.")
        }

        val userBadge = reply
                .substring(badgeStart)
                .trim('\n', ' ')

        checkBadgeBorder(userBadge) { errorMessage ->
            return CheckResult.wrong(errorMessage)
        }


        val name = clue.input.trim().split(' ').filter { it != "" }.joinToString(" ")

        val badge = "*".repeat(name.length + 4) + "\n" +
                "* $name *\n" +
                "*".repeat(name.length + 4)


        if (badge != userBadge) {
            return clue.toFailure("Wrong output for the input line \"${clue.input}\".")
        } else {
            return CheckResult.correct()
        }
    }
}
