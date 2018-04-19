package com.salesforce.mobilesdk.mobilesdkuitest.Login

import PageObjects.*
import PageObjects.TestAppPageObjects.IDPTestApplication
import TestUtility.*
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiSelector
import org.junit.Assert
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
    }

    @Test
    fun testSPInitaitedLogin() {
        spApp.launch()

        // Tap Login with IDP

        // In IDP App
        val loginPage = LoginPageObject()
        loginPage.setUsername(username)
        loginPage.setPassword(password)
        loginPage.tapLogin()
        AuthorizationPageObject().tapAllow()
        Thread.sleep(timeout * 2)

        // In SP App
        AuthorizationPageObject().tapAllow()
        Thread.sleep(timeout * 2)

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