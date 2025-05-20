package com.application.wavetobin

import javafx.stage.FileChooser
import javafx.stage.Stage
import java.util.concurrent.atomic.AtomicBoolean
import javafx.application.Platform
import javafx.stage.DirectoryChooser
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

class FilePicker {

    private val started = AtomicBoolean(false)

    private fun initJavaFxPlatformIfNeeded() {
        if (started.get().not()) {
            started.set(true)
            Platform.startup {} // Starts JavaFX Toolkit
        }
    }

    fun getWaveFile(): Flow<FileResult> = flow {
        emit(FileResult.Loading)
        initJavaFxPlatformIfNeeded()

        val result = CompletableDeferred<FileResult>()

        Platform.runLater {
            try {
                val stage = Stage()
                val fileChooser = FileChooser().apply {
                    title = "Select WAV File"
                    extensionFilters.add(FileChooser.ExtensionFilter("WAV files", "*.wav"))
                }

                val file = fileChooser.showOpenDialog(stage)

                val bytes: ByteArray =
                    file.takeIf { file.extension == "wav" }?.readBytes() ?: run {
                        result.complete(FileResult.Failure(Error.FILE_NOT_WAV))
                        return@runLater
                    }

                if (String(bytes, 0, 4) != "RIFF" || String(bytes, 8, 4) != "WAVE") {
                    result.complete(FileResult.Failure(Error.FILE_NOT_WAV))
                    return@runLater
                }

                stage.close()
                result.complete(
                    FileResult.Success(
                        UploadedFile(
                            name = file.name,
                            data = bytes
                        )
                    )
                )
            } catch (e: Exception) {
                result.complete(FileResult.Failure(Error.FILE_NULL))
            }
        }

        emit(result.await())
    }

    suspend fun outputBinFile(filename: String, data: ByteArray) : Boolean {
        initJavaFxPlatformIfNeeded()
        val result = CompletableDeferred<Boolean>()

        Platform.runLater {
            try {
                val chooser = DirectoryChooser().apply {
                    title = "Choose Output Directory"
                }

                val dir = chooser.showDialog(Stage())

                if (dir != null) {
                    val outputFile = File(dir, filename)
                    outputFile.writeBytes(data)
                    result.complete(true)
                } else {
                    println("Directory selection cancelled.")
                    result.complete(false)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                result.complete(false)
            }
        }

        return result.await()
    }
}