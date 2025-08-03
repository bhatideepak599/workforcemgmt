# Workforce Management API - Postman Testing Guide

**Author: Deepak Bhati**

## Base Configuration

- **Base URL**: `http://localhost:8080`
- **Content-Type**: `application/json` (for POST/PUT requests)

> 💡 **Tip**: Click the copy button (📋) next to each code block to easily copy the content to your clipboard for Postman testing.

---

## 1. Create Tasks

**Endpoint**: `POST /api/v1/tasks`

### Request Body:
<details>
<summary>📋 Click to copy JSON</summary>

```json
{
  "requests": [
    {
      "title": "Customer Onboarding Task",
      "description": "Complete onboarding process for new customer",
      "start_date": "2025-08-02",
      "due_date": "2025-08-10",
      "assignee_id": 1,
      "assignee_name": "John Doe",
      "customer_reference": "CUST001",
      "priority": "HIGH",
      "created_by": "manager"
    },
    {
      "title": "Follow-up Call",
      "description": "Schedule follow-up call with existing customer",
      "start_date": "2025-08-03",
      "due_date": "2025-08-05",
      "assignee_id": 2,
      "assignee_name": "Jane Smith",
      "customer_reference": "CUST002",
      "priority": "MEDIUM",
      "created_by": "team_lead"
    }
  ]
}
```
</details>

### Expected Response:
<details>
<summary>📋 Click to copy JSON</summary>

```json
{
  "success": true,
  "message": "Tasks created successfully",
  "data": [
    {
      "id": 1,
      "title": "Customer Onboarding Task",
      "description": "Complete onboarding process for new customer",
      "status": "ACTIVE",
      "priority": "HIGH",
      "start_date": "2025-08-02",
      "due_date": "2025-08-10",
      "assignee_id": 1,
      "assignee_name": "John Doe",
      "customer_reference": "CUST001",
      "created_at": "2025-08-02T18:00:00.000000000",
      "updated_at": "2025-08-02T18:00:00.000000000",
      "created_by": "manager",
      "updated_by": "manager",
      "activities": null,
      "comments": null
    },
    {
      "id": 2,
      "title": "Follow-up Call",
      "description": "Schedule follow-up call with existing customer",
      "status": "ACTIVE",
      "priority": "MEDIUM",
      "start_date": "2025-08-03",
      "due_date": "2025-08-05",
      "assignee_id": 2,
      "assignee_name": "Jane Smith",
      "customer_reference": "CUST002",
      "created_at": "2025-08-02T18:00:00.000000000",
      "updated_at": "2025-08-02T18:00:00.000000000",
      "created_by": "team_lead",
      "updated_by": "team_lead",
      "activities": null,
      "comments": null
    }
  ],
  "error_code": null,
  "timestamp": 1754157600000
}
```
</details>

---

## 2. Get All Tasks (No Filters)

    **Endpoint**: `GET /api/v1/tasks`

### Expected Response:
<details>
<summary>📋 Click to copy JSON</summary>

```json
{
  "success": true,
  "message": "All tasks retrieved successfully",
  "data": [
    {
      "id": 1,
      "title": "Customer Onboarding Task",
      "description": "Complete onboarding process for new customer",
      "status": "CANCELLED",
      "priority": "HIGH",
      "start_date": "2025-08-02",
      "due_date": "2025-08-10",
      "assignee_id": 1,
      "assignee_name": "John Doe",
      "customer_reference": "CUST001",
      "created_at": "2025-08-02T18:00:00.000000000",
      "updated_at": "2025-08-02T18:05:00.000000000",
      "created_by": "manager",
      "updated_by": "manager",
      "activities": null,
      "comments": null
    },
    {
      "id": 2,
      "title": "Follow-up Call",
      "description": "Schedule follow-up call with existing customer",
      "status": "ACTIVE",
      "priority": "MEDIUM",
      "start_date": "2025-08-03",
      "due_date": "2025-08-05",
      "assignee_id": 2,
      "assignee_name": "Jane Smith",
      "customer_reference": "CUST002",
      "created_at": "2025-08-02T18:00:00.000000000",
      "updated_at": "2025-08-02T18:00:00.000000000",
      "created_by": "team_lead",
      "updated_by": "team_lead",
      "activities": null,
      "comments": null
    },
    {
      "id": 3,
      "title": "Customer Onboarding Task",
      "description": "Complete onboarding process for new customer",
      "status": "ACTIVE",
      "priority": "HIGH",
      "start_date": "2025-08-02",
      "due_date": "2025-08-10",
      "assignee_id": 3,
      "assignee_name": "Mike Johnson",
      "customer_reference": "CUST001",
      "created_at": "2025-08-02T18:10:00.000000000",
      "updated_at": "2025-08-02T18:10:00.000000000",
      "created_by": "manager",
      "updated_by": "manager",
      "activities": null,
      "comments": null
    }
  ],
  "error_code": null,
  "timestamp": 1754157600000
}
```
</details>

