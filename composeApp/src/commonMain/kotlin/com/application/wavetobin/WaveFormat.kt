package com.application.wavetobin

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WaveFormat(modifier: Modifier = Modifier, formatInfo: WavFormatInfo) {
    WaveCard(modifier, title = "File Format") {
        InfoSection(modifier, "Audio Format", getAudioFormat(formatInfo.audioFormat))
        InfoSection(modifier, "Number of Channels", getChannelCount(formatInfo.numChannels))
        InfoSection(modifier, "Sample Rate", " ${formatInfo.sampleRate} Hz ")
        InfoSection(modifier, "Byte Rate", formatInfo.byteRate.toString() + " Bytes/sec")
        InfoSection(modifier, "Block Align", formatInfo.blockAlign.toString() + "Bytes/frame")
        InfoSection(modifier, "Bits Per Sample", formatInfo.bitsPerSample.toString() + " bits")
    }
}


@Composable
private fun getAudioFormat(audioFormat: Int): String {
    return when (audioFormat) {
        1 -> "PCM"
        3 -> "Float"
        else -> "Unknown"
    }
}

@Composable
private fun getChannelCount(channelCount: Int): String {
    return when (channelCount) {
        1 -> "1 (Mono)"
        2 -> "2 (Stereo)"
        else -> "Unknown"
    }
}

