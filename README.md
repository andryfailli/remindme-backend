# RemindMe! Backend
Containerized backend for RemindMe! built with Spring Boot.

[![Build Status](https://travis-ci.org/andryfailli/remindme-backend.svg?branch=master)](https://travis-ci.org/andryfailli/remindme-backend)
[![Coverage Status](https://coveralls.io/repos/github/andryfailli/remindme-backend/badge.svg?branch=master)](https://coveralls.io/github/andryfailli/remindme-backend?branch=master)
[![Quality Gate](https://sonarcloud.io/api/badges/gate?key=it.andreafailli.remindme%3Aremindme-backend)](https://sonarcloud.io/dashboard?id=it.andreafailli.remindme%3Aremindme-backend)
[![SonarCloud Bugs](https://sonarcloud.io/api/badges/measure?key=it.andreafailli.remindme%3Aremindme-backend&metric=bugs)](https://sonarcloud.io/component_measures/metric/reliability_rating/list?it.andreafailli.remindme%3Aremindme-backend)
[![SonarCloud Vulnerabilities](https://sonarcloud.io/api/badges/measure?key=it.andreafailli.remindme%3Aremindme-backend&metric=vulnerabilities)](https://sonarcloud.io/component_measures/metric/security_rating/list?id=it.andreafailli.remindme%3Aremindme-backend)
[![SonarCloud Technical Debt](https://sonarcloud.io/api/badges/measure?key=it.andreafailli.remindme%3Aremindme-backend&metric=sqale_index)](https://sonarcloud.io/component_measures/metric/sqale_index/list?id=it.andreafailli.remindme%3Aremindme-backend)

## Build
If you want to trigger a build, run `mvn clean package` (you need to have Docker installed and running).
Note that you should set these additional arguments
* spring.data.mongodb.uri
* firebase.config.serverKey

For example `mvn -D"spring.data.mongodb.uri"="mongodb://localhost:27017/remindme" -D"firebase.config.serverKey"="...." clean package`
