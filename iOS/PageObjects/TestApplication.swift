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
//
//  TestApplication.swift
//  MobileSDKUITest
//
//  Created by Brandon Page on 2/21/18.
//

import Foundation
import XCTest

class TestApplication: XCUIApplication {
    var bundleString = ""
    var type: AppType = .nativeObjC
    var advAuth = false
    var name: String
    var complexHybrid: String
    
    enum AppType {
        case nativeObjC, nativeSwift, hybridLocal, hybridRemote, reactNative, mobileSyncSwift, mobileSyncReact, carthage
    }
    
    override init() {
        // Get the Test App Bundle from command line arg
        bundleString = ProcessInfo.processInfo.environment["TEST_APP_BUNDLE"]!
        name = String(bundleString.split(separator: ".").last!)
        advAuth = (ProcessInfo.processInfo.environment["ADV_AUTH"] != "")
        complexHybrid = (ProcessInfo.processInfo.environment["COMPLEX_HYBRID"] ?? "")
        
        switch bundleString {
        case "com.salesforce.iosnative":
            type = .nativeObjC
        case "com.salesforce.iosnativeswift", "com.salesforce.iosnativelogin":
            type = .nativeSwift
        case "com.salesforce.ioshybridlocal":
            type = .hybridLocal
        case "com.salesforce.ioshybridremote":
            type = .hybridRemote
        case "com.salesforce.iosreactnative":
            type = .reactNative
        case "com.salesforce.iosmobilesyncexplorerswift":
            type = .mobileSyncSwift
        case "com.salesforce.iosmobilesyncexplorerreactnative":
            type = .mobileSyncReact
        case "com.mobilesdk.CarthageTest":
            type = .carthage
        default:
            assert(false, "Unknown AppType.")
        }
        
        super.init(bundleIdentifier: bundleString)
    }
    
    override func launch() {
        super.launch()
    }
}
