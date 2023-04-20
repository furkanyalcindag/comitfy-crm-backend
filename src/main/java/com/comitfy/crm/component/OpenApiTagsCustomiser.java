package com.comitfy.crm.component;

import io.swagger.v3.core.filter.SpecFilter;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.stereotype.Component;

@Component
public class OpenApiTagsCustomiser extends SpecFilter implements OpenApiCustomizer {
    @Override
    public void customise(OpenAPI openApi) {
        // remove the property reference controller
        openApi.getPaths().entrySet().removeIf(path -> path.getValue().readOperations().stream().anyMatch(
                operation -> operation.getTags().stream().anyMatch(tag -> tag.endsWith("property-reference-controller"))));

        openApi.getPaths().entrySet().removeIf(path -> path.getValue().readOperations().stream().anyMatch(
                operation -> operation.getTags().stream().anyMatch(tag -> tag.endsWith("entity-controller"))));

        openApi.getPaths().entrySet().removeIf(path -> path.getValue().readOperations().stream().anyMatch(
                operation -> operation.getTags().stream().anyMatch(tag -> tag.endsWith("search-controller"))));
        // rename the operation tags
       /* openApi.getPaths().values().stream().flatMap(pathItem -> pathItem.readOperations().stream())
                .forEach(operation -> {
                    String tagName = operation.getTags().get(0);
                    // rename the entity-controller tags
                    if (tagName.endsWith("entity-controller")) {
                        String entityName = tagName.substring(0, tagName.length() - 18);
                        String myTagValue = "my-entity-" + entityName;
                        // Replace with the new tag value
                        operation.getTags().set(0, myTagValue);

                    }
                    // rename the search-controller tags
                    else if (tagName.endsWith("search-controller")) {
                        String entityName = tagName.substring(0, tagName.length() - 18);
                        String myTagValue = "my-search-" + entityName;
                        // Replace with the new tag value
                        operation.getTags().set(0, myTagValue);
                    }
                });*/
        removeBrokenReferenceDefinitions(openApi);
    }
}
