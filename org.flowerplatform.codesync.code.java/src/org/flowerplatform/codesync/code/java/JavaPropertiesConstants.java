package org.flowerplatform.codesync.code.java;

/**
 * @author Mariana Gheorghe
 */
public class JavaPropertiesConstants {
	
	////////////////////////////////////////////
	// Properties constants
	////////////////////////////////////////////
	
	// Value features
	
	public static final String MODIFIERS = "modifiers";
	public static final String DOCUMENTATION = "documentation";
	public static final String TYPED_ELEMENT_TYPE = "typedElementType";
	public static final String SUPER_CLASS = "superClass";
	public static final String ATTRIBUTE_INITIALIZER = "attributeInitializer";
	public static final String OPERATION_HAS_BODY = "hasBody";
	public static final String ANNOTATION_VALUE_VALUE = "annotationValueValue";
	public static final String ANNOTATION_MEMBER_DEFAULT_VALUE = "annotationMemberDefaultValue";
	
	// Containment features
	
	public static final String TYPE_MEMBERS = "typeMembers";
	public static final String OPERATION_PARAMETERS = "operationParameters";
	public static final String ANNOTATION_VALUES = "annotationValues";
	public static final String SUPER_INTERFACES = "superInterfaces";
	public static final String ENUM_CONSTANT_ARGUMENTS = "enumConstantArguments";

	////////////////////////////////////////////
	// Icon and decorator constants
	////////////////////////////////////////////
	
	public static final String IMG_PACKAGE = "package_obj.gif";
	public static final String IMG_FILE = "jcu_obj.gif";
	
	public static final String IMG_TYPE_CLASS = "class_obj.gif";
	public static final String IMG_TYPE_INTERFACE = "interface_obj.gif";
	public static final String IMG_TYPE_ENUM = "enum_obj.gif";
	public static final String IMG_TYPE_ANNOTATION = "annotation_obj.gif";
	
	public static final String IMG_FIELD = "field_obj.gif";
	public static final String IMG_METHOD = "method_obj.gif";
	public static final String IMG_LOCAL_VAR = "localvariable_obj.gif";
	
	public static final String IMG_ANNOTATION = "annotation_alt_obj.gif";
	
	private static final String WIZ = "wiz/";
	
	public static final String IMG_WIZ_PACKAGE = WIZ + "newpack_wiz.gif";
	public static final String IMG_WIZ_TYPE_CLASS = WIZ + "newclass_wiz.gif";
	public static final String IMG_WIZ_TYPE_INTERFACE = WIZ + "newint_wiz.gif";
	public static final String IMG_WIZ_TYPE_ENUM = WIZ + "newenum_wiz.gif";
	public static final String IMG_WIZ_TYPE_ANNOTATION = WIZ + "newannotation_wiz.gif";
	
	private static final String DECORATOR = "decorator/";
	
	public static final String DECORATOR_ABSTRACT = DECORATOR + "abstract.gif";
	public static final String DECORATOR_FINAL = DECORATOR + "final.gif";
	public static final String DECORATOR_VOLATILE = DECORATOR + "volatile.gif";
	public static final String DECORATOR_STATIC = DECORATOR + "static.gif";
	public static final String DECORATOR_NATIVE = DECORATOR + "native.gif";
	public static final String DECORATOR_TRANSIENT = DECORATOR + "transient.gif";
	public static final String DECORATOR_SYNCHRONIZED = DECORATOR + "synchronized.gif";
	
	public static final String VISIBILITY_PRIVATE = "_private";
	public static final String VISIBILITY_PROTECTED = "_protected";
	public static final String VISIBILITY_DEFAULT = "_default";
	
	public static String getImagePath(String image) {
		return "org.flowerplatform.codesync.code.java/images/" + image;
	}
	
	public static String getImagePathFromPublicResources(String image) {
		return CodeSyncCodeJavaPlugin.getInstance().getResourceUrl("/images/" + image);
	}
	
}
