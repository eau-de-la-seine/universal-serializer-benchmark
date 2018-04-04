package fr.ekinci.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.junit.Test;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import static fr.ekinci.universalserializerbenchmark.utils.Utils.instanciateAndInitializeComplexClass;
import static fr.ekinci.universalserializerbenchmark.utils.Utils.instanciateAndInitializeProtoComplexClass;

public class FooBar {
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

	/**
	 * Size in bytes
	 * 		base64   : 1024
	 * 		base64Url: 1024
	 * 		java     : 766
	 * 		protoBuf : 93
	 * 		xml      : 470
	 * 		json     : 206
	 */
	@Test
	public void length() throws SerializationException, JsonProcessingException, UnsupportedEncodingException {
		String.format("base64   : %s", base64Serializer.serialize(objectToSerialize).getBytes(StandardCharsets.UTF_8).length);
		String.format("base64Url: %s", base64UrlSerializer.serialize(objectToSerialize).getBytes(StandardCharsets.UTF_8).length);
		String.format("java     : %s", javaSerializer.serialize(objectToSerialize).length);
		String.format("protoBuf : %s", protobufSerializer.serialize(protoObjectToSerialize).length);
		String.format("xml      : %s", xmlSerializer.serialize(objectToSerialize).getBytes(StandardCharsets.UTF_8).length);
		String.format("json     : %s", jsonSerializer.writeValueAsString(objectToSerialize).getBytes(StandardCharsets.UTF_8).length);
	}

