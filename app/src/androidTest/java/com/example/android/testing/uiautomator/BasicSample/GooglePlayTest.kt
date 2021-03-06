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
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

/**
 * Basic sample for unbundled UiAutomator.
 */
@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class GooglePlayTest {
    companion object {
        const val BASIC_SAMPLE_PACKAGE
        = "com.example.android.testing.uiautomator.BasicSample"
        const val LAUNCH_TIMEOUT:Long = 5000
    }

    private lateinit var mDevice: UiDevice

    @Before
    fun startMainActivityFromHomeScreen() {
    
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        // Start from the home screen
        mDevice.pressHome()

        // Wait for launcher
        val launcherPackage = getLauncherPackageName()
        assertThat(launcherPackage, notNullValue())
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT)
    }

    @Test
    fun checkPreconditions() {
        assertThat(mDevice, notNullValue())
    }

    @Test
    fun playStoreSignIn() {

        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(
                "https://play.google.com/store")
            setPackage("com.android.vending")
        }
        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val context = ApplicationProvider.getApplicationContext<Context>()
        context.startActivity(intent)

        // Wait for the app to appear
        mDevice.wait(
            Until.hasObject(
                By.pkg("com.android.vending").depth(0)), LAUNCH_TIMEOUT)

        buttonClick("Sign in")

        // Sign in????????????????????????????????????
        mDevice.wait(
            Until.hasObject(
                By.text("with your Google Account.")),
            LAUNCH_TIMEOUT)

        // Email?????????
        val emailOrPhone = mDevice.findObject(
            UiSelector().className("android.widget.EditText")
        )

        // ?????????????????????????????????reCAPTCHA????????????????????????????????????
        emailOrPhone.text = email

        // Press the next button
        buttonClick("Next")

        waitUntilWelcomeIsDisplayed()

        // password
        val passwordEditText = mDevice.findObject(
            UiSelector().className("android.widget.EditText")
        )

        // ?????????????????????????????????reCAPTCHA????????????????????????????????????
        passwordEditText.text = password

        // Press the next button on the password input screen
        buttonClick("Next")

        waitUntilWelcomeIsDisplayed()

        // Terms of Service
        buttonClick("I agree")

        // The screen continues after this, but sign in is completed up to this point

        // screen shot
        // ????????????????????????????????????????????????????????????permission denied
        val file = File("/sdcard/Pictures/playStoreSignIn.png")
        mDevice.takeScreenshot(file)

        assertThat("hello", equalTo("hello"))
    }

    /**
     * Uses package manager to find the package name of the device launcher. Usually this package
     * is "com.android.launcher" but can be different at times. This is a generic solution which
     * works on all platforms.`
     */
    private fun getLauncherPackageName():String? {
        // Create launcher Intent
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)

        // Use PackageManager to get the launcher package name
        val pm = ApplicationProvider.getApplicationContext<Context>().packageManager
        val resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
        return resolveInfo?.activityInfo?.packageName
    }

    private fun waitUntilWelcomeIsDisplayed() {
        mDevice.wait(
            Until.hasObject(
                By.text("Welcome")),
            LAUNCH_TIMEOUT)
    }

    private fun buttonClick(buttonName: String) {
        val aButton: UiObject = mDevice.findObject(
            UiSelector().text(buttonName).className("android.widget.Button")
        )
        if (aButton.exists() && aButton.isEnabled) {
            aButton.click()
        }
    }
}
