package fr.ekinci.universalserializerbenchmark;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ekinci.universalserializer.exception.DeserializationException;
import fr.ekinci.universalserializer.exception.SerializationException;
import fr.ekinci.universalserializer.format.binary.java.JavaSerializer;
import fr.ekinci.universalserializer.format.binary.protocolbuffers.ProtobufSerializer;
import fr.ekinci.universalserializer.format.binary.protocolbuffers.exception.ProtobufSerializerException;
import fr.ekinci.universalserializer.format.text.base64.AbstractBase64Serializer;
import fr.ekinci.universalserializer.format.text.base64.Base64Serializer;
import fr.ekinci.universalserializer.format.text.base64.Base64UrlSerializer;
import fr.ekinci.universalserializer.format.text.xml.XmlSerializer;
import fr.ekinci.universalserializerbenchmark.pojo.ComplexTestClass;
import fr.ekinci.universalserializerbenchmark.protocolbuffers.Protobuf;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;

import java.io.IOException;

import static fr.ekinci.universalserializerbenchmark.utils.Utils.instanciateAndInitializeComplexClass;
import static fr.ekinci.universalserializerbenchmark.utils.Utils.instanciateAndInitializeProtoComplexClass;

/**
 * @author Gokan EKINCI
 */
@Fork
@BenchmarkMode(Mode.Throughput)
public class SerializationBenchmark {
	private static final ComplexTestClass objectToSerialize = instanciateAndInitializeComplexClass();
	private static final Protobuf.ComplexTestClass protoObjectToSerialize = instanciateAndInitializeProtoComplexClass();
	private static final AbstractBase64Serializer<ComplexTestClass> base64Serializer = new Base64Serializer<>();
	private static final AbstractBase64Serializer<ComplexTestClass> base64UrlSerializer = new Base64UrlSerializer<>();
	private static final JavaSerializer<ComplexTestClass> javaSerializer = new JavaSerializer<>();
	private static ProtobufSerializer<Protobuf.ComplexTestClass> protobufSerializer;
	private static final XmlSerializer<ComplexTestClass> xmlSerializer = new XmlSerializer<>(ComplexTestClass.class);
	private static final ObjectMapper jsonSerializer = new ObjectMapper();

	static {
		try {
			protobufSerializer = new ProtobufSerializer<>(Protobuf.ComplexTestClass.class);
		} catch (ProtobufSerializerException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	@Benchmark
	public void benchBase64Serializer() throws SerializationException, DeserializationException {
		// Serialization
		String ser = base64Serializer.serialize(objectToSerialize);

		// Unserialization
		base64Serializer.deserialize(ser);

	}

	@Benchmark
	public void benchBase64UrlSerializer() throws SerializationException, DeserializationException {
		// Serialization
		String ser = base64UrlSerializer.serialize(objectToSerialize);

		// Unserialization
		base64UrlSerializer.deserialize(ser);
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
