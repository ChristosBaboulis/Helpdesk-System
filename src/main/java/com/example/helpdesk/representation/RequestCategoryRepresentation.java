package com.example.helpdesk.representation;

import com.example.helpdesk.domain.Customer;
import com.example.helpdesk.domain.Specialty;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class RequestCategoryRepresentation {
    public Integer id;
    public String categoryType;
    public SpecialtyRepresentation specialty;
}
