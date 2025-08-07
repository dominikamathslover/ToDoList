package org.create.todolist.data;

import org.create.todolist.model.Status;
import org.create.todolist.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(Status status);
}

