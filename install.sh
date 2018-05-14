#!/usr/bin/env bash

if [[ "$OSTYPE" == "linux-gnu" ]]; then
    sudo apt-get update
    sudo apt-get install libqt5widgets5
fi

sudo npm install -g cordova@7.0.0
sudo npm install -g sfdx-cli
cordova telemetry off
gem install --no-document fastlane

git clone --branch dev --single-branch --depth 1 https://github.com/forcedotcom/SalesforceMobileSDK-Package.git
cd SalesforceMobileSDK-Package && node ./install.js