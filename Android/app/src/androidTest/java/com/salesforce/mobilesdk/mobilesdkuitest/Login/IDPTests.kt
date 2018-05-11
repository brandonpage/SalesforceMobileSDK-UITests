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
package com.salesforce.mobilesdk.mobilesdkuitest.Login

import PageObjects.*
import PageObjects.IDPPageOjects.AccountSelectorPageObject
import TestUtility.IDPTestApplication
import TestUtility.*
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.UiDevice
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by bpage on 4/18/18.
 */

@RunWith(AndroidJUnit4::class)
class IDPTests {
    var spApp = TestApplication()
    var idpApp = IDPTestApplication()
    var userUtil = UserUtility()
    private var device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    var timeout:Long = 30000
    var username = userUtil.username
    var password = userUtil.password

    @Before
    fun setup() {
        // clear both apps
        spApp.resetApplication()
        idpApp.resetApplication()
    }

    /*
     * 1.  SP initiates login with IDP
     * 2.  Login in IDP
     * 3.  Verify SP login
     * 4.  Verify IDP login
     */
    @Test
    fun testSPInitiatedLogin() {
        spApp.launch()
        val loginPage = LoginPageObject()

        // In SP App - Tap Login with IDP
        loginPage.tapLaunchIDPApp()
        //val accountSelector = AccountSelectorPageObject()
        //accountSelector.tapAddAccount()
        Thread.sleep(timeout * 2)

        // In IDP App
        loginPage.setUsername(username)
        loginPage.setPassword(password)
        loginPage.tapLogin()
        AuthorizationPageObject().tapAllow()
        Thread.sleep(timeout * 2)

        when (spApp.type) {
            AppType.NATIVE_JAVA, AppType.NATIVE_KOTLIN ->
                NativeAppPageObject(spApp).assertAppLoads()
            AppType.HYBRID_LOCAL ->
                HybridLocalAppPageObject(spApp).assertAppLoads()
            AppType.HYBRID_REMOTE ->
                HybridRemoteAppPageObject(spApp).assertAppLoads()
            AppType.REACT_NATIVE ->
                ReactNativeAppPageObject().assertAppLoads()
        }
    }

    /*
     * 1.  IDP Login
     * 2.  Verify IDP login
     * 3.  SP initiates login with IDP
     * 4.  Verify SP login
     */
    @Test
    fun TestIDPLoginSPInitiated() {
        idpApp.launch()
        val loginPage = LoginPageObject()
        loginPage.setUsername(username)
        loginPage.setPassword(password)
        loginPage.tapLogin()
        AuthorizationPageObject().tapAllow()
        Thread.sleep(timeout)
        // Assert IDP is logged in

        spApp.launch()
        loginPage.tapLaunchIDPApp()
        val accountSelector = AccountSelectorPageObject()
        accountSelector.tapSelectAccount()

        // Wait for Swizzle back to SP App
        when (spApp.type) {
            AppType.NATIVE_JAVA, AppType.NATIVE_KOTLIN ->
                NativeAppPageObject(spApp).assertAppLoads()
            AppType.HYBRID_LOCAL ->
                HybridLocalAppPageObject(spApp).assertAppLoads()
            AppType.HYBRID_REMOTE ->
                HybridRemoteAppPageObject(spApp).assertAppLoads()
            AppType.REACT_NATIVE ->
                ReactNativeAppPageObject().assertAppLoads()
        }
    }
}