**Note**: Returns ALL tasks including ACTIVE, COMPLETED, and CANCELLED tasks. This is different from the filtered endpoints.

---

## 3. Get Task by ID (with Full History)

**Endpoint**: `GET /api/v1/tasks/{id}`

### Example: `GET /api/v1/tasks/1`

### Expected Response:
<details>
<summary>📋 Click to copy JSON</summary>

```json
{
  "success": true,
  "message": "Task retrieved successfully",
  "data": {
    "id": 1,
    "title": "Customer Onboarding Task",
    "description": "Complete onboarding process for new customer",
    "status": "ACTIVE",
    "priority": "HIGH",
    "start_date": "2025-08-02",
    "due_date": "2025-08-10",
    "assignee_id": 1,
    "assignee_name": "John Doe",
    "customer_reference": "CUST001",
    "created_at": "2025-08-02T18:00:00.000000000",
    "updated_at": "2025-08-02T18:00:00.000000000",
    "created_by": "manager",
    "updated_by": "manager",
    "activities": [
      {
        "id": 1,
        "task_id": 1,
        "action": "TASK_CREATED",
        "performed_by": "manager",
        "timestamp": "2025-08-02T18:00:00.000000000",
        "details": "Task created with title: Customer Onboarding Task"
      }
    ],
    "comments": []
  },
  "error_code": null,
  "timestamp": 1754157600000
}
```
</details>

---

## 4. Update Task

**Endpoint**: `PUT /api/v1/tasks`

### Request Body:
<details>
<summary>📋 Click to copy JSON</summary>

```json
{
  "task_id": 1,
  "title": "Updated Customer Onboarding Task",
  "description": "Complete comprehensive onboarding process for new customer",
  "status": "COMPLETED",
  "priority": "HIGH",
  "start_date": "2025-08-02",
  "due_date": "2025-08-12",
  "assignee_id": 1,
  "assignee_name": "John Doe",
  "customer_reference": "CUST001",
  "updated_by": "manager"
}
```
</details>

### Expected Response:
<details>
<summary>📋 Click to copy JSON</summary>

```json
{
  "success": true,
  "message": "Task updated successfully",
  "data": {
    "id": 1,
    "title": "Updated Customer Onboarding Task",
    "description": "Complete comprehensive onboarding process for new customer",
    "status": "COMPLETED",
    "priority": "HIGH",
    "start_date": "2025-08-02",
    "due_date": "2025-08-12",
    "assignee_id": 1,
    "assignee_name": "John Doe",
    "customer_reference": "CUST001",
    "created_at": "2025-08-02T18:00:00.000000000",
    "updated_at": "2025-08-02T18:05:00.000000000",
    "created_by": "manager",
    "updated_by": "manager",
    "activities": null,
    "comments": null
  },
  "error_code": null,
  "timestamp": 1754157900000
}
```
</details>

---

## 5. Assign Tasks by Customer Reference (Bug Fix Demo)

**Endpoint**: `POST /api/v1/tasks/assign-by-reference`

### Request Body:
<details>
<summary>📋 Click to copy JSON</summary>

```json
{
  "customer_reference": "CUST001",
  "new_assignee_id": 3,
  "new_assignee_name": "Mike Johnson",
  "updated_by": "manager"
}
```
</details>

### Expected Response:
<details>
<summary>📋 Click to copy JSON</summary>

```json
{
  "success": true,
  "message": "Tasks assigned successfully",
  "data": "Tasks reassigned successfully",
  "error_code": null,
  "timestamp": 1754158200000
}
```
</details>

**Note**: This will cancel the old task for CUST001 and create a new one for Mike Johnson.

---

## 6. Fetch Tasks by Date Range (Bug Fix Demo)

**Endpoint**: `POST /api/v1/tasks/fetch-by-date`

### Request Body:
<details>
<summary>📋 Click to copy JSON</summary>

```json
{
  "start_date": "2025-08-01",
  "end_date": "2025-08-31",
  "assignee_ids": [1, 2, 3]
}
```
</details>

### Expected Response:
<details>
<summary>📋 Click to copy JSON</summary>

