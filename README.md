# Optimize Java Apps in Cloud Run - Google Cloud

This material dives into the `features of optimized, modern Java applications deployed in Cloud Run in the Google Cloud`. It is intended to be a `living repo` where new optimizations will constantly be added.

## Why this work
Talking about optimizing an app for Cloud Run should always be framed in the larger context of production readiness of a service for a Cloud Run PROD environment. 

There are multiple facets to `writing effective services for serverless environments` and they all revolve around the combination of service design, implementation, testing, configuration with environment configuration and optimization best practices. 

Optimization is `shifted-left` and part of the service build from the beginning.

## How to think about production-readiness 
You generally think that a **production-ready cloud service must be:**
* Stable and Reliable
* Scalable and Performant
* Fault Tolerant without any single point of failure
* Properly Monitored
* Documented and Understood
* Properly secured

## How to think about optimizations
Optimizing any app for Cloud Run requires a balance of different aspects to be considered, therefore it is important to always have a clear picture of **what it is that you are optimizing for**:
- start-up time
- execution latency
- resource consumption (memory & CPU)
- concurrency
- image size
- easy maintainability
- lower costs

## Outcomes
This material contains lessons learned from participation in various projects or publicly available knowledge and documentation. 

You can:
* revisit the production-readiness checklist as you build the service and review it before deploying services to a Production serverless environment
* use the material as a starting point for optimization workshops or discussions around production readiness

Materials:
* production-readiness `checklist`
* `complete set` of services (code, config, environment setup) using best practices
* alternative versions of the services along the lines of `what is happening if you don't do this`

## Service production-readiness checklist

![Production Readiness Checklist](images/Main.png)

## The App
A set of services is provided to illustrate the different aspects, following this simple architecture:
![App](images/AppArch.png)


## Project & Source Code
Source code recommendations can be grouped into the following distinct categories:

### Project
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
    
### Spring
* Use the latest version of Spring - 2.7.0 currently
    * Spring releases constantly fix issues and CVEs in the frameworks and their dependencies
* Use [Spring profiles](https://docs.spring.io/spring-boot/docs/1.2.0.M1/reference/html/boot-features-profiles.html) for environment specific configuration segregation 
* Do NOT include Developer tools in Production build
    * Leverage Maven profiles for builds and remove [Spring Developer Tools ](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.devtools)
* Use [lazy initialization](https://cloud.google.com/run/docs/tips/java#lazy-init) - potentially not useful if using `min-instances`, as init could have occurred
* Avoid [class scanning](https://cloud.google.com/run/docs/tips/java#class-scanning) by limiting or avoiding class scanning
    * Improvements are app dependent
* Avoid [nested library](https://cloud.google.com/run/docs/tips/java#nested-jars) archives JARs - valid for OpenJDK (Hotspot)
    * Building native images with GraalVM eliminates the problem as only classes retained during ahead-of-time compilation will be included  in the app image

### Application source code
* Externalize application configuration - do NOT hard-code configs or package config files into images!

## Java Virtual Machine optimizations
* Always set the garbage collector
    * Very important for longer running, smaller footprint Cloud Run services
    * OpenJDK (Hotspot VM)
        * G1 GC set as default for Java 17 for a `server class machine` - defined as >=1,792 MB RAM and >= 2CPUs
        * Serial GC is automatically set for <1,1791 MB
        * The [algorithm used](https://github.com/openjdk/jdk/blob/3121898c33fa3cc5a049977f8677105a84c3e50c/src/hotspot/share/runtime/os.cpp#L1673) for setting the garbage collector
    * GraalVM 
        * Serial GC is set by default for low memory footpring and small Java heapsizes
        * [GC implementations](https://www.graalvm.org/22.0/reference-manual/native-image/MemoryManagement/#:~:text=A%20native%20image%2C%20when%20being,them%20is%20the%20memory%20management.) 
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
* Improve start-up time using `application class-data sharing` 
    * Hotspot JVM - [Analysis](https://ionutbalosin.com/2022/04/application-dynamic-class-data-sharing-in-hotspot-jvm/), [slides](https://ionutbalosin.com/wp-content/uploads/2022/05/Techniques-for-a-faster-JVM-start-up.pdf) and [source code](https://github.com/ionutbalosin/faster-jvm-start-up-techniques/blob/main/app-dynamic-cds-hotspot/README.md)
        * Spring Boot - use [shaded JARs](https://cloud.google.com/run/docs/tips/java#appcds-springboot)
    * Native Images with GraalVM - use Ahead-of-Time (AOT) compilation
        * Major frameworks have AOT support: 
            * [Spring AOT](https://docs.spring.io/spring-native/docs/current/reference/htmlsingle/#aot)
* Find the [optimal thread stack size](https://cloud.google.com/run/docs/tips/java#thread-stack) through profiling, to reduce heap consumption
* Use Native Java images for containers

## Testing 
* Test the app with containers - use Testcontainers
    * Use Google emulators for Google managed services
    * Use open-source test containers for Postgres, MySQL and SQLServer for relational databases running in CloudSQL 

## Build and Packaging
* Use cloud-native buildpacks to build the container images
* Minimize container images by using optimized container images

## Observability


## Operations and Resiliency


## Caching 

## Database 

## Security
* 

## Documentation