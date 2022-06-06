# Java Virtual Machine optimizations

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