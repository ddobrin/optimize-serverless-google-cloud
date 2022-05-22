# Optimize Java Apps in Cloud Run - Google Cloud

This repo dives into `aspects of optimizing modern Java applications deployed in Cloud Run within the Google Cloud`. 

It can be used as the basis for an optimization workshop or the starting point of a discussion. With every recommendation, you can also find notes on __what could happen if you don't follow it__?

These recommendations have been accumulated over the years working on various projects or publicly available knowledge and documentation. I'd recommend revisiting these aspects before deploying microservices to Production in a serverless environment. 

The material starts from the main aspects of a production-ready service, and is intended to be a `living repo` where new optimizations will be added as new lessons are learned.

## Service Production Readiness Checklist:

![Production Readiness Checklist](images/Main.png)

## The App
The different optimizations can be analyzed based on a few small services in the following architecture:
![App](images/AppArch.png)


Source Code


| Code | Optimization | Notes |
|:-----|:------------ |:---   |
| Java | Use latest LTS version | Java 17 currently
| 


