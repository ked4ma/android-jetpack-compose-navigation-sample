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

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import com.github.ked4ma.android_jetpack_compose.navigation_sample.model.Node
import com.github.ked4ma.android_jetpack_compose.navigation_sample.util.Const

@Composable
fun NavControlOptionPanel(
    optionState: NavControlOption = rememberNavControlOptionState(),
) {
    Column(modifier = Modifier.padding(horizontal = 8.dp)) {
        Row {
            NavControlOptionLaunchSingleTop(optionState)
            NavControlOptionRestoreState(optionState)
        }

        NavControlOptionPopUpTo(optionState.popUpTo)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NavControlOptionLaunchSingleTop(
    optionState: NavControlOption,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = optionState.launchSingleTop,
            onCheckedChange = {
                optionState.launchSingleTop = it
            }
        )
        Text(
            modifier = Modifier.clickable {
                optionState.launchSingleTop = !optionState.launchSingleTop
            },
            text = "launchSingleTop"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NavControlOptionRestoreState(
    optionState: NavControlOption,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = optionState.restoreState,
            onCheckedChange = {
                optionState.restoreState = it
            }
        )
        Text(
            modifier = Modifier.clickable {
                optionState.restoreState = !optionState.restoreState
            },
            text = "restoreState"
        )
    }
}

@Composable
private fun NavControlOptionPopUpTo(
    popUpTo: PopUpToOption,
) {
    var showDialog by remember { mutableStateOf(false) }
    TextButton(
        onClick = {
            showDialog = true
        }
    ) {
        Text(text = "popUpTo: ")
        if (popUpTo.to == null) {
            Text(text = "NONE")
        } else {
            Text(
                modifier = Modifier
                    .background(popUpTo.to!!.colors.first, shape = RoundedCornerShape(100))
                    .padding(horizontal = 8.dp, vertical = 2.dp),
                text = popUpTo.to!!.name,
                color = MaterialTheme.colorScheme.onPrimary
            )
            val info = buildAnnotatedString {
                append(
                    listOfNotNull(
                        if (popUpTo.inclusive) " / inclusive" else null,
                        if (popUpTo.saveState) " / saveState" else null,
                    ).joinToString(separator = "")
                )
            }
            if (info.isNotBlank()) {
                Text(
                    text = info
                )
            }
            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.Top)
                    .clickable {
                        popUpTo.to = null
                    },
                imageVector = Icons.Filled.Cancel,
                contentDescription = null
            )
        }
    }
    if (showDialog) {
        PopUpToDialog(
            initialDest = popUpTo.to ?: Const.ROOT_NODE,
            initialInclusive = if (popUpTo.to == null) false else popUpTo.inclusive,
            initialSaveState = if (popUpTo.to == null) false else popUpTo.saveState,
            onDismissRequest = { showDialog = false },
        ) { (to, inc, save) ->
            popUpTo.to = to
            popUpTo.inclusive = inc
            popUpTo.saveState = save
            showDialog = false
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PopUpToDialog(
    initialDest: Node,
    initialInclusive: Boolean,
    initialSaveState: Boolean,
    onDismissRequest: () -> Unit,
    onSelect: (Triple<Node, Boolean, Boolean>) -> Unit,
) {
    var dest by remember { mutableStateOf(initialDest) }
    var inclusive by remember { mutableStateOf(initialInclusive) }
    var saveState by remember { mutableStateOf(initialSaveState) }
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(onClick = { onSelect(Triple(dest, inclusive, saveState)) }) {
                Text(text = "OK")
            }
        },
        text = {
            Column {
                DestTree(
                    curNode = dest,
                    onClick = {
                        dest = it
                    }
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = inclusive,
                        onCheckedChange = { inclusive = it }
                    )
                    Text(
                        modifier = Modifier.clickable {
                            inclusive = !inclusive
                        },
                        text = "inclusive"
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = saveState,
                        onCheckedChange = { saveState = it }
                    )
                    Text(
                        modifier = Modifier.clickable {
                            saveState = !saveState
                        },
                        text = "saveState"
                    )
                }
            }
        }
    )
    rememberScrollState()
}

@Stable
class NavControlOption {
    var launchSingleTop by mutableStateOf(false)
    var restoreState by mutableStateOf(false)
    val popUpTo = PopUpToOption()
}

@Stable
class PopUpToOption {
    var to: Node? by mutableStateOf(null)
    var inclusive: Boolean by mutableStateOf(false)
    var saveState: Boolean by mutableStateOf(false)
}

@Composable
fun rememberNavControlOptionState() = remember { NavControlOption() }
