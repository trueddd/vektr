package com.github.trueddd

import com.github.ajalt.clikt.completion.CompletionCandidates
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.boolean
import com.github.ajalt.clikt.parameters.types.enum
import com.github.ajalt.clikt.parameters.types.file

class PathWriter : CliktCommand() {

    private val verbose by option("-v", "--verbose", help = "Should emit parsing logs")
        .boolean()
        .default(false)

    private val inputFile by argument(
        name = "in",
        help = "Input file",
        completionCandidates = CompletionCandidates.Path
    ).file(mustExist = true, canBeDir = false, mustBeReadable = true)

    private val outputDir by argument(
        name = "out",
        help = "Output directory",
        completionCandidates = CompletionCandidates.Path
    ).file(mustExist = true, canBeDir = true, canBeFile = false)

    private val outName by option(
        names = arrayOf("-n", "--name"),
        help = "Name of icon (e.g. SearchIcon). If not specified, name of input file will be used"
    )

    private val outFileName by option(
        names = arrayOf("-f", "--file"),
        help = "Name of file where generated code will be saved. If not specified, name of icon will be used"
    )

    private val packageName by option("-p", "--package", help = "Package name for output file")
        .default("`package-name`")

    private val usePathDataBuilder by option("-pd")
        .flag()
        .help("Use path data builder instead of materialPath function (not recommended)")

    private val iconStyle by option("-s", "--style")
        .enum<IconStyle>()
        .help("Icon style that will be used as Icon receiver type. Must be one of these: ${IconStyle.entries.joinToString { it.name }})")

    private val autoMirrored by option("-a", help = "Generate this icon as auto mirrored")
        .flag()

    override fun run() {
        val content = createXmlParser().parse(inputFile.readText())
        if (verbose) {
            echo("Parsed content: $content")
        }
        val paths = content.path.map { PathParser.create().parse(it.pathData) }.takeIf { it.isNotEmpty() } ?: run {
            echo("No path data found in input file", err = true)
            return
        }
        if (verbose) {
            echo("Path data: $paths")
        }
        val fileName = outFileName ?: outName ?: inputFile.nameWithoutExtension
        val writer = CodeWriter(
            iconName = outName ?: inputFile.nameWithoutExtension,
            fileName = fileName,
            paths = paths,
            packageName = packageName,
            viewportHeight = content.viewportHeight,
            viewportWidth = content.viewportWidth,
            useMaterialPath = !usePathDataBuilder,
            autoMirrored = autoMirrored,
            iconStyle = iconStyle,
        )
        val resultCode = writer.write()
        if (verbose) {
            echo("Result code: $resultCode")
        }
        val outputFile = outputDir.resolve("$fileName.kt")
        if (!outputFile.exists()) {
            outputFile.createNewFile()
        }
        outputFile.writeText(resultCode)
    }
}

fun main(args: Array<String>) = PathWriter().main(args)
