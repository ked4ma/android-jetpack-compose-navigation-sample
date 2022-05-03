/*
 * Copyright 2021 ked4ma
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
package com.github.ked4ma.android_jetpack_compose_template.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * NOTE: This class is referring to [google codelabs](https://github.com/googlecodelabs/kotlin-coroutines/blob/5b962442/coroutines-codelab/finished_code/src/test/java/com/example/android/kotlincoroutines/main/utils/MainCoroutineScopeRule.kt).
 *
 * TestCoroutineRule installs a TestCoroutineDispatcher for Disptachers.Main.
 *
 * Since it extends TestCoroutineScope, you can directly launch coroutines on the TestCoroutineRule
 * as a [CoroutineScope]:
 *
 * ```
 * mainCoroutineRule.launch { aTestCoroutine() }
 * ```
 *
 * All coroutines started on [TestCoroutineRule] must complete (including timeouts) before the test
 * finishes, or it will throw an exception.
 *
 * When using TestCoroutineRule you should always invoke runBlockingTest on it to avoid creating two
 * instances of [TestCoroutineDispatcher] or [TestCoroutineScope] in your test:
 *
 * ```
 * @Test
 * fun usingRunBlockingTest() = mainCoroutineRule.runBlockingTest {
 *     aTestCoroutine()
 * }
 * ```
 *
 * You may call [DelayController] methods on [TestCoroutineRule] and they will control the
 * virtual-clock.
 *
 * ```
 * testCoroutineRule.pauseDispatcher()
 * // do some coroutines
 * testCoroutineRule.advanceUntilIdle() // run all pending coroutines until the dispatcher is idle
 * ```
 *
 * By default, [TestCoroutineRule] will be in a *resumed* state.
 *
 * @param dispatcher if provided, this [TestCoroutineDispatcher] will be used.
 */
@ExperimentalCoroutinesApi
class TestCoroutineRule(val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()) :
    TestWatcher(),
    TestCoroutineScope by TestCoroutineScope(dispatcher) {
    override fun starting(description: Description?) {
        super.starting(description)
        // If your codebase allows the injection of other dispatchers like
        // Dispatchers.Default and Dispatchers.IO, consider injecting all of them here
        // and renaming this class to `CoroutineScopeRule`
        //
        // All injected dispatchers in a test should point to a single instance of
        // TestCoroutineDispatcher.
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        cleanupTestCoroutines()
        Dispatchers.resetMain()
    }
}
