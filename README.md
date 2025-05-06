# Roadmap Todo List API
 RESTful API for users to manage their to-do list

Visit application at localhost:8094

Endpoints
1. POST /register: Register new user
2. POST /login: Log in to existing user
3. POST /todos: Create new to-do item for user 
4. GET /todos: List all to-do items
5. PUT /todos/{todoId}: Update an existing to-do item
6. DELETE /todos/{todoId}: Update an existing to-do item

Note: /todos endpoints require authorization in the request header using a token from the /register or /login endpoints
