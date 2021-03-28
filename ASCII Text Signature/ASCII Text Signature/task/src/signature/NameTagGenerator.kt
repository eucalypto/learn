package signature

class NameTagGenerator(val name: String) {

    val nameLength = name.length

    val tag = StringBuilder()

    fun getTag(): String {
        addStarLineToTag()
        addNameLineToTag()
        addStarLineToTag()

        return tag.toString()
    }

    private fun addNameLineToTag() {
        tag.append("* ")
        tag.append(name)
        tag.append(" *\n")
    }

    private fun addStarLineToTag() {
        tag.append("**")
        tag.append("*".repeat(nameLength))
        tag.append("**\n")
    }


    fun printName(name: String) {
        val printString = """ _____________
| $name |
 •••••••••••••"""
        println(printString)
    }


}