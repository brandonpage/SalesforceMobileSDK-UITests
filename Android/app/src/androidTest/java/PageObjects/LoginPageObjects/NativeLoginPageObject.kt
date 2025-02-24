package PageObjects.LoginPageObjects

import android.util.Log
import androidx.test.uiautomator.UiSelector
import pageobjects.BasePageObject

class NativeLoginPageObject: BasePageObject() {
    fun setUsername(name: String) {
        val usernameField = device.findObject(UiSelector().resourceId("username"))
        Log.i("uia", "Waiting for username filed to be present.")
        assert(usernameField.waitForExists(timeout))
        usernameField.setText(name)
    }

    fun setPassword(password: String) {
        val passwordField = device.findObject(UiSelector().resourceId("password"))
        Log.i("uia", "Waiting for password filed to be present.")
        assert(passwordField.waitForExists(timeout))
        passwordField.setText(password)
    }

    fun tapLogin() {
        val loginButton = device.findObject(UiSelector().resourceId("Login"))
        assert(loginButton.waitForExists(timeout))
        loginButton.click()
    }
}