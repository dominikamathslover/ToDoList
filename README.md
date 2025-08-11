To-Do List App
  A simple web application for managing daily tasks.

Getting Started
  Home Page: http://localhost:8080/

H2 Database Console: http://localhost:8080/h2-console

Features & Endpoints
1. Add a New Task
  POST /to-do-list

    Parameters:
      title (string, required)
      description (string, optional)
      date (defaults to today's date)
      !markAllCompleted 
      !id 

2. Display All Tasks
  GET /to-do-list

3.  Mark a Task as Completed
  POST /to-do-list

     Parameters:
      id (long, required) – ID of the task to mark as completed

5. Mark All Tasks as Completed
  POST /to-do-list

    Parameters:
      markAllCompleted (boolean)

5.  Download Task List as PDF
  GET /to-do-list/pdf


Technologies Used
Java / Spring Boot / Maven / H2 Database / Spring Data JPA / Thymeleaf / PDF generation library 
