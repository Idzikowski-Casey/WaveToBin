package com.application.wavetobin

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class AndroidFileHelper(private val context: Context?) : FileHelper {
    override suspend fun selectWavFile(): Flow<UploadedFile?> {
        if(context == null) return flowOf(null)
        // TODO - launch intent to upload wave file
        return flowOf(null)
    }

    override suspend fun outputBinFile(filename: String, data: ByteArray): Boolean {
        return true
    }

}

actual fun getFileHelper(context: Any?): FileHelper = AndroidFileHelper(context as? Context)