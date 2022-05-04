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
package com.github.ked4ma.android_jetpack_compose.navigation_sample.ui.screen.controller

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.ked4ma.android_jetpack_compose.navigation_sample.model.Leaf
import com.github.ked4ma.android_jetpack_compose.navigation_sample.model.NavNode
import com.github.ked4ma.android_jetpack_compose.navigation_sample.model.Navigation
import com.github.ked4ma.android_jetpack_compose.navigation_sample.model.Node
import com.github.ked4ma.android_jetpack_compose.navigation_sample.ui.theme.AppTheme
import com.github.ked4ma.android_jetpack_compose.navigation_sample.util.Const

@Composable
fun NavControlPanel(
    modifier: Modifier = Modifier,
    initialDest: Node = Const.ROOT_NODE,
    onNavigate: (Navigation) -> Unit,
) {
    var dest by remember { mutableStateOf(initialDest) }
    val optionState = rememberNavControlOptionState()
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        shadowElevation = 8.dp,
        tonalElevation = 8.dp,
    ) {
        Column {
            DestTree(
                modifier = Modifier.padding(8.dp),
                curNode = dest,
                onClick = {
                    if (it == dest) {
                        onNavigate(
                            Navigation(
                                dest,
                                optionState.launchSingleTop,
                                optionState.popUpTo.to,
                                optionState.popUpTo.inclusive,
                                optionState.popUpTo.saveState
                            )
                        )
                    } else {
                        dest = it
                    }
                }
            )

            NavControlOptionPanel(optionState)
        }
    }
}

@Composable
fun DestTree(
    modifier: Modifier = Modifier,
    curNode: Node = Const.ROOT_NODE,
    onClick: (Node) -> Unit = {},
) {
    Box(modifier = modifier.height(IntrinsicSize.Min)) {
        val lineColor = MaterialTheme.colorScheme.onSurface
        val lineStrokeWidth = 8.dp.value
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val baseHeight = size.height / Const.NODE_HEIGHT / 2
            Const.NODES_FOR_DEPTH.forEachIndexed { i, groups ->
                val height = (2 * i + 1) * baseHeight
                val baseWidth = size.width / groups.flatten().size / 2
                var groupStart = 0
                var groupEnd = 0
                groups.forEach { group ->
                    group.forEachIndexed { j, node ->
                        val width = (2 * (j + groupStart) + 1) * baseWidth
                        when (node) {
                            is NavNode -> {
                                if (node.name == Const.NAV_ROOT) {
                                    drawLine(
                                        color = lineColor,
                                        start = Offset(width, height),
                                        end = Offset(width, height + baseHeight),
                                        strokeWidth = lineStrokeWidth,
                                    )
                                } else {
                                    drawLine(
                                        color = lineColor,
                                        start = Offset(width, height - baseHeight),
                                        end = Offset(width, height + baseHeight),
                                        strokeWidth = lineStrokeWidth,
                                    )
                                }
                            }
                            is Leaf -> {
                                drawLine(
                                    color = lineColor,
                                    start = Offset(width, height - baseHeight),
                                    end = Offset(width, height),
                                    strokeWidth = lineStrokeWidth,
                                )
                            }
                        }
                        groupEnd++
                    }
                    if (i > 0) {
                        drawLine(
                            color = lineColor,
                            start = Offset((2 * groupStart + 1) * baseWidth, height - baseHeight),
                            end = Offset((2 * groupEnd - 1) * baseWidth, height - baseHeight),
                            strokeWidth = lineStrokeWidth,
                        )
                    }
                    groupStart = groupEnd
                }
            }
        }
        Column(modifier = Modifier) {
            for (nodes in Const.NODES_FOR_DEPTH) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    for (node in nodes.flatten()) {
                        NodeButton(
                            node = node,
                            selected = node.name == curNode.name,
                        ) {
                            onClick(node)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun NodeButton(
    node: Node,
    selected: Boolean,
    onClick: () -> Unit
) {
    val color by animateColorAsState(
        targetValue = if (selected) node.colors.first else node.colors.second,
        animationSpec = tween(400)
    )
    Text(
        modifier = Modifier
            .clip(RoundedCornerShape(100))
            .background(color)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
        text = node.name,
        color = MaterialTheme.colorScheme.onPrimary,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun NavControlPanelPreview() {
    AppTheme {
        NavControlPanel {}
    }
}
