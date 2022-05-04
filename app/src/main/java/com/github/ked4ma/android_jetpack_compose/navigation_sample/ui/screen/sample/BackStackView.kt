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
package com.github.ked4ma.android_jetpack_compose.navigation_sample.ui.screen.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import com.github.ked4ma.android_jetpack_compose.navigation_sample.model.Node
import com.github.ked4ma.android_jetpack_compose.navigation_sample.util.Const

@Composable
fun BackStackView(
    modifier: Modifier = Modifier,
    entries: List<NavBackStackEntry>
) {
    Row(modifier = modifier.fillMaxWidth()) {
        // back stacks
        BackStackEntryView(
            modifier = Modifier.weight(0.5F),
            entries = entries
        )
        // saved?
        // TODO: consider how to impl
    }
}

@Composable
private fun BackStackEntryView(
    modifier: Modifier = Modifier,
    entries: List<NavBackStackEntry>
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val nodes = entries.mapNotNull {
            val route = it.destination.route ?: return@mapNotNull null
            Const.NODE_MAP.getValue(route)
        }.asReversed()
        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            text = "Back Stack Entry",
            fontSize = 22.sp
        )
        nodes.forEachIndexed { index, node ->
            BackStackItem(
                modifier = Modifier.fillMaxWidth(),
                node = node
            )
            if (index < nodes.lastIndex) {
                Icon(imageVector = Icons.Filled.ArrowUpward, contentDescription = null)
            }
        }
    }
}

@Composable
private fun BackStackItem(
    modifier: Modifier = Modifier,
    node: Node,
) {
    Box(
        modifier = modifier
            .background(
                shape = RoundedCornerShape(100),
                color = node.colors.first
            )
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = node.name,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 24.sp
        )
    }
}
