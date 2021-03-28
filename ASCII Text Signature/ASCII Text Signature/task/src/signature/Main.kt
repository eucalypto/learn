package signature

fun main() {
    val name = readLine()!!

    val tagGenerator = NameTagGenerator(name)

    println(tagGenerator.getTag())
}


