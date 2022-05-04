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
package com.github.ked4ma.android_jetpack_compose.navigation_sample.util

import com.github.ked4ma.android_jetpack_compose.navigation_sample.model.Leaf
import com.github.ked4ma.android_jetpack_compose.navigation_sample.model.NavNode
import com.github.ked4ma.android_jetpack_compose.navigation_sample.model.Node
import com.github.ked4ma.android_jetpack_compose.navigation_sample.ui.theme.Blue200
import com.github.ked4ma.android_jetpack_compose.navigation_sample.ui.theme.Blue500
import com.github.ked4ma.android_jetpack_compose.navigation_sample.ui.theme.Green200
import com.github.ked4ma.android_jetpack_compose.navigation_sample.ui.theme.Green500
import com.github.ked4ma.android_jetpack_compose.navigation_sample.ui.theme.Red200
import com.github.ked4ma.android_jetpack_compose.navigation_sample.ui.theme.Red500

object Const {
    const val NAV_ROOT = "ROOT"

    val ROOT_NODE = NavNode(NAV_ROOT, Green500 to Green200)
    val NODE_MAP: Map<String, Node>
    val NODE_HEIGHT: Int
    val NODES_FOR_DEPTH: List<List<List<Node>>>

    init {
        // nav 1
        run {
            val nav = NavNode("nav1", Red500 to Red200)
            nav.addChild(Leaf("A"))
            nav.addChild(Leaf("B"))
            nav.addChild(Leaf("C"))
            ROOT_NODE.addChild(nav)
        }
        run {
            ROOT_NODE.addChild(Leaf("D"))
        }
        // nav 2
        run {
            val nav = NavNode("nav2", Blue500 to Blue200)
            nav.addChild(Leaf("E"))
            nav.addChild(Leaf("F"))
            nav.addChild(Leaf("G"))
            ROOT_NODE.addChild(nav)
        }

        NODE_MAP = ROOT_NODE.flatten().associateBy(Node::name)
        NODE_HEIGHT = ROOT_NODE.height()
        NODES_FOR_DEPTH = (1 until NODE_HEIGHT).runningFold(listOf(listOf<Node>(ROOT_NODE))) { acc, _ ->
            acc.map { nodes ->
                nodes.mapNotNull {
                    when (it) {
                        is Leaf -> null
                        is NavNode -> it.children
                    }
                }
            }.flatten()
        }
    }
}
