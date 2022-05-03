package com.github.ked4ma.android_jetpack_compose.navigation_sample.model

import androidx.compose.ui.graphics.Color
import com.github.ked4ma.android_jetpack_compose.navigation_sample.ui.theme.Gray200
import com.github.ked4ma.android_jetpack_compose.navigation_sample.ui.theme.Gray500

interface Node {
    val name: String
    val colors: Pair<Color, Color>
    fun flatten(): List<Node>
    fun setColor(colors: Pair<Color, Color>)
}

data class NavNode(
    override val name: String,
    override val colors: Pair<Color, Color>,
) : Node {
    private val _children = mutableListOf<Node>()
    val children: List<Node>
        get() = _children.toList()

    fun addChild(node: Node) {
        _children.add(node)
        node.setColor(colors)
    }

    override fun flatten(): List<Node> = listOf(this) + _children.flatMap { it.flatten() }
    override fun setColor(colors: Pair<Color, Color>) = Unit
}

data class Leaf(
    override val name: String,
) : Node {
    private var _colors: Pair<Color, Color> = Gray500 to Gray200
    override val colors: Pair<Color, Color>
        get() = _colors

    override fun flatten(): List<Node> = listOf(this)

    override fun setColor(colors: Pair<Color, Color>) {
        _colors = colors
    }
}