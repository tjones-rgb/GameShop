During development, several REST-related issues were identified and resolved. The API originally returned incorrect HTTP status codes for create and delete operations, which were updated to follow REST standards by returning 201 Created and 204 No Content. Error handling was improved so that requesting a deleted or non-existent category correctly returns 404 Not Found instead of 200 OK. Authentication-related issues were resolved by ensuring admin-only endpoints required proper authorization.

1. Incorrect HTTP Status for Create Category

Issue: POST /categories returned 200 OK after successfully creating a category.

Expected Behavior: REST standards and automated tests require 201 Created.

Fix: Added @ResponseStatus(HttpStatus.CREATED) to the addCategory controller method.

2. Incorrect HTTP Status for Delete Category

Issue: DELETE /categories/{id} returned 200 OK after deleting a category.

Expected Behavior: REST standards require 204 No Content for successful deletes.

Fix: Added @ResponseStatus(HttpStatus.NO_CONTENT) to the delete controller method.

3. Get Category by ID Returned 200 After Deletion

Issue: GET /categories/{id} returned 200 OK even after the category was deleted.

Root Cause: The DAO returned a Category object instead of null when no database record existed.

Fix: Updated getById() to return null when no result is found and added a 404 Not Found response in the controller using ResponseStatusException.

4. Unauthorized (401) Errors on Protected Endpoints

Issue: Admin-only endpoints returned 401 Unauthorized when accessed without proper authentication.

Cause: Requests were missing valid credentials or tokens required by Spring Security.

Fix: Verified security configuration and ensured admin requests included proper authentication.

5. Frontend Price Filter Displayed Incorrect Labels

Issue: Both price filter input fields displayed “Minimum”, causing user confusion.

Cause: Duplicate placeholder text in the frontend HTML.

Fix: Updated the second input placeholder to “Maximum” in index.html.

6. REST Error Handling Improvements
Issue: Missing or deleted resources returned incorrect HTTP status codes.
Fix: Added explicit error handling using ResponseStatusException to return 404 Not Found when resources do not exist.

<img width="733" height="264" alt="Screenshot 2025-12-19 085022" src="https://github.com/user-attachments/assets/29f3954d-5140-488a-ab08-152bb6b939b6" />
<img width="679" height="283" alt="Screenshot 2025-12-19 085205" src="https://github.com/user-attachments/assets/67ecf8b9-8b80-4a76-bbbc-aec26a63e2fe" />
<img width="504" height="253" alt="Screenshot 2025-12-19 085215" src="https://github.com/user-attachments/assets/54616a02-5386-4280-bf39-bd7b82bc7c98" />
<img width="461" height="198" alt="Screenshot 2025-12-19 085156" src="https://github.com/user-attachments/assets/e9c46792-d54a-4091-a9e8-87e2d7d520b3" />

