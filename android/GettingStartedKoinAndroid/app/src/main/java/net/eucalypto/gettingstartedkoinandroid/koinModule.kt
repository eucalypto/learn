package net.eucalypto.gettingstartedkoinandroid

import net.eucalypto.gettingstartedkoinandroid.view.simple.MySimplePresenter
import org.koin.dsl.module

val appModule = module {

    // single instance of HelloRepository
    single<HelloRepository> { HelloRepositoryImpl() }

    // Simple Presenter Factory (creates a new instance when requested)
    factory { MySimplePresenter(get()) }
}