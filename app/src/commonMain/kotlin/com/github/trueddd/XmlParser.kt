package com.github.trueddd

expect fun createXmlParser(): XmlParser

interface XmlParser {
    fun parse(data: String): Vector
}
