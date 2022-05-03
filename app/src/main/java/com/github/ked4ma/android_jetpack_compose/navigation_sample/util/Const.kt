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
import com.github.ked4ma.android_jetpack_compose.navigation_sample.ui.theme.Blue200
import com.github.ked4ma.android_jetpack_compose.navigation_sample.ui.theme.Blue500
import com.github.ked4ma.android_jetpack_compose.navigation_sample.ui.theme.Green200
import com.github.ked4ma.android_jetpack_compose.navigation_sample.ui.theme.Green500
import com.github.ked4ma.android_jetpack_compose.navigation_sample.ui.theme.Orange200
import com.github.ked4ma.android_jetpack_compose.navigation_sample.ui.theme.Orange500
import com.github.ked4ma.android_jetpack_compose.navigation_sample.ui.theme.Red200
import com.github.ked4ma.android_jetpack_compose.navigation_sample.ui.theme.Red500

object Const {
    val DESTINATIONS = listOf("A", "B", "C", "D")
    val DESTINATION_LOOKUP = DESTINATIONS.withIndex().associate { it.value to it.index }
    val DESTINATION_COLORS = listOf(
        Red500 to Red200,
        Green500 to Green200,
        Blue500 to Blue200,
        Orange500 to Orange200
    )

    const val NAV_ROOT = "ROOT"
    const val NAV_SAMPLE = "sample"

    val ROOT_NODE = NavNode(NAV_ROOT, Green500 to Green200)

    init {
        // nav 1
        run {
            val nav = NavNode("nav1", Red500 to Red200)
            nav.addChild(Leaf("A"))
            nav.addChild(Leaf("B"))
            nav.addChild(Leaf("C"))
            ROOT_NODE.addChild(nav)
        }
        // nav 2
        run {
            ROOT_NODE.addChild(Leaf("D"))
        }
        // nav 3
        run {
            val nav = NavNode("nav3", Blue500 to Green200)
            nav.addChild(Leaf("E"))
            nav.addChild(Leaf("F"))
            nav.addChild(Leaf("G"))
            ROOT_NODE.addChild(nav)
        }
    }
}
