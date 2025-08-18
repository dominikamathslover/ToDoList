package org.create.todolist.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.create.todolist.model.Task;
import org.create.todolist.data.TaskRepository;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DownloadControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void setup() {
        taskRepository.deleteAll();
    }

    @Nested
    class ExportToDoListPdf{
        @Test
        @DisplayName("shouldGeneratePdfWithTasks")
        void shouldGeneratePdfWithTasks() throws Exception {
            taskRepository.save(new Task("Read a book", "your favourite"));
            taskRepository.save(new Task("Go for a walk", "30 minutes"));

            MvcResult result = mvc.perform(get("/to-do-list/pdf"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_PDF))
                    .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"todolist.pdf\""))
                    .andReturn();

            byte[] pdfBytes = result.getResponse().getContentAsByteArray();
            Assertions.assertTrue(pdfBytes.length > 0);

        }
    }



}