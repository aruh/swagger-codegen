package io.swagger.codegen.typescript.typescriptangularjson2typescript;


import io.swagger.codegen.CodegenModel;
import io.swagger.codegen.CodegenProperty;
import io.swagger.codegen.languages.TypeScriptAngularJSON2TypeScriptClientCodegen;
import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.properties.*;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This test checks whether model property types are converted to the matching json2typescript type that are used
 * in the generated annotation.
 */
public class Json2TypeScriptTypeConversionTest {

    private TypeScriptAngularJSON2TypeScriptClientCodegen codegen;

    @BeforeTest
    public void setUp() {
        codegen = new TypeScriptAngularJSON2TypeScriptClientCodegen();
    }

    @Test(description = "should convert numeric types to TypeScripts Number type for the annotation")
    public void testNumberConversion() {
        final Model model = new ModelImpl()
                .property("intProperty", new IntegerProperty())
                .property("longProperty", new LongProperty())
                .property("floatProperty", new FloatProperty())
                .property("doubleProperty", new DoubleProperty());

        final CodegenModel cm = postProcessGeneratedModel(codegen.fromModel("sample", model));

        Assert.assertTrue(cm.vars.size() == 4, "There should be four properties");

        CodegenProperty intProperty = getPropertyByName(cm, "intProperty");
        assertJ2TType(intProperty, TypeScriptAngularJSON2TypeScriptClientCodegen.TYPE_NUMBER);

        CodegenProperty longProperty = getPropertyByName(cm, "longProperty");
        assertJ2TType(longProperty, TypeScriptAngularJSON2TypeScriptClientCodegen.TYPE_NUMBER);

        CodegenProperty floatProperty = getPropertyByName(cm, "floatProperty");
        assertJ2TType(floatProperty, TypeScriptAngularJSON2TypeScriptClientCodegen.TYPE_NUMBER);

        CodegenProperty doubleProperty = getPropertyByName(cm, "doubleProperty");
        assertJ2TType(doubleProperty, TypeScriptAngularJSON2TypeScriptClientCodegen.TYPE_NUMBER);
    }


    @Test(description = "should convert StringProperties to TypeScripts String type")
    public void testStringConversion() {
        final Model model = new ModelImpl()
                .property("stringProperty", new StringProperty());

        final CodegenModel cm = postProcessGeneratedModel(codegen.fromModel("sample", model));
        CodegenProperty intProperty = getPropertyByName(cm, "stringProperty");
        assertJ2TType(intProperty, TypeScriptAngularJSON2TypeScriptClientCodegen.TYPE_STRING);
    }

    @Test(description = "should convert Date and DateTime properties to TypeScripts Date type")
    public void testDateConversion() {
        final Model model = new ModelImpl()
                .property("dateProperty", new DateProperty())
                .property("dateTimeProperty", new DateTimeProperty());

        final CodegenModel cm = postProcessGeneratedModel(codegen.fromModel("sample", model));
        CodegenProperty dateProperty = getPropertyByName(cm, "dateProperty");
        assertJ2TType(dateProperty, TypeScriptAngularJSON2TypeScriptClientCodegen.TYPE_DATE);

        CodegenProperty dateTimeProperty = getPropertyByName(cm, "dateTimeProperty");
        assertJ2TType(dateTimeProperty, TypeScriptAngularJSON2TypeScriptClientCodegen.TYPE_DATE);
    }

    private void assertJ2TType(CodegenProperty property, String typeName) {
        Assert.assertNotNull(property);
        Assert.assertNotNull(property.vendorExtensions);

        Map<String, Object> vendorExtensions = property.vendorExtensions;
        Assert.assertTrue(vendorExtensions.containsKey(TypeScriptAngularJSON2TypeScriptClientCodegen.KEY_J2T_DATA_TYPE));
        Assert.assertEquals(
                vendorExtensions.get(TypeScriptAngularJSON2TypeScriptClientCodegen.KEY_J2T_DATA_TYPE),
                typeName);
    }

    private CodegenProperty getPropertyByName(CodegenModel model, String propertyName) {
        if (model.vars == null || model.vars.isEmpty()) {
            return null;
        }

        for (CodegenProperty property : model.vars) {
            if (property.name.equals(propertyName)) {
                return property;
            }
        }
        return null;
    }

    private CodegenModel postProcessGeneratedModel(final CodegenModel cm) {
        Map<String, Object> modelInfo = new HashMap<>();
        modelInfo.put("model", cm);

        List<Map<String, Object>> modelInfos = new ArrayList<>();
        modelInfos.add(modelInfo);

        Map<String, Object> allModels = new HashMap<>();
        allModels.put("models", modelInfos);
        Map<String, Object> processedModels = codegen.postProcessModels(allModels);

        Map<String, Object> allProcessedModels = new HashMap<>();
        processedModels.put("classname", codegen.toModelName(cm.name));
        processedModels.putAll(codegen.additionalProperties());
        allProcessedModels.put(cm.name, processedModels);
        Map<String, Object> allPostProcessedModels = codegen.postProcessAllModels(allProcessedModels);

        List<Map<String, Object>> postProcessedModelList =
                (List<Map<String, Object>>)((Map<String, Object>) allPostProcessedModels.get(cm.name)).get("models");

        return (CodegenModel) postProcessedModelList.get(0).get("model");
    }

}
