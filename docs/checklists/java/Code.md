# Project and Source Code

## Project
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
    
## Spring
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

## Application source code
* Externalize application configuration - do NOT hard-code configs or package config files into images!