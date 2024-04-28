package com.github.trueddd

import androidx.compose.ui.graphics.vector.PathNode

interface PathParser {
    companion object {
        fun create(): PathParser = PathParserImpl()
    }
    fun parse(pathData: String): List<PathNode>
}
