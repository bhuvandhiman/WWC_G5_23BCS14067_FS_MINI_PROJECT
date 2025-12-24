Mini Project: Student Course Registration REST
API (With Spring Security)
Objective
Build a secure RESTful backend service that manages students registering for courses. The
application uses Spring Security for authentication and authorization while allowing public access to
user registration and login endpoints.
Student Model Requirements
• id (int) – Unique student ID
• name (String) – Student name
• course (String) – Registered course
Security Requirements (Spring Security)
• User registration and login endpoints are publicly accessible
• Registration endpoint allows new users to create an account without authentication
• Login endpoint authenticates users and generates a security token
• All student-related APIs are secured and require authentication
• Unauthorized access to secured APIs is restricted
Authentication APIs (Public)
Register User
Endpoint: POST /auth/register
Allows new users to register without authentication.
Login User
Endpoint: POST /auth/login
Accepts username and password in JSON format.
Returns authentication token on successful login.
Responses:
• 200 OK – Login successful
• 401 UNAUTHORIZED – Invalid credentials
Student API Requirements (Secured)
1. Register Student
Endpoint: POST /students
Validations:
• name and course must not be null or empty
• id must be unique
Responses:
• 201 CREATED – Registration successful
• 400 BAD REQUEST – Validation failed
• 409 CONFLICT – Duplicate student ID
2. Get All Students
Endpoint: GET /students
Returns a list of all registered students.
Returns an empty list if no students exist.
Status: 200 OK
3. Get Student by ID
Endpoint: GET /students/{id}
Responses:
• 200 OK – Student found
• 404 NOT FOUND – Student not found
4. Delete Student
Endpoint: DELETE /students/{id}
Responses:
• 200 OK – Student deleted successfully
• 404 NOT FOUND – Student does not exist
Technologies Used
• Java
• Spring Boot
• Spring Security
• REST APIs
• Mave