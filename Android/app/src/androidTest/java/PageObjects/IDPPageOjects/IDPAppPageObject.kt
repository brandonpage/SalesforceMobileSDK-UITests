package PageObjects.IDPPageOjects

import PageObjects.BasePageObject
import TestUtility.IDPTestApplication
import android.support.test.uiautomator.UiSelector
import org.junit.Assert

class IDPAppPageObject(private val app: IDPTestApplication) : BasePageObject() {
    fun assertAppLoads() {
        val actionBar = device.findObject(UiSelector().resourceId("android:id/action_bar_title"))
        val title = device.findObject(UiSelector().resourceId(" android:id/title"))
        if (isArm) {
            actionBar.waitForExists(timeout * 10)
        }
        Assert.assertEquals("IDP App did not successfully testLogin.", app.name, actionBar.text)
        Assert.assertEquals("IDP App did not successfully testLogin.", "USERS", title.text)
    }
}