```json
{
  "success": true,
  "message": "Tasks retrieved successfully",
  "data": [
    {
      "id": 2,
      "title": "Follow-up Call",
      "description": "Schedule follow-up call with existing customer",
      "status": "ACTIVE",
      "priority": "MEDIUM",
      "start_date": "2025-08-03",
      "due_date": "2025-08-05",
      "assignee_id": 2,
      "assignee_name": "Jane Smith",
      "customer_reference": "CUST002",
      "created_at": "2025-08-02T18:00:00.000000000",
      "updated_at": "2025-08-02T18:00:00.000000000",
      "created_by": "team_lead",
      "updated_by": "team_lead",
      "activities": null,
      "comments": null
    },
    {
      "id": 3,
      "title": "Customer Onboarding Task",
      "description": "Complete onboarding process for new customer",
      "status": "ACTIVE",
      "priority": "HIGH",
      "start_date": "2025-08-02",
      "due_date": "2025-08-10",
      "assignee_id": 3,
      "assignee_name": "Mike Johnson",
      "customer_reference": "CUST001",
      "created_at": "2025-08-02T18:10:00.000000000",
      "updated_at": "2025-08-02T18:10:00.000000000",
      "created_by": "manager",
      "updated_by": "manager",
      "activities": null,
      "comments": null
    }
  ],
  "error_code": null,
  "timestamp": 1754158500000
}
```
</details>

**Note**: Only ACTIVE tasks are returned. CANCELLED tasks are filtered out.

---

## 7. Update Task Priority

**Endpoint**: `PUT /api/v1/tasks/priority`

### Request Body:
<details>
<summary>📋 Click to copy JSON</summary>

```json
{
  "task_id": 2,
  "priority": "HIGH",
  "updated_by": "manager"
}
```
</details>

### Expected Response:
<details>
<summary>📋 Click to copy JSON</summary>

```json
{
  "success": true,
  "message": "Task priority updated successfully",
  "data": {
    "id": 2,
    "title": "Follow-up Call",
    "description": "Schedule follow-up call with existing customer",
    "status": "ACTIVE",
    "priority": "HIGH",
    "start_date": "2025-08-03",
    "due_date": "2025-08-05",
    "assignee_id": 2,
    "assignee_name": "Jane Smith",
    "customer_reference": "CUST002",
    "created_at": "2025-08-02T18:00:00.000000000",
    "updated_at": "2025-08-02T18:15:00.000000000",
    "created_by": "team_lead",
    "updated_by": "manager",
    "activities": null,
    "comments": null
  },
  "error_code": null,
  "timestamp": 1754158800000
}
```
</details>

---

## 8. Get Tasks by Priority

**Endpoint**: `GET /api/v1/tasks/priority/{priority}`

### Example: `GET /api/v1/tasks/priority/HIGH`

### Expected Response:
<details>
<summary>📋 Click to copy JSON</summary>

```json
{
  "success": true,
  "message": "Tasks retrieved successfully",
  "data": [
    {
      "id": 2,
      "title": "Follow-up Call",
      "description": "Schedule follow-up call with existing customer",
      "status": "ACTIVE",
      "priority": "HIGH",
      "start_date": "2025-08-03",
      "due_date": "2025-08-05",
      "assignee_id": 2,
      "assignee_name": "Jane Smith",
      "customer_reference": "CUST002",
      "created_at": "2025-08-02T18:00:00.000000000",
      "updated_at": "2025-08-02T18:15:00.000000000",
      "created_by": "team_lead",
      "updated_by": "manager",
      "activities": null,
      "comments": null
    },
    {
      "id": 3,
      "title": "Customer Onboarding Task",
      "description": "Complete onboarding process for new customer",
      "status": "ACTIVE",
      "priority": "HIGH",
      "start_date": "2025-08-02",
      "due_date": "2025-08-10",
      "assignee_id": 3,
      "assignee_name": "Mike Johnson",
      "customer_reference": "CUST001",
      "created_at": "2025-08-02T18:10:00.000000000",
      "updated_at": "2025-08-02T18:10:00.000000000",
      "created_by": "manager",
      "updated_by": "manager",
      "activities": null,
      "comments": null
    }
  ],
  "error_code": null,
  "timestamp": 1754159100000
}
```
</details>

**Note**: Returns all tasks with HIGH priority, excluding cancelled tasks.

---

## 9. Add Comment to Task

**Endpoint**: `POST /api/v1/tasks/comment`

### Request Body:
<details>
<summary>📋 Click to copy JSON</summary>

```json
{
  "task_id": 2,
  "comment": "Customer confirmed the meeting time. Proceeding with the call as scheduled.",
  "commented_by": "jane_smith"
}
```
</details>

### Expected Response:
<details>
<summary>📋 Click to copy JSON</summary>

