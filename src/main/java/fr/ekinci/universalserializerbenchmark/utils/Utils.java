package fr.ekinci.universalserializerbenchmark.utils;

import com.google.protobuf.ByteString;
import fr.ekinci.universalserializerbenchmark.pojo.ComplexTestClass;
import fr.ekinci.universalserializerbenchmark.protocolbuffers.Protobuf;

import java.util.ArrayList;

/**
 * @author Gokan EKINCI
 */
public class Utils {

	public static ComplexTestClass instanciateAndInitializeComplexClass() {
		final ComplexTestClass obj = new ComplexTestClass();
		obj.setAttr1((byte) 1);
		obj.setAttr2((short) 2);
		obj.setAttr3(3);
		obj.setAttr4(4L);
		obj.setAttr5(5f);
		obj.setAttr6(6d);
		obj.setAttr7(true);
		obj.setAttr8('8');
		obj.setAttr9("&é'(-è_çà)=€$");

		// Complex attributes initialization
		obj.setAttr10(new ArrayList<Double>() {{
			add(10.1d);
			add(10.2d);
			add(10.3d);
		}});

		obj.setAttr11(new ArrayList<String>() {{
			add("11.1");
			add("11.2");
			add("11.3");
		}});

		return obj;
	}

	public static Protobuf.ComplexTestClass instanciateAndInitializeProtoComplexClass() {
		final Protobuf.ComplexTestClass obj = Protobuf.ComplexTestClass.newBuilder()
		.setAttr1(ByteString.copyFrom(new byte[]{1}))
		.setAttr2((short) 2)
		.setAttr3(3)
		.setAttr4(4L)
		.setAttr5(5f)
		.setAttr6(6d)
		.setAttr7(true)
		.setAttr8("8")
		.setAttr9("&é'(-è_çà)=€$")

		// Complex attributes initialization
		.addAllAttr10(new ArrayList<Double>() {{
			add(10.1d);
			add(10.2d);
			add(10.3d);
		}})

		.addAllAttr11(new ArrayList<String>() {{
			add("11.1");
			add("11.2");
			add("11.3");
		}})

		.build();

		return obj;
	}
}
