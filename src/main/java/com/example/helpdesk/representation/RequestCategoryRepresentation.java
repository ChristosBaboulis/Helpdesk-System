package com.example.helpdesk.representation;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class RequestCategoryRepresentation {

    public Integer id;
    public String categoryType;
    public SpecialtyRepresentation specialty;

}
