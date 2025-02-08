# Build With eas

## Introduction

Pour build l'application react native en application Android ou IOS on utilise le service EAS de expo

### Installation EAS

    npm install -g eas-cli

### Login with expo account

    eas login

    {
      email : chanalexjacques@gmail.com
      pseudo : shinojca
      password : expoAppMob!l3
    } 

Verifier le compte connecter actuelle avec

    eas whoami

### Configurer le projet

    eas build:configure

Make custom configuration on eas.json

    ...
    "build": {
      "development": {
        "developmentClient": true,
        "distribution": "internal"
      },
      "preview": {
        "android": {
          "buildType": "apk"
        },
        "distribution": "internal"
      },
      "production": {
        "autoIncrement": true
      }
    },

### Build

    eas build -p android --profile preview
