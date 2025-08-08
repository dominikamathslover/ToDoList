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

    @PostMapping("/to-do-list")
    public String saveTask(@RequestParam String title,
                           @RequestParam(required = false) String description) {
        taskRepository.save(new Task(title, description));
        return "redirect:/";
    }

    @GetMapping("/to-do-list")
    public String getTasks(Model model) {
        List<Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);
        return "to-do-list";
    }


}