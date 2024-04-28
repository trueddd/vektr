package com.github.trueddd

import androidx.compose.ui.graphics.vector.PathNode

internal class PathParserImpl : PathParser {

    private val parser = androidx.compose.ui.graphics.vector.PathParser()

    override fun parse(pathData: String): List<PathNode> {
        parser.clear()
        parser.parsePathString(pathData)
        return parser.toNodes()
    }
}
