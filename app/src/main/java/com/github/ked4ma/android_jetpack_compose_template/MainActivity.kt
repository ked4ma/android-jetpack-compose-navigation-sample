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
package com.github.ked4ma.android_jetpack_compose_template

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.github.ked4ma.android_jetpack_compose_template.ui.screen.JetpackComposeTemplateApp
import com.github.ked4ma.android_jetpack_compose_template.ui.theme.AndroidjetpackcomposetemplateTheme
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTemplateApp {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {
                    SampleContent()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SampleContent() {
    Column {
        Greeting("Android")
        Image(
            painter = rememberImagePainter(
                data = "https://github.com/ked4ma/GeneralImageAssets/blob/7a059aa4f4026b799915ebee8516521b336db617/profile/cat.jpg?raw=true",
                builder = {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
            ),
            contentDescription = null,
            modifier = Modifier.size(56.dp)
        )
        val context = LocalContext.current
        Button(onClick = {
            context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
        }) {
            Text(text = "OSS Licenses")
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidjetpackcomposetemplateTheme {
        Greeting("Android")
    }
}
