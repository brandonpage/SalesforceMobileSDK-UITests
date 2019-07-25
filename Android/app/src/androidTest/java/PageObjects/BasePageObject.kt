/*
 * Copyright (c) 2017-present, salesforce.com, inc.
 * All rights reserved.
 * Redistribution and use of this software in source and binary forms, with or
 * without modification, are permitted provided that the following conditions
 * are met:
 * - Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * - Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * - Neither the name of salesforce.com, inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software without
 * specific prior written permission of salesforce.com, inc.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package PageObjects

import android.os.Build
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiObjectNotFoundException
import androidx.test.uiautomator.UiSelector

/**
 * Created by bpage on 2/24/18.
 */
open class BasePageObject {
    val device: UiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    // TODO: Update when min verison increaes past API 23
    val isOldDevice: Boolean = (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M)
    // FIXME Update this when we stop using ARM Emulators
    val isArm = Build.SUPPORTED_ABIS.first().contains("armeabi")
    var timeout:Long = if (isArm) 30000 else 5000

    init {
        registerCrashWatcher()
    }

    fun findOnPage(selector: UiSelector): UiObject {
        // Trigger Watcher if necessary
        device.findObject(UiSelector().className("bogus")).waitForExists(1)

        return device.findObject(selector)
    }

    private fun registerCrashWatcher() {
        device.registerWatcher("NotResponding") {
            val notRespondingWindow = UiObject(UiSelector().className("com.android.server.am.AppNotRespondingDialog"))
            if (notRespondingWindow.exists()) {
                Log.d("UITest", "Ui Crash Watcher, found AppNotRespondingDialog")
                recover()
                true
            }
            false
        }

        device.registerWatcher("NotResponding2") {
            val notRespondingWindow = UiObject(UiSelector().packageName("android").textContains("isn't responding"))
            if (notRespondingWindow.exists()) {
                Log.d("UITest", "Ui Crash Watcher, found App isn't responding dialog")
                recover()
                true
            }
            false
        }

        device.registerWatcher("Crash") {
            val notRespondingWindow = UiObject(UiSelector().className("com.android.server.am.AppErrorDialog"))
            if (notRespondingWindow.exists()) {
                Log.d("UITest", "Ui Crash Watcher, found app error dialog")
                recover()
                true
            }
            false
        }

        device.registerWatcher("Crash2") {
            val notRespondingWindow = UiObject(UiSelector().packageName("android").textContains("has stopped"))
            if (notRespondingWindow.exists()) {
                Log.d("UITest", "Ui Crash Watcher, found app has stopped dialog")
                recover()
                true
            }
            false
        }
    }

    private fun recover() {
        Log.d("UITest", "Ui Crash Watcher, recovery attempt")
        val buttonStrings = mutableListOf("Close app", "OK", "Force close", "Wait", "Yes", "Dismiss", "No")
        var button : UiObject
        for (buttonString in buttonStrings) {
            button = device.findObject(UiSelector().text(buttonString).enabled(true))

            if (button != null && button.exists()) {
                Log.d("UITest", "Ui Crash Watcher, found button: $buttonString")
                try {
                    button.waitForExists(timeout)
                    button.click()
                    return
                } catch (e : UiObjectNotFoundException) {
                    Log.d("UITest", "Ui Crash Watcher, crash tapping button: $buttonString")
                }
            }
        }
    }
}