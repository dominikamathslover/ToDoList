package org.create.todolist.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
public class Task {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;

    @Setter(AccessLevel.NONE)
    private LocalDate dueDate;

    private boolean completed;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.dueDate = LocalDate.now();
        this.completed = false;
    }

    public Task() {
        this.title = "default title";
    }

    @Override
    public String toString() {
        return String.format("%s %s %s | %s",
                title,
                description,
                dueDate.toString(),
                completed ? "Completed" : "In Progress");
    }
}
