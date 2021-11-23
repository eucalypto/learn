package net.eucalypto.gettingstartedkoinandroid

interface HelloRepository {
    fun giveHello(): String
}

class HelloRepositoryImpl : HelloRepository {
    override fun giveHello(): String = "Hello World. Insert Koin."

}