package io.swagger.codegen.languages;

import io.swagger.codegen.CodegenModel;
import io.swagger.codegen.CodegenProperty;

import java.util.List;
import java.util.Map;

public class TypeScriptAngularJSON2TypeScriptClientCodegen extends TypeScriptAngularClientCodegen {

    public static final String TYPE_NUMBER = "Number";
    public static final String TYPE_STRING = "String";
    public static final String TYPE_BOOLEAN = "Boolean";
    public static final String TYPE_DATE = "Date";

    public static final String KEY_J2T_DATA_TYPE = "j2tDataType";
    public static final String KEY_J2T_DEFAULT_VALUE = "j2tDefaultValue";
    private static final String VALUE_NULL = "null";
    private static final String VALUE_UNDEFINED = "undefined";

    public TypeScriptAngularJSON2TypeScriptClientCodegen() {
        super();
        this.outputFolder = "generated-code/typescript-angular-json2typescript";

        embeddedTemplateDir = templateDir = "typescript-angular-json2typescript";
    }


    @Override
    public Map<String, Object> postProcessAllModels(Map<String, Object> objs) {
        Map<String, Object> result = super.postProcessAllModels(objs);

        for (Object modelContainer : result.values()) {
            List<Map<String, Object>> models =
                    ((List<Map<String, Object>>)((Map<String, Object>)modelContainer).get("models"));

            for (Map<String, Object> model : models) {
                List<CodegenProperty> properties = ((CodegenModel)model.get("model")).vars;
                for (CodegenProperty property : properties) {
                    property.vendorExtensions.put(KEY_J2T_DATA_TYPE, buildJ2TPropertyType(property));
                    if (property.isEnum) {
                        property.vendorExtensions.put(KEY_J2T_DATA_TYPE, property.datatypeWithEnum);
                    }

                    property.vendorExtensions.put(KEY_J2T_DEFAULT_VALUE, buildJ2TPropertyDefaultValue(property));
                }
            }

        }

        return result;
    }

    @Override
    public String getName() {
        return "typescript-angular-json2typescript";
    }

    @Override
    public String getHelp() {
        return "Generates a TypeScript Angular (2.x or 4.x) client library. " +
                "Models are annotated to be deserialized with json2typescript.";
    }

    private String buildJ2TPropertyDefaultValue(CodegenProperty prop) {
        if (prop.defaultValue.equals(VALUE_NULL)) {
            return VALUE_UNDEFINED;
        }

        return prop.defaultValue;
    }

    private String buildJ2TPropertyType(CodegenProperty prop) {
        if (!prop.isListContainer) return buildNonListType(prop);
        return "[" + buildJ2TPropertyType(prop.items) + "]";
    }

    private String buildNonListType(CodegenProperty prop) {
        if (prop.isEnum) {
            return prop.datatypeWithEnum;
        }
        if (prop.isString) return TYPE_STRING;
        if (prop.isBoolean) return TYPE_BOOLEAN;
        if (prop.isNumeric || prop.isInteger || prop.isLong || prop.isFloat || prop.isDouble) return TYPE_NUMBER;
        if (prop.isDateTime ||prop.isDate) return TYPE_DATE;
        return prop.datatype;
    }

}
