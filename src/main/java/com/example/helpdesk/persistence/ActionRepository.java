package com.example.helpdesk.persistence;

import com.example.helpdesk.domain.Action;
import com.example.helpdesk.domain.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class ActionRepository implements PanacheRepositoryBase<Action, Integer> {
}
