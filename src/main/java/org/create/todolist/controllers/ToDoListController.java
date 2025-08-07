package org.create.todolist.controllers;

import org.create.todolist.data.TaskRepository;
import org.create.todolist.model.Status;
import org.create.todolist.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.table.TableRowSorter;
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

    @DeleteMapping("/to-do-list")
    public String deleteTask() {
        List<Task> completedTasks = taskRepository.findByStatus(Status.COMPLETED);
        taskRepository.deleteAll(completedTasks);
        return "redirect:/to-do-list";
    }

//    @PostMapping("/to-do-list")
//    public String updateStatus(@PathVariable Long id,
//                               @RequestParam(required = false) String status) {
//        Task task = taskRepository.findById(id).orElseThrow();
//
//        if ("COMPLETED".equals(status)) {
//            task.setStatus(Status.COMPLETED);
//        } else {
//            task.setStatus(Status.IN_PROGRESS);
//        }
//
//        taskRepository.save(task);
//        return "redirect:/tasks";
//    }

}