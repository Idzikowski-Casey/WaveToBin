package com.application.wavetobin

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform