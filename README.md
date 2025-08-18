To-Do List App
  A simple web application for managing daily tasks.

Getting Started
  Home Page: http://localhost:8080/

H2 Database Console: http://localhost:8080/h2-console

Features & Endpoints
1. Add a new task
  POST  http://localhost:8080/to-do-list

    Parameters:
      title (string, required)
      description (string, optional)
      date (defaults to today's date)
      !markAllCompleted 
      !id 

2. Display all tasks
  GET  http://localhost:8080/to-do-list

3.  Mark a task as completed
  POST  http://localhost:8080/to-do-list

     Parameters:
      id (long, required) – ID of the task to mark as completed

5. Mark all tasks as completed
  POST  http://localhost:8080/to-do-list

    Parameters:
      markAllCompleted (boolean)

5. Download a task list as PDF
  GET  http://localhost:8080/to-do-list/pdf

6. Message if the to-do list is empty
  GET http://localhost:8080/empty-list"


Technologies Used
Java / Spring Boot / Maven / H2 Database / Spring Data JPA / Thymeleaf / JUnit5 / MockMvc / PDF generation library 
