package org.create.todolist.controllers;

import org.create.todolist.data.TaskRepository;
import org.create.todolist.model.Task;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class ToDoListControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TaskRepository taskRepository;

    Task task;


    @BeforeEach
    public void setup() {
        taskRepository.deleteAll();
        task = new Task("Read a book", "your favourite");
        taskRepository.save(task);

    }

    @Test
    @DisplayName("getTasks")
    void getTasks() throws Exception {
        mvc.perform(get("/to-do-list"))
                .andExpect(status().isOk())
                .andExpect(view().name("to-do-list"))
                .andExpect(model().attributeExists("tasks"))
                .andExpect(content().string(containsString("Read a book")));
    }


    @Nested
    class SaveTask {
        @Test
        public void saveTaskAndRedirectToHome() throws Exception {
            mvc.perform(post("/to-do-list")
                            .param("title", "Read a book")
                            .param("description", "your favourite")
                    )
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/"));

            var tasks = taskRepository.findAll();
            assertThat(tasks).hasSize(2);
            assertThat(tasks.get(1).getTitle()).isEqualTo("Read a book");
            assertThat(tasks.get(1).getDescription()).isEqualTo("your favourite");
        }

        @Test
        @DisplayName("saveTaskWithoutTitle")
        public void saveTaskWithoutTitle() throws Exception {
            mvc.perform(post("/to-do-list")
                            .param("description", "without title")
                    )
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class UpdateTask {
        @Test
        public void updateTaskMarkTaskAsCompleted() throws Exception {
            mvc.perform(post("/to-do-list")
                            .param("id", String.valueOf(task.getId()))
                            .param("completed", "true")
                    )
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/to-do-list"));

            Task updated = taskRepository.findById(task.getId()).orElseThrow();
            assertThat(updated.isCompleted()).isTrue();
        }

        @Test
        public void updateTaskWhenCompletedParamIsMissing() throws Exception {
            task.setCompleted(true);
            taskRepository.save(task);

            mvc.perform(post("/to-do-list")
                            .param("id", String.valueOf(task.getId())))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/to-do-list"));

            Task updated = taskRepository.findById(task.getId()).orElseThrow();
            assertThat(updated.isCompleted()).isFalse();
        }

        @Test
        @DisplayName("Invalid id")
        public void updateTaskThrowExceptionWhenTaskNotFound() throws Exception {
            Long nonExistentId = 99L;

            mvc.perform(post("/to-do-list")
                            .param("id", String.valueOf(nonExistentId))
                            .param("completed", "true")
                    )
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class MarkAllTasksCompleted {

        @Test
        void shouldMarkAllTasksAsCompletedAndRedirect() throws Exception {
            mvc.perform(MockMvcRequestBuilders.post("/to-do-list")
                            .param("markAllCompleted", "")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/to-do-list"));
            List<Task> tasks = taskRepository.findAll();
            Assertions.assertEquals(1, tasks.size());
            assertTrue(tasks.get(0).isCompleted());
        }
    }

    @Nested
    class EmptyList {
        @Test
        @DisplayName("shouldRedirectToEmptyListWhenNoTasksExist")
        void shouldRedirectToEmptyListWhenNoTasksExist() throws Exception {
            taskRepository.deleteAll();

            mvc.perform(get("/to-do-list"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/empty-list"));
            assertTrue(taskRepository.findAll().isEmpty());
        }

        @Test
        @DisplayName("GET /empty-list should return empty-list view")
        void shouldReturnEmptyListView() throws Exception {
            mvc.perform(get("/empty-list"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("empty-list"));
        }
    }

}