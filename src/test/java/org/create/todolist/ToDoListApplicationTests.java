package org.create.todolist;

import org.create.todolist.controllers.ToDoListController;
import org.create.todolist.data.TaskRepository;
import org.create.todolist.model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ToDoListApplicationTests {

    @Autowired
    private ToDoListController taskController;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void contextLoads() {
        assertNotNull(taskController);
        assertNotNull(taskRepository);
    }

    @Test
    void shouldSaveTaskToDatabase() {
        Task task = new Task("Test", "description");
        Task saved = taskRepository.save(task);
        assertNotNull(saved.getId());
    }



}
