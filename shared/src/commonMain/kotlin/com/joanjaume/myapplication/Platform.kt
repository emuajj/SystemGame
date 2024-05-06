package com.joanjaume.myapplication

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform