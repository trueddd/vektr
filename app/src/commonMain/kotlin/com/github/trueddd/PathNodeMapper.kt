package com.github.trueddd

import androidx.compose.ui.graphics.vector.PathBuilder
import androidx.compose.ui.graphics.vector.PathNode
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.MemberName.Companion.member
import com.squareup.kotlinpoet.asClassName

internal fun PathNode.asMethodCall(): CodeBlock.Builder {
    val className = PathBuilder::class.asClassName()
    val representation = when (this) {
        is PathNode.MoveTo -> CodeBlock.of(
            "%L(%Lf, %Lf)",
            className.member("moveTo").simpleName, x, y
        )
        is PathNode.LineTo -> CodeBlock.of(
            "%L(%Lf, %Lf)",
            className.member("lineTo").simpleName, x, y
        )
        is PathNode.HorizontalTo -> CodeBlock.of(
            "%L(%Lf)",
            className.member("horizontalLineTo").simpleName, x
        )
        is PathNode.VerticalTo -> CodeBlock.of(
            "%L(%Lf)",
            className.member("verticalLineTo").simpleName, y
        )
        is PathNode.QuadTo -> CodeBlock.of(
            "%L(%Lf, %Lf, %Lf, %Lf)",
            className.member("quadTo").simpleName, x1, y1, x2, y2
        )
        is PathNode.CurveTo -> CodeBlock.of(
            "%L(%Lf, %Lf, %Lf, %Lf, %Lf, %Lf)",
            className.member("curveTo").simpleName, x1, y1, x2, y2, x3, y3
        )
        is PathNode.ArcTo -> CodeBlock.of(
            "%L(%Lf, %Lf, %Lf, %L, %L, %Lf, %Lf)",
            className.member("arcTo").simpleName, horizontalEllipseRadius, verticalEllipseRadius, theta,
            isMoreThanHalf, isPositiveArc, arcStartX , arcStartY
        )
        is PathNode.Close -> CodeBlock.of(
            "%L()",
            className.member("close").simpleName
        )
        is PathNode.ReflectiveCurveTo -> CodeBlock.of(
            "%L(%Lf, %Lf, %Lf, %Lf)",
            className.member("reflectiveCurveTo").simpleName, x1, y1, x2, y2
        )
        is PathNode.ReflectiveQuadTo -> CodeBlock.of(
            "%L(%Lf, %Lf)",
            className.member("reflectiveQuadTo").simpleName, x, y
        )
        is PathNode.RelativeArcTo -> CodeBlock.of(
            "%L(%Lf, %Lf, %Lf, %L, %L, %Lf, %Lf)",
            className.member("arcToRelative").simpleName, horizontalEllipseRadius, verticalEllipseRadius, theta,
            isMoreThanHalf, isPositiveArc, arcStartDx, arcStartDy
        )
        is PathNode.RelativeCurveTo -> CodeBlock.of(
            "%L(%Lf, %Lf, %Lf, %Lf, %Lf, %Lf)",
            className.member("curveToRelative").simpleName, dx1, dy1, dx2, dy2, dx3, dy3
        )
        is PathNode.RelativeHorizontalTo -> CodeBlock.of(
            "%L(%Lf)",
            className.member("horizontalLineToRelative").simpleName, dx
        )
        is PathNode.RelativeLineTo -> CodeBlock.of(
            "%L(%Lf, %Lf)",
            className.member("lineToRelative").simpleName, dx, dy
        )
        is PathNode.RelativeMoveTo -> CodeBlock.of(
            "%L(%Lf, %Lf)",
            className.member("moveToRelative").simpleName, dx, dy
        )
        is PathNode.RelativeQuadTo -> CodeBlock.of(
            "%L(%Lf, %Lf, %Lf, %Lf)",
            className.member("quadToRelative").simpleName, dx1, dy1, dx2, dy2
        )
        is PathNode.RelativeReflectiveCurveTo -> CodeBlock.of(
            "%L(%Lf, %Lf, %Lf, %Lf)",
            className.member("reflectiveCurveToRelative").simpleName, dx1, dy1, dx2, dy2
        )
        is PathNode.RelativeReflectiveQuadTo -> CodeBlock.of(
            "%L(%Lf, %Lf)",
            className.member("reflectiveQuadToRelative").simpleName, dx, dy
        )
        is PathNode.RelativeVerticalTo -> CodeBlock.of(
            "%L(%Lf)",
            className.member("verticalLineToRelative").simpleName, dy
        )
    }
    return CodeBlock.builder().add(representation)
}

