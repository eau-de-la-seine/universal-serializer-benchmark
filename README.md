# Benchmarking serialization and unserialization of JAXB, Jackson, Protobuf, Java etc


* JMH version: 1.20
* VM version: JDK 1.8.0_91, VM 25.91-b15
* VM invoker: C:\opt\jdk1.8.0_91\jre\bin\java.exe
* VM options: -Dvisualvm.id=307294426819049 -Didea.launcher.port=7533 -Didea.launcher.bin.path=C:\opt\intellij-community-2016.1.2\bin -Dfile.encoding=UTF-8
* Warmup: 20 iterations, 1 s each
* Measurement: 20 iterations, 1 s each
* Timeout: 10 min per iteration
* Threads: 1 thread, will synchronize iterations
* Benchmark mode: Throughput, ops/time
* Benchmark: fr.ekinci.universalserializerbenchmark.SerializationBenchmark.

JMH output (**higher Score is better**):

    Run complete. Total time: 01:01:43

    Benchmark                                             Mode  Cnt       Score       Error  Units
    SerializationBenchmark.benchBase64BasicSerializer    thrpt  200   21409,969 ±  1109,881  ops/s
    SerializationBenchmark.benchBase64MimeSerializer     thrpt  200   21976,016 ±   719,333  ops/s
    SerializationBenchmark.benchBase64UrlSerializer      thrpt  200   24535,153 ±   531,182  ops/s
    SerializationBenchmark.benchJavaSerializer           thrpt  200   28120,712 ±   151,490  ops/s
    SerializationBenchmark.benchJsonSerializer           thrpt  200  246173,014 ± 10477,019  ops/s
    SerializationBenchmark.benchProtobufSerializer       thrpt  200  947406,348 ± 31474,364  ops/s
    SerializationBenchmark.benchThriftBinarySerializer   thrpt  200  359121,511 ± 11606,805  ops/s
    SerializationBenchmark.benchThriftCompactSerializer  thrpt  200  473755,705 ±  1823,196  ops/s
    SerializationBenchmark.benchXmlSerializer            thrpt  200    5956,681 ±    98,151  ops/s
