package net.eucalypto.gettingstartedkoinandroid.view.simple

import net.eucalypto.gettingstartedkoinandroid.HelloRepository

class MySimplePresenter(private val repo: HelloRepository) {
    fun sayHello() = "$this says: ${repo.giveHello()}"
}