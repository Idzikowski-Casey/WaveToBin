package com.application.wavetobin

import kotlinx.coroutines.flow.Flow

interface FileHelper {
    suspend fun selectWavFile() : Flow<UploadedFile?>
    suspend fun outputBinFile(filename: String, data: ByteArray) : Boolean
}

expect fun getFileHelper(context: Any?): FileHelper