package com.github.trueddd

data class Vector(
    val height: String,
    val width: String,
    val tint: String,
    val viewportHeight: Int,
    val viewportWidth: Int,
    val path: List<Path>
) {
    data class Path(
        val fillColor: String,
        val pathData: String
    )
}
