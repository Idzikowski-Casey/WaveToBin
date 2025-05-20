package com.application.wavetobin

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(state: AppState) {
    MaterialTheme {
        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = {
                state.onUpload()
            }) {
                Text("Upload a WAV file")
            }
            AnimatedVisibility(state.file != null) {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Upload Successful!")
                    Text("File Name: ${state.file?.name}")
                    Text("Data Size: ${state.file?.chunks?.get("data")?.size}")
                    Text("Format Info: ${state.file?.formatInfo}")
                    Button(onClick = {
                        state.onDownload(state.file?.name ?: "")
                    }) {
                        Text("Download as binary")
                    }
                }
            }
        }
    }
}