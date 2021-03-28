fun main() {
    val words = ArrayList<String>()

    for (i in 1..5) words.add(readLine()!!)

    println(words.joinToString(" "))
}