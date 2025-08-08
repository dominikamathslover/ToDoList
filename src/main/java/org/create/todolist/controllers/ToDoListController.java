package org.create.todolist.controllers;

import org.create.todolist.data.TaskRepository;
import org.create.todolist.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ToDoListController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/to-do-list")
    public String getTasks(Model model) {
        List<Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);
        return "to-do-list";
    }

    @PostMapping(value = "/to-do-list", params = {"!markAllCompleted", "!id"})
    public String saveTask(@RequestParam String title,
                           @RequestParam(required = false) String description) {
        taskRepository.save(new Task(title, description));
        return "redirect:/";
    }

    @PostMapping(value = "/to-do-list", params = "id")
    public String updateTask(@RequestParam Long id,
                             @RequestParam(required = false) String completed) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        task.setCompleted(completed != null); //
        taskRepository.save(task);

        return "redirect:/to-do-list";
    }

    @PostMapping(value = "/to-do-list", params = "markAllCompleted")
    public String markAllTasksCompleted() {
        List<Task> tasks = taskRepository.findAll();

        if (tasks.isEmpty()) {
            return "redirect:/to-do-list";
        }

        tasks.forEach(task -> task.setCompleted(true));
        taskRepository.saveAll(tasks);

        return "redirect:/to-do-list";
    }


}