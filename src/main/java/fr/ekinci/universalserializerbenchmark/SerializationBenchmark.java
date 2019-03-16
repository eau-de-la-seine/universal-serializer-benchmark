package fr.ekinci.universalserializerbenchmark;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.format.binary.avro.AvroOption;
import fr.ekinci.universalserializer.format.binary.avro.AvroSerializer;
import fr.ekinci.universalserializer.format.binary.java.JavaSerializer;
import fr.ekinci.universalserializer.format.binary.protocolbuffers.ProtobufSerializer;
import fr.ekinci.universalserializer.format.binary.protocolbuffers.exception.ProtobufSerializerException;
import fr.ekinci.universalserializer.format.binary.thrift.ThriftOption;
import fr.ekinci.universalserializer.format.binary.thrift.ThriftSerializer;
import fr.ekinci.universalserializer.format.binary.thrift.exception.ThriftSerializerException;
import fr.ekinci.universalserializer.format.text.base64.Base64Option;
import fr.ekinci.universalserializer.format.text.base64.Base64Serializer;
import fr.ekinci.universalserializer.format.text.xml.XmlSerializer;
import fr.ekinci.universalserializerbenchmark.pojo.ComplexTestClass;
import fr.ekinci.universalserializerbenchmark.protocolbuffers.Protobuf;
import fr.ekinci.universalserializerbenchmark.thrift.ThriftComplexTestClass;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;

import java.io.IOException;

import static fr.ekinci.universalserializerbenchmark.utils.Utils.instanciateAndInitializeComplexClass;
import static fr.ekinci.universalserializerbenchmark.utils.Utils.instanciateAndInitializeProtoComplexClass;
import static fr.ekinci.universalserializerbenchmark.utils.Utils.instanciateAndInitializeThriftComplexClass;

/**
 * @author Gokan EKINCI
 */
@BenchmarkMode(Mode.Throughput)
public class SerializationBenchmark {
	private static final ComplexTestClass objectToSerialize = instanciateAndInitializeComplexClass();
	private static final Protobuf.ComplexTestClass protoObjectToSerialize = instanciateAndInitializeProtoComplexClass();
	private static final ThriftComplexTestClass thriftObjectToSerialize = instanciateAndInitializeThriftComplexClass();
	private static final Base64Serializer<ComplexTestClass> base64BasicSerializer = new Base64Serializer<>(Base64Option.BASIC);
	private static final Base64Serializer<ComplexTestClass> base64UrlSerializer = new Base64Serializer<>(Base64Option.URL);
	private static final Base64Serializer<ComplexTestClass> base64MimeSerializer = new Base64Serializer<>(Base64Option.MIME);
	private static final JavaSerializer<ComplexTestClass> javaSerializer = new JavaSerializer<>();
	private static ProtobufSerializer<Protobuf.ComplexTestClass> protobufSerializer;
	private static ThriftSerializer<ThriftComplexTestClass> thriftBinarySerializer;
	private static ThriftSerializer<ThriftComplexTestClass> thriftCompactSerializer;
	private static final AvroSerializer<ComplexTestClass> avroBinarySerializer = new AvroSerializer<>(ComplexTestClass.class, AvroOption.BINARY);
	private static final AvroSerializer<ComplexTestClass> avroJsonSerializer = new AvroSerializer<>(ComplexTestClass.class, AvroOption.JSON);
	private static final XmlSerializer<ComplexTestClass> xmlSerializer = new XmlSerializer<>(ComplexTestClass.class);
	private static final ObjectMapper jsonSerializer = new ObjectMapper();

	static {
		try {
			protobufSerializer = new ProtobufSerializer<>(Protobuf.ComplexTestClass.class);
		} catch (ProtobufSerializerException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		try {
			thriftBinarySerializer = new ThriftSerializer<>(ThriftComplexTestClass.class, ThriftOption.BINARY);
			thriftCompactSerializer = new ThriftSerializer<>(ThriftComplexTestClass.class, ThriftOption.COMPACT);
		} catch (ThriftSerializerException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	@Benchmark
	public void benchBase64BasicSerializer() throws SerializationException, DeserializationException {
		// Serialization
		String ser = base64BasicSerializer.serialize(objectToSerialize);

		// Unserialization
		base64BasicSerializer.deserialize(ser);

	}

	@Benchmark
	public void benchBase64UrlSerializer() throws SerializationException, DeserializationException {
		// Serialization
		String ser = base64UrlSerializer.serialize(objectToSerialize);

		// Unserialization
		base64UrlSerializer.deserialize(ser);
	}

	@Benchmark
	public void benchBase64MimeSerializer() throws SerializationException, DeserializationException {
		// Serialization
		String ser = base64MimeSerializer.serialize(objectToSerialize);

		// Unserialization
		base64MimeSerializer.deserialize(ser);
	}

	@Benchmark
	public void benchJavaSerializer() throws SerializationException, DeserializationException {
		// Serialization
		byte[] ser = javaSerializer.serialize(objectToSerialize);

		// Unserialization
		javaSerializer.deserialize(ser);
	}

	@Benchmark
	public void benchProtobufSerializer() throws SerializationException, DeserializationException {
		// Serialization
		byte[] ser = protobufSerializer.serialize(protoObjectToSerialize);

		// Unserialization
		protobufSerializer.deserialize(ser);
	}

	@Benchmark
	public void benchThriftBinarySerializer() throws SerializationException, DeserializationException {
		// Serialization
		byte[] ser = thriftBinarySerializer.serialize(thriftObjectToSerialize);

		// Unserialization
		thriftBinarySerializer.deserialize(ser);
	}

	@Benchmark
	public void benchThriftCompactSerializer() throws SerializationException, DeserializationException {
		// Serialization
		byte[] ser = thriftCompactSerializer.serialize(thriftObjectToSerialize);

		// Unserialization
		thriftCompactSerializer.deserialize(ser);
	}

	@Benchmark
	public void benchAvroBinarySerializer() throws SerializationException, DeserializationException {
		// Serialization
		byte[] ser = avroBinarySerializer.serialize(objectToSerialize);

		// Unserialization
		avroBinarySerializer.deserialize(ser);
	}

	@Benchmark
	public void benchAvroJsonSerializer() throws SerializationException, DeserializationException {
		// Serialization
		byte[] ser = avroJsonSerializer.serialize(objectToSerialize);

		// Unserialization
		avroJsonSerializer.deserialize(ser);
	}

	/**
	 * JAXB implementation
	 */
	@Benchmark
	public void benchXmlSerializer() throws SerializationException, DeserializationException {
		// Serialization
		String ser = xmlSerializer.serialize(objectToSerialize);

		// Unserialization
		xmlSerializer.deserialize(ser);
	}

	/**
	 * Jackson implementation
	 */
	@Benchmark
	public void benchJsonSerializer() throws IOException {
		// Serialization
		String ser = jsonSerializer.writeValueAsString(objectToSerialize);

		// Unserialization
		jsonSerializer.readValue(ser, ComplexTestClass.class);
	}
}
