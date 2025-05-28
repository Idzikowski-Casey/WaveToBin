package com.application.wavetobin

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
                state.file?.let { file ->

                    val scrollState = rememberScrollState(0)

                    Column(Modifier.fillMaxWidth().verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Upload Successful!")
                        Text("File Name: ${file.name}")

                        file.formatInfo?.let { it1 -> WaveFormat(formatInfo = it1) }
                        file.chunks.forEach {
                            WaveChunk(chunk = it.value)
                        }
                        Button(onClick = {
                            state.onDownload(file.name)
                        }) {
                            Text("Download as binary")
                        }
                    }
                }
            }
        }
    }
}