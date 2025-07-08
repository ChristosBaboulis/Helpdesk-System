package com.example.helpdesk.domain;

import com.example.helpdesk.util.SystemDate;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Actions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "action_type", discriminatorType = DiscriminatorType.STRING)
public class Action {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;

    @Column(name = "title", length = 50, nullable = false)
    protected String title;

    @Column(name = "description", length = 1000)
    protected String description;

    @Column(name = "submission_date", length = 10, nullable = false)
    protected LocalDate submissionDate = SystemDate.now();

    public Action() {}

    //Full Args constructor - for testing purposes
    public Action(String title, String description, LocalDate submissionDate) {
        this.title = title;
        this.description = description;
        this.submissionDate = submissionDate;
    }

    //Constructor to be used
    public Action(String title, String description) {
        this.title = title;
        this.description = description;
    }

    //Constructor to be used
    public Action(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

}
