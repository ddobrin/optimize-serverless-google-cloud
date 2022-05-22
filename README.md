# Optimize Java Apps in Cloud Run - Google Cloud

This repo dives into the `characteristics of optimized, modern Java applications deployed in Cloud Run in the Google Cloud`. It is intended to be a `living repo` where new optimizations will constantly be added.

## Why this repo 
Optimizing an app for Cloud Run is always framed in the larger context of production readiness for a Cloud Run PROD environment. 

It contains a number of lessons learned from participation in various projects over the yars or publicly available knowledge and documentation. Revisit them before deploying microservices to Production in a serverless environment.

## How to think about production-readiness 
We generally think that a **production-ready service is:**
* Stable and Reliable
* Scalable and Performant
* Fault Tolerant without any single point of failure
* Properly Monitored
* Documented and Understood

This material dives into these characteristics as part of a production-ready service checklist and can be used for optimization workshops or discussion around production readiness.

## How to think about optimizations
Optimizing any app for Cloud Run requires a balance of different aspects to be considered, therefore it is important to always have a clear picture of **what it is that we are optimizing for**:
- start-up time
- execution latency
- resource consumption (memory & CPU)
- concurrency
- image size
- costs

Some aspects need to be balanced, others such as container and runtime security are mandatory.

## Service production-readiness checklist

![Production Readiness Checklist](images/Main.png)

## The App
A set of services is provided to illustrate the different points, following this simple architecture:
![App](images/AppArch.png)


### Project & Source Code
Source code recommendations can be grouped into few distinct categories:

**Project**
* Java - Use latest LTS version 
    * Java 17 currently 
    * Better performance, security and resource consumption are achieved by simply building and running the app with the latest Java LTS release
* Maven - Use the latest version
    * 3.8.5 currently  
* Gradle - Use the latest version 
    * 7.4.2 currently
* Dependency management - Use a bill-of-materials(BOM) for managing dependencies and versions consistently for libraries that work together without linkage errors
    * [Spring Boot dependencies](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.build-systems.dependency-management)
    * [Spring Cloud dependencies](https://spring.io/projects/spring-cloud)
    * [Spring Native dependencies](https://docs.spring.io/spring-native/docs/current/reference/htmlsingle/#_add_the_spring_native_dependency)
    * [Google Cloud BOM](https://cloud.google.com/java/docs/bom)
    
**Spring**
* Use the latest version of Spring - 2.7.0 currently
    * Spring releases constantly fix issues and CVEs in the frameworks and their dependencies
* Use 

**Source code** {#source}

### Java Virtual Machine optimizations
* Always set the garbage collector
    * Very important for longer running, smaller footprint Cloud Run services
    * Default for Java 17 in OpenJDK is G1 GC for a `server class machine` - defined as >=2GB RAM and >= 2CPUs
    * For lower setting, Serial GC is automatically set
    * The [algorithm used](https://github.com/openjdk/jdk/blob/3121898c33fa3cc5a049977f8677105a84c3e50c/src/hotspot/share/runtime/os.cpp#L1673) for setting the garbage collector
* Use [container-aware versions](https://cloud.google.com/run/docs/tips/java#container-aware) when deploying a Java app in Cloud Run or a Kubernetes-based environment
    * container awareness is important as it allows deployments to cloud orchestration systems to limit container resources via CPU and memory quotas
    * Java 17 and Java 11 are container aware since general availability (GA)
    * Java 8 is container aware since version 8u202
* Understand JVM memory usage
    * Native memory tracking can't be set via JAVA_TOOL_OPTIONS
    * [Set start-up arguments](https://cloud.google.com/run/docs/tips/java#jvm-memory) in the container image entrypoint or the Buildpack parameters if in use
    * Useful - [Java memory calculator](https://github.com/cloudfoundry/java-buildpack-memory-calculator)
* Catch and [handle](https://cloud.google.com/run/docs/tips/java#handling_sequential_5xx_responses_under_the_container_runtime_contract) properly `Internal errors - 5XX` under the container runtime contract
    * see also [source code](#source)