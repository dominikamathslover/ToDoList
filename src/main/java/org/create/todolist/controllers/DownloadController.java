package org.create.todolist.controllers;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.create.todolist.data.TaskRepository;
import org.create.todolist.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DownloadController {

    private final SpringTemplateEngine templateEngine;
    private final TaskRepository taskRepository;

    @Autowired
    public DownloadController(SpringTemplateEngine templateEngine, TaskRepository taskRepository) {
        this.templateEngine = templateEngine;
        this.taskRepository = taskRepository;
    }

    @GetMapping(value = "/to-do-list/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> exportToDoListPdf() {
        try {
            List<Task> taskList = taskRepository.findAll();
            List<String> taskDetails = taskList.stream()
                    .map(task -> task.toString())
                    .collect(Collectors.toList());

            Context context = new Context();
            context.setVariable("details", taskDetails);
            String htmlContent = templateEngine.process("downloadPage", context);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode()
                    .withHtmlContent(htmlContent, null)
                    .toStream(baos).run();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"todolist.pdf\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(baos.toByteArray());

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
