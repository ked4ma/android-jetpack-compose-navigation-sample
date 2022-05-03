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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.ked4ma.android_jetpack_compose.navigation_sample.model.Navigation
import com.github.ked4ma.android_jetpack_compose.navigation_sample.ui.theme.AppTheme
import com.github.ked4ma.android_jetpack_compose.navigation_sample.ui.theme.Gray200
import com.github.ked4ma.android_jetpack_compose.navigation_sample.ui.theme.Gray500
import com.github.ked4ma.android_jetpack_compose.navigation_sample.util.Const
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun NavControlPanel(
    modifier: Modifier = Modifier,
    initialDest: Int = 0,
    onNavigate: (Navigation) -> Unit,
) {
    var dest by remember { mutableStateOf(initialDest) }
    var popUpTo by remember { mutableStateOf(-1) }
    Column(modifier = modifier) {
        NavControlDestPanel(
            modifier = Modifier.padding(8.dp),
            dests = Const.DESTINATIONS,
            destColors = Const.DESTINATION_COLORS,
            curIndex = dest,
            onClick = {
                if (dest == it) {
                    onNavigate(
                        Navigation(
                            dest,
                            popUpTo
                        )
                    )
                } else {
                    dest = it
                }
            }
        )
        NavControlOptionPanel(
            onSetPopUpTo = {
                popUpTo = it
            }
        )
    }
}

@Composable
private fun NavControlDestPanel(
    modifier: Modifier = Modifier,
    curIndex: Int = 0,
    dests: List<String>,
    destColors: List<Pair<Color, Color>>,
    onClick: (Int) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        for (i in dests.indices) {
            DestButton(
                title = dests[i],
                colors = destColors[i],
                selected = i == curIndex,
                onClick = {
                    onClick(i)
                }
            )
        }
    }
}

@Composable
private fun DestButton(
    title: String,
    colors: Pair<Color, Color>,
    selected: Boolean,
    onClick: () -> Unit
) {
    val buttonColor by animateColorAsState(
        if (selected) colors.first else colors.second,
        animationSpec = tween(400)
    )
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor
        ),
        elevation = ButtonDefaults.buttonElevation(4.dp)
    ) {
        Text(
            text = title,
            fontSize = 24.sp
        )
    }
}

@Composable
private fun NavControlOptionPanel(
    onSetPopUpTo: (Int) -> Unit,
) {
    FlowRow(
        mainAxisSpacing = 8.dp,
        crossAxisSpacing = 8.dp
    ) {
        NavControlOptionPopUpTo(onSetPopUpTo)
        // TODO: impl inclusive
        // TODO: impl saveState
    }
}

@Composable
private fun NavControlOptionPopUpTo(
    onChanged: (Int) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    var target by remember { mutableStateOf(-1) }
    TextButton(
        onClick = {
            showDialog = true
        }
    ) {
        // TODO: adjust design
        Text(text = "popUpTo: ${Const.DESTINATIONS.getOrNull(target) ?: "NONE"}")
    }
    if (showDialog) {
        DestSelectionDialog(
            curTarget = target,
            onDismissRequest = { showDialog = false },
        ) {
            target = it
            onChanged(target)
            showDialog = false
        }
    }
}

@Composable
private fun DestSelectionDialog(
    curTarget: Int,
    onDismissRequest: () -> Unit,
    onSelect: (Int) -> Unit,
) {
    val dests = listOf("NONE") + Const.DESTINATIONS
    val destColors = listOf(Gray500 to Gray200) + Const.DESTINATION_COLORS
    var dest by remember { mutableStateOf(curTarget + 1) }
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(onClick = { onSelect(dest - 1) }) {
                Text(text = "OK")
            }
        },
        text = {
            FlowRow(
                mainAxisSpacing = 4.dp,
                crossAxisSpacing = 4.dp
            ) {
                for (i in dests.indices) {
                    DestButton(
                        title = dests[i],
                        colors = destColors[i],
                        selected = i == dest,
                        onClick = { dest = i }
                    )
                }
            }
        }
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
