# Home Planner

Simple software to Manage home.

[![CircleCI](https://circleci.com/gh/hcassus/TaskList.svg?style=svg)](https://circleci.com/gh/hcassus/TaskList)

[![Codecov](https://codecov.io/gh/hcassus/TaskList/branch/master/graph/badge.svg)](https://codecov.io/gh/hcassus/TaskList)


# How to start

- Create a database on PostgreSQL
- Edit `application.properties` to reflect your own database data
- set `spring.jpa.hibernate.ddl-auto` to create
- On the root directory, type `mvn spring-boot:run`
- set `spring.jpa.hibernate.ddl-auto` to validate
- Access localhost:8000

# How to use

## Tasks Manager
- Type an activity
- Click on the button to submit the activity
- Click on the red X button to remove the task
- Click on the green tick button to complete the task
- Click on the yellow reload button to mark it as undone again

## Pantry Manager
- Fill in the item data
- Click on the add button to submit it
- Click on the remove button to remove the item
