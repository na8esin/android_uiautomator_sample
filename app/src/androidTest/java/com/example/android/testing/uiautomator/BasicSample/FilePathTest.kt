/*
 * Copyright 2015, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.testing.uiautomator.BasicSample

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.*
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.CoreMatchers.`is` as Is
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

import java.util.*
import kotlin.io.relativeTo

/**
 * Basic sample for unbundled UiAutomator.
 */
@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class FilePathTest {

    private lateinit var mDevice: UiDevice

    @Test
    fun checkFilePath() {
        // エミュレータのディレクトリになっちゃう
        // ということは実行時じゃなくてコンパイル時に解決できてないとだめ？
        val file = File("/sdcard/Download/NewTextFile.txt")
        println(file.readText())
    }
}
