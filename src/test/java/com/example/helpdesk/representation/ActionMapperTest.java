package com.example.helpdesk.representation;

import com.example.helpdesk.domain.Action;
import com.example.helpdesk.persistence.ActionRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

@QuarkusTest
public class ActionMapperTest {

    @Inject
    ActionMapper actionMapper;
    @Inject
    ActionRepository actionRepository;

    @Test
    @Transactional
    public void test() {
        ActionRepresentation representation = new ActionRepresentation();
        representation.title = "title";
        representation.description = "description";
        representation.callDuration = 10;
        representation.submissionDate = LocalDate.now();

        Action action = actionMapper.toModel(representation);
        Assertions.assertNotNull(action);

        Assertions.assertEquals("title", action.getTitle());
        Assertions.assertEquals("description", action.getDescription());
        Assertions.assertEquals(10, representation.callDuration);
        Assertions.assertEquals(LocalDate.now(), action.getSubmissionDate());

        actionRepository.persist(action);

        ActionRepresentation representation2 = actionMapper.toRepresentation(action);
        Assertions.assertNotNull(representation2);
        Assertions.assertEquals("title", representation2.title);
        Assertions.assertEquals("description", representation2.description);
        Assertions.assertNotNull(representation2.id);
        Assertions.assertEquals(LocalDate.now() ,representation2.submissionDate);
    }

}
