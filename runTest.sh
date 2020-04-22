#!/bin/bash

Xvfb :99 &
export local_addr="127.0.0.1:8080"

/Katalon_Studio_Linux_64-5.7.1/katalon -noSplash  -runMode=console -projectPath="$(pwd)/katalon_tests/WizarderyTest.prj" -retry=1 -testSuitePath="Test Suites/AllTests" -executionProfile="default" -browserType="Chrome (headless)" -Djava.awt.headless

