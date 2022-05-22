# Optimize Java Apps in Cloud Run - Google Cloud

This repo dives into the `characteristics of optimized, modern Java applications deployed in Cloud Run in the Google Cloud`. It is intended to be a `living repo` where new optimizations will constantly be added.

## Why this repo 
It can be used as the basis for an optimization workshop or the starting point of a discussion on service production readiness. Notes on *what could happen if you don't follow it* are provided for the different recommendations.

These recommendations have been accumulated over the years working on various projects or publicly available knowledge and documentation. Revisit them before deploying microservices to Production in a serverless environment. 

## How to think about production-readiness 
We think that a **production-ready service is:**
* Stable and Reliable
* Scalable and Performant
* Fault Tolerant and has no single point of failure.
* Properly Monitored
* Documented and Understood

The material dives into these characteristics as part of a production-ready service checklist

## Service production-readiness checklist

![Production Readiness Checklist](images/Main.png)

## The App
A set of services is provided to illustrate the different points, following this simple architecture:
![App](images/AppArch.png)


## Source Code
Source code recommendations can be grouped into few distinct categories:

<font color="gree">**Project**</font>
| Project | Recommendations / Optimizations | Notes |
|:-----|:------------ |:---   |
| Java | Use latest LTS version | Java 17 currently
|  | 

| Code | Optimization | Notes |
|:-----|:------------ |:---   |
| Java | Use latest <br> LTS version | Java 17 currently
| 


