package com.github.trueddd

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathNode
import com.squareup.kotlinpoet.*
import kotlin.reflect.KClass

class CodeWriter(
    private val iconName: String,
    private val fileName: String,
    private val paths: List<List<PathNode>>,
    private val packageName: String,
    private val viewportHeight: Int,
    private val viewportWidth: Int,
    private val useMaterialPath: Boolean,
    private val iconStyle: IconStyle?,
    private val autoMirrored: Boolean,
) {

    private val privatePropertyName: String
        get() = "saved$iconName"

    private fun FunSpec.Builder.addPaths(paths: List<List<PathNode>>): FunSpec.Builder {
        Icons.Filled.Add
        if (useMaterialPath) {
            paths.forEach { pathData ->
                beginControlFlow(".materialPath")
                pathData.forEach { addStatement(it.asMethodCall().build().toString()) }
                endControlFlow()
            }
        } else {
            paths.forEach { pathData ->
                addCode(CodeBlock.builder().addStatement(".addPath(").build())
                addCode(CodeBlock.builder().indent().addStatement("pathData = listOf(").build())
                addCode(CodeBlock.builder().indent().build())
                pathData.forEach { addCode(it.asClassCall().addStatement(",").build()) }
                addCode(CodeBlock.builder().unindent().addStatement(")").build())
                addCode(CodeBlock.builder().unindent().addStatement(")").build())
            }
        }
        return this
    }

    private fun iconReceiverType(): KClass<*> {
        return when (iconStyle) {
            IconStyle.Rounded -> if (autoMirrored) Icons.AutoMirrored.Rounded::class else Icons.Rounded::class
            IconStyle.Outlined -> if (autoMirrored) Icons.AutoMirrored.Outlined::class else Icons.Outlined::class
            IconStyle.Filled -> if (autoMirrored) Icons.AutoMirrored.Filled::class else Icons.Filled::class
            IconStyle.TwoTone -> if (autoMirrored) Icons.AutoMirrored.TwoTone::class else Icons.TwoTone::class
            IconStyle.Sharp -> if (autoMirrored) Icons.AutoMirrored.Sharp::class else Icons.Sharp::class
            null -> Icons::class
        }
    }

    fun write(): String {
        val file = FileSpec.builder(packageName, fileName)
            .indent("    ")
            .addImport("androidx.compose.ui.unit", "dp")
            .apply {
                if (useMaterialPath) {
                    addImport("androidx.compose.material.icons", "materialPath")
                }
            }
            .addProperty(
                PropertySpec.builder(iconName, ImageVector::class).getter(
                    FunSpec.getterBuilder()
                        .beginControlFlow("if ($privatePropertyName != null)")
                        .addStatement("return $privatePropertyName!!")
                        .endControlFlow()
                        .addStatement("$privatePropertyName = ImageVector.Builder(")
                        .addCode(CodeBlock.builder().indent().build())
                        .addStatement("name = \"$iconName\",")
                        .addStatement("defaultWidth = 24.dp,")
                        .addStatement("defaultHeight = 24.dp,")
                        .addStatement("viewportWidth = ${viewportWidth}f,")
                        .addStatement("viewportHeight = ${viewportHeight}f,")
                        .addCode(CodeBlock.builder().unindent().addStatement(")").build())
                        .addCode(CodeBlock.builder().indent().build())
                        .apply {
                            addPaths(paths)
                        }
                        .addStatement(".build()")
                        .addCode(CodeBlock.builder().unindent().addStatement("return $privatePropertyName!!").build())
                        .build()
                )
                    .addAnnotation(
                        AnnotationSpec.builder(Suppress::class)
                            .addMember("%S", "UnusedReceiverParameter")
                            .build()
                    )
                    .receiver(iconReceiverType())
                    .addModifiers(KModifier.PUBLIC)
                    .build()
            )
            .addProperty(
                PropertySpec.builder(privatePropertyName, ImageVector::class.asTypeName().copy(nullable = true))
                    .initializer("null")
                    .mutable()
                    .addModifiers(KModifier.PRIVATE)
                    .build()
            )
            .build()
        return file.toString()
    }
}
