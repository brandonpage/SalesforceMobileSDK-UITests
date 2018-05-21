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
package PageObjects.IDPPageOjects

import PageObjects.BasePageObject
import android.support.test.uiautomator.UiSelector


/**
 * Created by bpage on 4/19/18.
 */
class AccountSelectorPageObject : BasePageObject() {

    fun tapSelectAccount() {
        var selectAccountButton = device.findObject(UiSelector().resourceId("com.salesforce.native_kotlin:id/sf__switcher_apply_button"))
        assert(selectAccountButton.waitForExists(timeout * 2))
        selectAccountButton.click()

        var sleep_time = timeout * 2
        if (isArm) {
            sleep_time *= 2
        }
        Thread.sleep(sleep_time)
    }

    fun tapAddAccount() {
        var addAccountButton = device.findObject(UiSelector().resourceId("com.salesforce.native_kotlin:id/sf__add_account_button"))
        assert(addAccountButton.waitForExists(timeout * 5))
        addAccountButton.click()
    }
}
