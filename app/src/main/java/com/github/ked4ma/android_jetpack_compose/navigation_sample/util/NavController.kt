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
@file:Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")

package com.github.ked4ma.android_jetpack_compose.navigation_sample.util

import android.content.Intent
import androidx.navigation.NavBackStackEntryState
import androidx.navigation.NavHostController
import com.github.ked4ma.android_jetpack_compose.navigation_sample.model.Node
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.superclasses
import kotlin.reflect.jvm.isAccessible

// WARNING: using kotlin-reflect for getting backStackStates (this is private field)
@Suppress("UNCHECKED_CAST")
fun NavHostController.getBackStackStates(): Map<String, List<Node>> {
    return NavHostController::class.superclasses[0].declaredMemberProperties.find {
        it.name == "backStackStates"
    }?.apply {
        isAccessible = true
    }?.getter?.call(this)?.let {
        it as Map<String, ArrayDeque<NavBackStackEntryState>>
    }?.mapValues { entry ->
        entry.value.toList().map { state ->
            // NavGraph class can be retrieved with destinationId
            this.findDestination(state.destinationId)?.route?.run {
                return@map Const.NODE_MAP.getValue(this)
            }
            // Otherwise, get destination name from bundle
            val intent = state.args?.getParcelable<Intent>(
                "android-support-nav:controller:deepLinkIntent"
            ) ?: return@map Const.UNKNOWN_NODE
            val name = intent.dataString!!.split("androidx.navigation/")[1]
            Const.NODE_MAP.getValue(name)
        }.asReversed()
    } ?: emptyMap()
}
