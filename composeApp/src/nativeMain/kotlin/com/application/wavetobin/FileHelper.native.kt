package com.application.wavetobin

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class IosFileHelper : FileHelper {
    override suspend fun selectWavFile(): Flow<UploadedFile?> {
        return flowOf(null)
    }

    override suspend fun outputBinFile(filename: String, data: ByteArray): Boolean {
        return true
    }
}

actual fun getFileHelper(context: Any?): FileHelper = IosFileHelper()