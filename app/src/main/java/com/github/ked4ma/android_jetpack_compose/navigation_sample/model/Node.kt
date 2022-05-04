/*
 * Copyright 2022 ked4ma
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.ked4ma.android_jetpack_compose.navigation_sample.model

import androidx.compose.ui.graphics.Color
import com.github.ked4ma.android_jetpack_compose.navigation_sample.ui.theme.Gray200
import com.github.ked4ma.android_jetpack_compose.navigation_sample.ui.theme.Gray500

sealed interface Node {
    val name: String
    val colors: Pair<Color, Color>
    fun flatten(): List<Node>
    fun setColor(colors: Pair<Color, Color>)
    fun height(): Int
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
    override fun height(): Int = (_children.map(Node::height).maxOrNull() ?: 0) + 1
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

    override fun height(): Int = 1
}
