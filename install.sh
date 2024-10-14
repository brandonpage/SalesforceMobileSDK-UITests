#!/usr/bin/env bash

if [[ "$OSTYPE" != "linux-gnu" ]]; then
    gem install cocoapods
    npm install plist
fi

# gem install --no-document fastlane

npm install -g cordova
cordova telemetry off
npm install -g typescript

git clone --branch dev --single-branch --depth 1 https://github.com/forcedotcom/SalesforceMobileSDK-Package.git
cd SalesforceMobileSDK-Package && node ./install.js