import java.util.*
import kotlin.collections.ArrayList

fun main() {
    val scanner = Scanner(System.`in`)
    val words = ArrayList<String>()
    for (i in 1..5) words.add(scanner.next())
    for (word in words) println(word)
}