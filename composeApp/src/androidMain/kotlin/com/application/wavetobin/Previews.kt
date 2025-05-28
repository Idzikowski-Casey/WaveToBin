package com.application.wavetobin

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun WaveFormatPreview() {

    val data = WavFormatInfo(
        audioFormat = 1,
        numChannels = 2,
        sampleRate = 44100,
        byteRate = 176400,
        blockAlign = 4,
        bitsPerSample = 16
    )

    MaterialTheme {
        WaveFormat(modifier = Modifier, formatInfo = data)
    }
}