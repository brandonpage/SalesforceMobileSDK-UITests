#!/usr/bin/env bash

if [[ "$OSTYPE" == "linux-gnu" ]]; then
    sudo apt-get update
    sudo apt-get install libqt5widgets5
    sudo npm install -g cordova@8.1.2
    sudo cordova telemetry off
    gem install --no-document fastlane
else
    npm install -g cordova@8.1.2
    cordova telemetry off
    sudo gem install cocoapods
    sudo npm install plist

    HOMEBREW_NO_AUTO_UPDATE=1 brew install node@12.9.1
fi

git clone --branch dev --single-branch --depth 1 https://github.com/forcedotcom/SalesforceMobileSDK-Package.git
cd SalesforceMobileSDK-Package && node ./install.js