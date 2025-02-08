# Build With eas

## Introduction

Pour build l'application react native en application Android ou IOS on utilise le service EAS de expo

### Installation EAS

    npm install -g eas-cli

### Login with expo account

    eas login

Verifier le compte connecter actuelle avec

    eas whoami

### Configurer le projet

    eas build:configure

### Build

    eas build --platform android
