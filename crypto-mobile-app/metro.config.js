// Learn more https://docs.expo.io/guides/customizing-metro
const { getDefaultConfig } = require('expo/metro-config');

const defauleConfig = getDefaultConfig(__dirname);
defauleConfig.resolver.assetExts.push('cjs');

module.exports = defauleConfig;
