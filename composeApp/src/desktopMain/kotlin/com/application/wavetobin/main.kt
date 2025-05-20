package com.application.wavetobin

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking

fun main() {

    val appScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    val fileHelper = getFileHelper(null)

    val presenter = AppPresenter(appScope, fileHelper)

    application(exitProcessOnExit = false) {
        Window(
            onCloseRequest = ::exitApplication,
            title = "WaveToBin",
        ) {
            val state by presenter.invoke().collectAsState()

            App(state)
        }
    }

    // clean up processes
    appScope.cancel()
}