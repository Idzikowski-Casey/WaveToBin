package com.application.wavetobin

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.ComposeUIViewController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

fun MainViewController() {
    val fileHelper = getFileHelper(null)
    val appScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    val presenter = AppPresenter(appScope, fileHelper)

    ComposeUIViewController {
        val state by presenter.invoke().collectAsState()
        App(state)
    }
}