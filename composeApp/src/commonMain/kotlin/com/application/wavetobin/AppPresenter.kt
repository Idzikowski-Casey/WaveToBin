package com.application.wavetobin

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppPresenter(
    private val scope: CoroutineScope,
    private val fileHelper: FileHelper
) {

    private val stateFlow = MutableStateFlow(
        AppState(
            isLoading = false,
            file = null,
            onUpload = {
                scope.launch(Dispatchers.IO) {
                    onUpload()
                }
            },
            onDownload = { scope.launch(Dispatchers.IO) { onDownload() } }
        ))

    operator fun invoke(): StateFlow<AppState> {
        return stateFlow
    }

    private suspend fun onUpload() {
        println("Uploading file")
        stateFlow.value = stateFlow.value.copy(isLoading = true)

        fileHelper.selectWavFile().collect {
            it?.let {
                println("File selected Presenter: ${it.name}")
                println("File Looping through chunks!")
                for (chunk in it.chunks) {
                    println("Chunk: ${chunk.key} offset - ${chunk.value.offset}")
                    println("Chunk: ${chunk.key} size - ${chunk.value.size}")
                }
                stateFlow.value = stateFlow.value.copy(file = it, isLoading = false)
            }
        }
    }

    private suspend fun onDownload() {
        val filename = (stateFlow.value.file?.name + ".pcm") ?: return
        println("Downloading file - $filename")
        stateFlow.value.file?.chunks?.get("data")?.let {
            val downloadSuccessful = fileHelper.outputBinFile(filename, it.content)
        }
    }
}