internal fun PathNode.asClassCall(): CodeBlock.Builder {
    val representation = when (this) {
        is PathNode.MoveTo -> CodeBlock.of(
            "%T(%Lf, %Lf)",
            PathNode.MoveTo::class, x, y
        )
        is PathNode.LineTo -> CodeBlock.of(
            "%T(%Lf, %Lf)",
            PathNode.LineTo::class, x, y
        )
        is PathNode.HorizontalTo -> CodeBlock.of(
            "%T(%Lf)",
            PathNode.HorizontalTo::class, x
        )
        is PathNode.VerticalTo -> CodeBlock.of(
            "%T(%Lf)",
            PathNode.VerticalTo::class, y
        )
        is PathNode.QuadTo -> CodeBlock.of(
            "%T(%Lf, %Lf, %Lf, %Lf)",
            PathNode.QuadTo::class, x1, y1, x2, y2
        )
        is PathNode.CurveTo -> CodeBlock.of(
            "%T(%Lf, %Lf, %Lf, %Lf, %Lf, %Lf)",
            PathNode.CurveTo::class, x1, y1, x2, y2, x3, y3
        )
        is PathNode.ArcTo -> CodeBlock.of(
            "%T(%Lf, %Lf, %Lf, %L, %L, %Lf, %Lf)",
            PathNode.ArcTo::class, horizontalEllipseRadius, verticalEllipseRadius, theta,
            isMoreThanHalf, isPositiveArc, arcStartX , arcStartY
        )
        is PathNode.Close -> CodeBlock.of(
            "%T",
            PathNode.Close::class
        )
        is PathNode.ReflectiveCurveTo -> CodeBlock.of(
            "%T(%Lf, %Lf, %Lf, %Lf)",
            PathNode.ReflectiveCurveTo::class, x1, y1, x2, y2
        )
        is PathNode.ReflectiveQuadTo -> CodeBlock.of(
            "%T(%Lf, %Lf)",
            PathNode.ReflectiveQuadTo::class, x, y
        )
        is PathNode.RelativeArcTo -> CodeBlock.of(
            "%T(%Lf, %Lf, %Lf, %L, %L, %Lf, %Lf)",
            PathNode.RelativeArcTo::class, horizontalEllipseRadius, verticalEllipseRadius, theta,
            isMoreThanHalf, isPositiveArc, arcStartDx, arcStartDy
        )
        is PathNode.RelativeCurveTo -> CodeBlock.of(
            "%T(%Lf, %Lf, %Lf, %Lf, %Lf, %Lf)",
            PathNode.RelativeCurveTo::class, dx1, dy1, dx2, dy2, dx3, dy3
        )
        is PathNode.RelativeHorizontalTo -> CodeBlock.of(
            "%T(%Lf)",
            PathNode.RelativeHorizontalTo::class, dx
        )
        is PathNode.RelativeLineTo -> CodeBlock.of(
            "%T(%Lf, %Lf)",
            PathNode.RelativeLineTo::class, dx, dy
        )
        is PathNode.RelativeMoveTo -> CodeBlock.of(
            "%T(%Lf, %Lf)",
            PathNode.RelativeMoveTo::class, dx, dy
        )
        is PathNode.RelativeQuadTo -> CodeBlock.of(
            "%T(%Lf, %Lf, %Lf, %Lf)",
            PathNode.RelativeQuadTo::class, dx1, dy1, dx2, dy2
        )
        is PathNode.RelativeReflectiveCurveTo -> CodeBlock.of(
            "%T(%Lf, %Lf, %Lf, %Lf)",
            PathNode.RelativeReflectiveCurveTo::class, dx1, dy1, dx2, dy2
        )
        is PathNode.RelativeReflectiveQuadTo -> CodeBlock.of(
            "%T(%Lf, %Lf)",
            PathNode.RelativeReflectiveQuadTo::class, dx, dy
        )
        is PathNode.RelativeVerticalTo -> CodeBlock.of(
            "%T(%Lf)",
            PathNode.RelativeVerticalTo::class, dy
        )
    }
    return CodeBlock.builder().add(representation)
}
