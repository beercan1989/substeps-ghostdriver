# Substeps GhostDriver [![Build Status](https://travis-ci.org/beercan1989/substeps-ghostdriver.svg?branch=master)](https://travis-ci.org/beercan1989/substeps-ghostdriver)

Project to provide an integration to PhtantomJS via RemoteWebDriver for use within Substeps, as a replacement for WebDriver. 

+ Substeps: https://github.com/G2G3Digital?utf8=%E2%9C%93&query=substeps
+ Ghost Driver: https://github.com/detro/ghostdriver
+ PhantomJS: https://github.com/ariya/phantomjs

## Basic Requirements
+ Java 8
+ Substeps 1.1.2
+ PhantomJS 1.9.8
+ GhostDriver 1.1.0

## Features coming up in 0.0.1
+ Ability to use PhantomJS instead of Chrome or Firefox with webdriver-substeps
+ Configuration of PhantomJS via Typesafe Config
    + CLI Arguments
    + Page Settings
    + Page Custom Headers