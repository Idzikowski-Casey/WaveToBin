package com.application.wavetobin

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WaveChunk(modifier: Modifier = Modifier, chunk: WavChunk) {
    WaveCard(modifier, title = chunk.id) {
        InfoSection(Modifier, title = "Size", info = chunk.size.toString())
    }
}