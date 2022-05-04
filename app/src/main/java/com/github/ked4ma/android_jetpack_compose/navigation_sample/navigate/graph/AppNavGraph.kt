package com.github.ked4ma.android_jetpack_compose.navigation_sample.navigate.graph

import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.github.ked4ma.android_jetpack_compose.navigation_sample.model.Leaf
import com.github.ked4ma.android_jetpack_compose.navigation_sample.model.NavNode
import com.github.ked4ma.android_jetpack_compose.navigation_sample.model.Node
import com.github.ked4ma.android_jetpack_compose.navigation_sample.ui.screen.sample.NavSample
import com.github.ked4ma.android_jetpack_compose.navigation_sample.util.Const


fun NavGraphBuilder.appNavGraph(
    node: Node = Const.ROOT_NODE,
    navController: NavHostController,
) {
    when (node) {
        is NavNode -> {
            navigation(
                route = node.name,
                startDestination = node.children.first().name
            ) {
                node.children.forEach {
                    appNavGraph(it, navController)
                }
            }
        }
        is Leaf -> {
            composable(node.name) {
                val backStackEntries = remember { navController.backQueue.toList() }
                NavSample(
                    title = node.name,
                    backStackEntries = backStackEntries,
                    onNavigate = { nav ->
                        navController.navigate(nav.dest.name) {
                            launchSingleTop = nav.launchSingleTop
                            nav.popUpTo?.let {
                                popUpTo(it.name) {
                                    inclusive = nav.popUpToInclusive
                                    saveState = nav.popUpToSaveState
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}