	/**
	 * base64   : rO0ABXNyADxmci5la2luY2kudW5pdmVyc2Fsc2VyaWFsaXplcmJlbmNobWFyay5wb2pvLkNvbXBsZXhUZXN0Q2xhc3OglBPNH4GfsAIABEwABmF0dHIxMHQAEExqYXZhL3V0aWwvTGlzdDtMAAZhdHRyMTFxAH4AAUwABmF0dHIxMnQAPkxmci9la2luY2kvdW5pdmVyc2Fsc2VyaWFsaXplcmJlbmNobWFyay9wb2pvL0NvbXBsZXhUZXN0Q2xhc3M7TAAGYXR0cjEzcQB+AAF4cgA7ZnIuZWtpbmNpLnVuaXZlcnNhbHNlcmlhbGl6ZXJiZW5jaG1hcmsucG9qby5TaW1wbGVUZXN0Q2xhc3PeB7jNNlxAiQIACUIABWF0dHIxUwAFYXR0cjJJAAVhdHRyM0oABWF0dHI0RgAFYXR0cjVEAAVhdHRyNloABWF0dHI3QwAFYXR0cjhMAAVhdHRyOXQAEkxqYXZhL2xhbmcvU3RyaW5nO3hwAQACAAAAAwAAAAAAAAAEQKAAAEAYAAAAAAAAAQA4dAATJsOpJygtw6hfw6fDoCk94oKsJHNyADRmci5la2luY2kudW5pdmVyc2Fsc2VyaWFsaXplcmJlbmNobWFyay51dGlscy5VdGlscyQxG0+5CaiJlpwCAAB4cgATamF2YS51dGlsLkFycmF5TGlzdHiB0h2Zx2GdAwABSQAEc2l6ZXhwAAAAA3cEAAAAA3NyABBqYXZhLmxhbmcuRG91YmxlgLPCSilr+wQCAAFEAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cEAkMzMzMzMzc3EAfgAKQCRmZmZmZmZzcQB+AApAJJmZmZmZmnhzcgA0ZnIuZWtpbmNpLnVuaXZlcnNhbHNlcmlhbGl6ZXJiZW5jaG1hcmsudXRpbHMuVXRpbHMkMkde3lqarWXZAgAAeHEAfgAIAAAAA3cEAAAAA3QABDExLjF0AAQxMS4ydAAEMTEuM3hwcA==
	 * base64Url: rO0ABXNyADxmci5la2luY2kudW5pdmVyc2Fsc2VyaWFsaXplcmJlbmNobWFyay5wb2pvLkNvbXBsZXhUZXN0Q2xhc3OglBPNH4GfsAIABEwABmF0dHIxMHQAEExqYXZhL3V0aWwvTGlzdDtMAAZhdHRyMTFxAH4AAUwABmF0dHIxMnQAPkxmci9la2luY2kvdW5pdmVyc2Fsc2VyaWFsaXplcmJlbmNobWFyay9wb2pvL0NvbXBsZXhUZXN0Q2xhc3M7TAAGYXR0cjEzcQB-AAF4cgA7ZnIuZWtpbmNpLnVuaXZlcnNhbHNlcmlhbGl6ZXJiZW5jaG1hcmsucG9qby5TaW1wbGVUZXN0Q2xhc3PeB7jNNlxAiQIACUIABWF0dHIxUwAFYXR0cjJJAAVhdHRyM0oABWF0dHI0RgAFYXR0cjVEAAVhdHRyNloABWF0dHI3QwAFYXR0cjhMAAVhdHRyOXQAEkxqYXZhL2xhbmcvU3RyaW5nO3hwAQACAAAAAwAAAAAAAAAEQKAAAEAYAAAAAAAAAQA4dAATJsOpJygtw6hfw6fDoCk94oKsJHNyADRmci5la2luY2kudW5pdmVyc2Fsc2VyaWFsaXplcmJlbmNobWFyay51dGlscy5VdGlscyQxG0-5CaiJlpwCAAB4cgATamF2YS51dGlsLkFycmF5TGlzdHiB0h2Zx2GdAwABSQAEc2l6ZXhwAAAAA3cEAAAAA3NyABBqYXZhLmxhbmcuRG91YmxlgLPCSilr-wQCAAFEAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cEAkMzMzMzMzc3EAfgAKQCRmZmZmZmZzcQB-AApAJJmZmZmZmnhzcgA0ZnIuZWtpbmNpLnVuaXZlcnNhbHNlcmlhbGl6ZXJiZW5jaG1hcmsudXRpbHMuVXRpbHMkMkde3lqarWXZAgAAeHEAfgAIAAAAA3cEAAAAA3QABDExLjF0AAQxMS4ydAAEMTEuM3hwcA==
	 * java     : ACED00057372003C66722E656B696E63692E756E6976657273616C73657269616C697A657262656E63686D61726B2E706F6A6F2E436F6D706C657854657374436C617373A09413CD1F819FB00200044C00066174747231307400104C6A6176612F7574696C2F4C6973743B4C000661747472313171007E00014C000661747472313274003E4C66722F656B696E63692F756E6976657273616C73657269616C697A657262656E63686D61726B2F706F6A6F2F436F6D706C657854657374436C6173733B4C000661747472313371007E00017872003B66722E656B696E63692E756E6976657273616C73657269616C697A657262656E63686D61726B2E706F6A6F2E53696D706C6554657374436C617373DE07B8CD365C40890200094200056174747231530005617474723249000561747472334A00056174747234460005617474723544000561747472365A0005617474723743000561747472384C000561747472397400124C6A6176612F6C616E672F537472696E673B787001000200000003000000000000000440A00000401800000000000001003874001326C3A927282DC3A85FC3A7C3A0293DE282AC247372003466722E656B696E63692E756E6976657273616C73657269616C697A657262656E63686D61726B2E7574696C732E5574696C7324311B4FB909A889969C020000787200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A65787000000003770400000003737200106A6176612E6C616E672E446F75626C6580B3C24A296BFB0402000144000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B020000787040243333333333337371007E000A40246666666666667371007E000A402499999999999A787372003466722E656B696E63692E756E6976657273616C73657269616C697A657262656E63686D61726B2E7574696C732E5574696C732432475EDE5A9AAD65D90200007871007E00080000000377040000000374000431312E3174000431312E3274000431312E33787070
	 * protoBuf : 0A01011002180320042D0000A04031000000000000184038014201384A1326C3A927282DC3A85FC3A7C3A0293DE282AC245218333333333333244066666666666624409A999999999924405A0431312E315A0431312E325A0431312E33
	 * xml      : <?xml version="1.0" encoding="UTF-8" standalone="yes"?><complexTestClass><attr1>1</attr1><attr2>2</attr2><attr3>3</attr3><attr4>4</attr4><attr5>5.0</attr5><attr6>6.0</attr6><attr7>true</attr7><attr8>56</attr8><attr9>&amp;é'(-è_çà)=€$</attr9><attr10>10.1</attr10><attr10>10.2</attr10><attr10>10.3</attr10><attr11>11.1</attr11><attr11>11.2</attr11><attr11>11.3</attr11></complexTestClass>
	 * json     : {"attr1":1,"attr2":2,"attr3":3,"attr4":4,"attr5":5.0,"attr6":6.0,"attr7":true,"attr8":"8","attr9":"&é'(-è_çà)=€$","attr10":[10.1,10.2,10.3],"attr11":["11.1","11.2","11.3"],"attr12":null,"attr13":null}
	 */
	@Test
	public void stringRepresentation() throws SerializationException, JsonProcessingException, UnsupportedEncodingException {
		String.format("base64   : %s", base64Serializer.serialize(objectToSerialize));
		String.format("base64Url: %s", base64UrlSerializer.serialize(objectToSerialize));
		String.format("java     : %s", DatatypeConverter.printHexBinary(javaSerializer.serialize(objectToSerialize)));
		String.format("protoBuf : %s", DatatypeConverter.printHexBinary(protobufSerializer.serialize(protoObjectToSerialize)));
		String.format("xml      : %s", xmlSerializer.serialize(objectToSerialize));
		String.format("json     : %s", jsonSerializer.writeValueAsString(objectToSerialize));
	}
}
