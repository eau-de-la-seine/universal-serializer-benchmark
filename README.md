# Benchmarking serialization and unserialization of JAXB, Jackson, Protobuf, Java etc


* JMH version: 1.20
* VM version: JDK 1.8.0_144, VM 25.144-b01
* VM invoker: /opt/java/jdk1.8.0_144/jre/bin/java
* VM options: -Didea.launcher.port=7532 -Didea.launcher.bin.path=/opt/intellij/intellij_2016.1.4_ultimate/idea-IU-145.2070.6/bin -Dfile.encoding=UTF-8
* Warmup: 20 iterations, 1 s each
* Measurement: 20 iterations, 1 s each
* Timeout: 10 min per iteration
* Threads: 1 thread, will synchronize iterations
* Benchmark mode: Throughput, ops/time
* Benchmark: fr.ekinci.universalserializerbenchmark.SerializationBenchmark.benchBase64Serializer

JMH output (**higher Score is better**):

    Benchmark                                         Mode  Cnt        Score       Error  Units
    SerializationBenchmark.benchBase64Serializer     thrpt  200    32280.926 ±   649.182  ops/s
    SerializationBenchmark.benchBase64UrlSerializer  thrpt  200    31832.296 ±   481.519  ops/s
    SerializationBenchmark.benchJavaSerializer       thrpt  200    37320.349 ±   351.941  ops/s
    SerializationBenchmark.benchJsonSerializer       thrpt  200   340692.587 ±  3906.882  ops/s
    SerializationBenchmark.benchProtobufSerializer   thrpt  200  1339570.892 ± 15076.530  ops/s
    SerializationBenchmark.benchXmlSerializer        thrpt  200     9553.367 ±    99.079  ops/s
