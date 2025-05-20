package com.application.wavetobin

data class AppState(
    val isLoading: Boolean = false,
    val file: UploadedFile? = null,
    val onUpload: () -> Unit = {},
    val onDownload: (name: String) -> Unit = {},
)

data class UploadedFile(
    val name: String,
    val data: ByteArray,
    val formatInfo: WavFormatInfo? = null,
    val chunks: Map<String, WavChunk> = emptyMap()
)

data class WavChunk(
    val id: String,
    val offset: Int,
    val size: Int,
    val content: ByteArray
)

data class WavFormatInfo(
    val audioFormat: Int,
    val numChannels: Int,
    val sampleRate: Int,
    val byteRate: Int,
    val blockAlign: Int,
    val bitsPerSample: Int
)

sealed class FileResult {
    object Loading : FileResult()
    data class Success(val file: UploadedFile) : FileResult()
    data class Failure(val error: Error) : FileResult()
}

enum class Error {
    NO_FILE_SELECTED,
    FILE_NOT_WAV,
    FILE_NULL
}