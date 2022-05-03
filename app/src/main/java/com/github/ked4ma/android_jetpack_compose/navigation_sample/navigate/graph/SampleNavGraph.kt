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
package com.github.ked4ma.android_jetpack_compose.navigation_sample.navigate.graph

import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.github.ked4ma.android_jetpack_compose.navigation_sample.ui.screen.sample.NavSample
import com.github.ked4ma.android_jetpack_compose.navigation_sample.util.Const

fun NavGraphBuilder.sampleNavGraph(
    navController: NavHostController
) {
    navigation(
        route = Const.NAV_SAMPLE,
        startDestination = "${Const.NAV_SAMPLE}/${Const.DESTINATIONS[0]}",
    ) {
        for (i in Const.DESTINATIONS.indices) {
            composable("${Const.NAV_SAMPLE}/${Const.DESTINATIONS[i]}") {
                val backStackEntries = remember { navController.backQueue.toList() }
                NavSample(
                    title = Const.DESTINATIONS[i],
                    backStackEntries = backStackEntries,
                    onNavigate = {
                        navController.navigate("${Const.NAV_SAMPLE}/${Const.DESTINATIONS[it.dest]}") {
                            if (it.popUpTo in Const.DESTINATIONS.indices) {
                                popUpTo("${Const.NAV_SAMPLE}/${Const.DESTINATIONS[it.popUpTo]}")
                            }
                        }
                    }
                )
            }
        }
    }
}
