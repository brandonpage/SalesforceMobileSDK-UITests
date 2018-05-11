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
//  IDPTests.swift
//  IDPTests
//
//  Created by Brandon Page on 4/20/18.
//

import XCTest

class IDPTests: XCTestCase {
    private var spApp = TestApplication()
    private var idpApp = IDPApplicaiton()
    private var username = UserUtility().defaultUsername
    private var password = UserUtility().defaultPassword
    private var appLoadError = "App did not load."
    private var timeout:double_t = 30
    private let reactNativeUsers = "Automated Process Brandon Page circleci Integration User Security User Chatter Expert Mobile SDK Sample App"
    private let sampleAppTitle = "Mobile SDK Sample App"
    
    override func setUp() {
        super.setUp()
        continueAfterFailure = false
        
        spApp.logout()
        idpApp.launch()
        IDPAppPageObject(testApp: idpApp).logout()
    }
    
    override func tearDown() {
        super.tearDown()
    }
    
    func testSPInitiatedFresh() {
        let loginPage = LoginPageObject(testApp: idpApp)
        let idpAuthPage = AuthorizationPageObject(testApp: idpApp)
        let selectLoginFlowPage = SelectLoginFlowPageObject(testApp: spApp)
        
        // SP App
        spApp.launch()
        selectLoginFlowPage.selectIDPFlow()
        
        // IDP App
        idpApp.activate()
        sleep(1)
        loginPage.setUsername(name: username)
        loginPage.setPassword(password: password)
        loginPage.tapLogin()
        idpAuthPage.tapAllow()
        
        // Assert App loads
        switch spApp.appType {
        case .nativeObjC, .nativeSwift:
            XCTAssert(spApp.navigationBars[sampleAppTitle].waitForExistence(timeout: timeout), appLoadError)
        case .hybridLocal, .hyrbidRemote:
            let titleText = (spApp.appType == .hybridLocal) ? "Users" : "Salesforce Mobile SDK Test"
            let title = spApp.staticTexts[titleText]
            let exists = NSPredicate(format: "exists == 1")
            
            expectation(for: exists, evaluatedWith: title, handler: nil)
            waitForExpectations(timeout: timeout, handler: nil)
            XCTAssert(title.exists, appLoadError)
        case .reactNative:
            let titleElement = spApp.children(matching: .window).element(boundBy: 0).children(matching: .other).element.children(matching: .other)[reactNativeUsers].children(matching: .other)[reactNativeUsers].children(matching: .other)[reactNativeUsers].children(matching: .other)[reactNativeUsers].children(matching: .other)[reactNativeUsers].children(matching: .other)[sampleAppTitle].children(matching: .other)[sampleAppTitle].children(matching: .other)[sampleAppTitle]
            XCTAssert(titleElement.waitForExistence(timeout: timeout), appLoadError)
        //default:
        //case .idp:
        //    break
        case .idp: break
            
        }
    }
    
    func testSPInitiated() {
        let loginPage = LoginPageObject(testApp: idpApp)
        let idpAuthPage = AuthorizationPageObject(testApp: idpApp)
        let selectLoginFlowPage = SelectLoginFlowPageObject(testApp: spApp)
        let selectUserPage = SelectUserPageObject(testApp: idpApp)
        
        // IDP App
        idpApp.activate()
        sleep(1)
        loginPage.setUsername(name: username)
        loginPage.setPassword(password: password)
        loginPage.tapLogin()
        idpAuthPage.tapAllow()
        
        // SP App
        spApp.launch()
        selectLoginFlowPage.selectIDPFlow()
        
        // Select User
        selectUserPage.selectUser(user: "circleci circleci")
        
        // Assert App loads
        switch spApp.appType {
        case .nativeObjC, .nativeSwift:
            XCTAssert(spApp.navigationBars[sampleAppTitle].waitForExistence(timeout: timeout), appLoadError)
        case .hybridLocal, .hyrbidRemote:
            let titleText = (spApp.appType == .hybridLocal) ? "Users" : "Salesforce Mobile SDK Test"
            let title = spApp.staticTexts[titleText]
            let exists = NSPredicate(format: "exists == 1")
            
            expectation(for: exists, evaluatedWith: title, handler: nil)
            waitForExpectations(timeout: timeout, handler: nil)
            XCTAssert(title.exists, appLoadError)
        case .reactNative:
            let titleElement = spApp.children(matching: .window).element(boundBy: 0).children(matching: .other).element.children(matching: .other)[reactNativeUsers].children(matching: .other)[reactNativeUsers].children(matching: .other)[reactNativeUsers].children(matching: .other)[reactNativeUsers].children(matching: .other)[reactNativeUsers].children(matching: .other)[sampleAppTitle].children(matching: .other)[sampleAppTitle].children(matching: .other)[sampleAppTitle]
            XCTAssert(titleElement.waitForExistence(timeout: timeout), appLoadError)
        //default:
        //case .idp:
        //    break
        case .idp: break
            
        }
    }
}
