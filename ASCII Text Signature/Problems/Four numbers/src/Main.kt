import java.util.*
import kotlin.collections.ArrayList

fun main() {
    val scanner = Scanner(System.`in`)
    val integers = ArrayList<Int>()

    for (i in 1..4) integers.add(scanner.nextInt())

    for (integer in integers) println(integer)
}