```json
{
  "success": true,
  "message": "Comment added successfully",
  "data": {
    "id": 2,
    "title": "Follow-up Call",
    "description": "Schedule follow-up call with existing customer",
    "status": "ACTIVE",
    "priority": "HIGH",
    "start_date": "2025-08-03",
    "due_date": "2025-08-05",
    "assignee_id": 2,
    "assignee_name": "Jane Smith",
    "customer_reference": "CUST002",
    "created_at": "2025-08-02T18:00:00.000000000",
    "updated_at": "2025-08-02T18:15:00.000000000",
    "created_by": "team_lead",
    "updated_by": "manager",
    "activities": [
      {
        "id": 1,
        "task_id": 2,
        "action": "TASK_CREATED",
        "performed_by": "team_lead",
        "timestamp": "2025-08-02T18:00:00.000000000",
        "details": "Task created with title: Follow-up Call"
      },
      {
        "id": 2,
        "task_id": 2,
        "action": "PRIORITY_CHANGED",
        "performed_by": "manager",
        "timestamp": "2025-08-02T18:15:00.000000000",
        "details": "Priority changed from MEDIUM to HIGH"
      },
      {
        "id": 3,
        "task_id": 2,
        "action": "COMMENT_ADDED",
        "performed_by": "jane_smith",
        "timestamp": "2025-08-02T18:20:00.000000000",
        "details": "Comment added: Customer confirmed the meeting time. Proceeding with the call as scheduled."
      }
    ],
    "comments": [
      {
        "id": 1,
        "task_id": 2,
        "comment": "Customer confirmed the meeting time. Proceeding with the call as scheduled.",
        "commented_by": "jane_smith",
        "timestamp": "2025-08-02T18:20:00.000000000"
      }
    ]
  },
  "error_code": null,
  "timestamp": 1754159400000
}
```
</details>

---

## Error Response Examples

### 1. Resource Not Found (404)
**Example**: `GET /api/v1/tasks/999`

<details>
<summary>📋 Click to copy JSON</summary>

```json
{
  "success": false,
  "message": "Task not found with ID: 999",
  "data": null,
  "error_code": "RESOURCE_NOT_FOUND",
  "timestamp": 1754159700000
}
```
</details>

### 2. Validation Error (400)
**Example**: POST request with missing required fields

<details>
<summary>📋 Click to copy JSON</summary>

```json
{
  "success": false,
  "message": "Validation failed",
  "data": null,
  "error_code": "VALIDATION_ERROR",
  "timestamp": 1754159800000
}
```
</details>

### 3. Invalid Argument (400)
**Example**: Invalid priority value

<details>
<summary>📋 Click to copy JSON</summary>

```json
{
  "success": false,
  "message": "Invalid priority value",
  "data": null,
  "error_code": "INVALID_ARGUMENT",
  "timestamp": 1754159900000
}
```
</details>

---

## Testing Sequence for Bug Demonstration

### Demonstrating Bug 1 Fix: Task Re-assignment

1. **Create a task** with customer reference "CUST001"
2. **Get the task by ID** - note it's ACTIVE
3. **Assign by reference** to a new assignee
4. **Get the original task by ID** - verify it's now CANCELLED
5. **Get tasks by priority** - verify new task exists for new assignee

### Demonstrating Bug 2 Fix: Cancelled Tasks Filtering

1. **Create multiple tasks** with different statuses
2. **Cancel some tasks** using update endpoint
3. **Fetch tasks by date** - verify only ACTIVE tasks are returned
4. **Get individual cancelled tasks** - verify they still exist but are filtered from lists

---

## Postman Collection Setup

### Environment Variables
Create a Postman environment with:
- `baseUrl`: `http://localhost:8080`
- `taskId`: `1` (update as needed)
- `customerId`: `CUST001`

### Pre-request Scripts
For dynamic data, add to Collection pre-request script:

<details>
<summary>📋 Click to copy JavaScript</summary>

```javascript
// Generate random customer reference
pm.environment.set("randomCustomer", "CUST" + Math.floor(Math.random() * 1000));

// Generate current date
pm.environment.set("currentDate", new Date().toISOString().split('T')[0]);

// Generate future date (7 days from now)
const futureDate = new Date();
futureDate.setDate(futureDate.getDate() + 7);
pm.environment.set("futureDate", futureDate.toISOString().split('T')[0]);
```
</details>

### Test Scripts
Add to each request for validation:

<details>
<summary>📋 Click to copy JavaScript</summary>

```javascript
// Validate response structure
pm.test("Response has correct structure", function () {
    const jsonData = pm.response.json();
    pm.expect(jsonData).to.have.property('success');
    pm.expect(jsonData).to.have.property('message');
    pm.expect(jsonData).to.have.property('timestamp');
});

// Validate success response
pm.test("Request was successful", function () {
    const jsonData = pm.response.json();
    pm.expect(jsonData.success).to.be.true;
});

// Store task ID for subsequent requests
if (pm.response.json().data && pm.response.json().data.id) {
    pm.environment.set("taskId", pm.response.json().data.id);
}
```
</details>

---

## Priority Values
- `HIGH`
- `MEDIUM`
- `LOW`

## Status Values
- `ACTIVE`
- `COMPLETED`
- `CANCELLED`

---

**Author: Deepak Bhati**
**Date: August 2025**

**Note**: Make sure the application is running on `http://localhost:8080` before testing these endpoints.
```
