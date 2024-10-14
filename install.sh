#!/usr/bin/env bash

if [[ "$OSTYPE" != "linux-gnu" ]]; then
    gem install cocoapods
    npm install plist
    gem install --no-document fastlane
fi

npm install -g cordova
cordova telemetry off
npm install -g typescript

git clone --branch dev --single-branch --depth 1 https://github.com/forcedotcom/SalesforceMobileSDK-Package.git
cd SalesforceMobileSDK-Package && node ./install.js