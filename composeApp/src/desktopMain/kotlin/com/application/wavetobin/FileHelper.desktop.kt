package com.application.wavetobin

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.withContext
import java.nio.ByteBuffer
import java.nio.ByteOrder

class DesktopFileHelper : FileHelper {

    private val filePicker = FilePicker()

    override suspend fun selectWavFile(): Flow<UploadedFile?> = withContext(Dispatchers.IO) {
        println("Selecting file inside FileHelper")
        return@withContext filePicker.getWaveFile().transform {
            when (it) {
                is FileResult.Success -> {
                    val chunks = parseWaveChunks(it.file.data)

                    val waveFormatInfo = chunks["fmt "]?.let { it1 -> parseFormatChunk(it1) }

                    val uploadedFile = it.file.copy(chunks = chunks, formatInfo = waveFormatInfo)

                    emit(uploadedFile)
                }

                is FileResult.Failure -> emit(null)
                FileResult.Loading -> emit(null)
            }
        }
    }

    override suspend fun outputBinFile(filename: String, data: ByteArray): Boolean {
        return filePicker.outputBinFile(filename, data)
    }

    private fun parseWaveChunks(wavBytes: ByteArray): Map<String, WavChunk> {
        val chunks = mutableMapOf<String, WavChunk>()

        // skip over RIFF header of 12 bytes
        var offset = 12

        while (offset + 8 <= wavBytes.size) {
            val chunkId = String(wavBytes, offset, 4)
            val chunkSize = ByteBuffer.wrap(wavBytes, offset + 4, 4)
                .order(ByteOrder.LITTLE_ENDIAN).int

            val chunkDataStart = offset + 8
            val chunkDataEnd = chunkDataStart + chunkSize

            if (chunkDataEnd > wavBytes.size) break

            val content = wavBytes.copyOfRange(chunkDataStart, chunkDataEnd)

            chunks[chunkId] = WavChunk(chunkId, offset, chunkSize, content)
            offset += 8 + chunkSize
        }

        return chunks
    }

    private fun parseFormatChunk(fmtChunk: WavChunk): WavFormatInfo {
        require(fmtChunk.id == "fmt ")
        val content = requireNotNull(fmtChunk.content)
        val buffer = ByteBuffer.wrap(content).order(ByteOrder.LITTLE_ENDIAN)

        val audioFormat = buffer.short.toInt()      // 0–1
        val numChannels = buffer.short.toInt()      // 2–3
        val sampleRate = buffer.int                 // 4–7
        val byteRate = buffer.int                   // 8–11
        val blockAlign = buffer.short.toInt()       // 12–13
        val bitsPerSample = buffer.short.toInt()    // 14–15

        return WavFormatInfo(audioFormat, numChannels, sampleRate, byteRate, blockAlign, bitsPerSample)
    }
}

actual fun getFileHelper(context: Any?): FileHelper = DesktopFileHelper()