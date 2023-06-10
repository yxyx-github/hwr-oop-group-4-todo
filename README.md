# HWR OOP Lecture Project - Gruppe 4: Todo

This repository contains a student project created for an ongoing lecture on object-oriented programming with Java at HWR Berlin (summer term 2023).

> :warning: This code is for educational purposes only. Do not rely on it!

## Abstract

[TODO]: # (State the most interesting problems you encountered during the project.)
This is a todolist to keep track of the tasks and projects you have currently running.
It is able to create and manage tasks, ideas, projects and a somedaymaybe list with tags.
Tasks and the somedaymaybe list can be converted to a project.
Projects also can be managed with a calendar.
The todolist is able to save and load from a file.

What the list can't do:
Review the closed and finished projects/tasks. So it is not capable of showing statistics of what you have already done

With that being said, enjoy using our todolist.


## Feature List

### Library

| Number |    Implemented     | Feature               |       Tests        |
|:------:|:------------------:|-----------------------|:------------------:|
|   1    | :heavy_check_mark: | Task (Todo)           | :heavy_check_mark: |
|   2    | :heavy_check_mark: | Project               | :heavy_check_mark: |
|   3    | :heavy_check_mark: | Tag                   | :heavy_check_mark: |
|   4    | :heavy_check_mark: | Tasks in Projects     | :heavy_check_mark: |
|   5    | :heavy_check_mark: | Tasks have Tags       | :heavy_check_mark: |
|   6    | :heavy_check_mark: | Deadlines             | :heavy_check_mark: |
|   7    |        :x:         | Daily Checklist       |        :x:         |
|   8    | :heavy_check_mark: | In Tray               | :heavy_check_mark: |
|   9    |        :x:         | Time Allocation       |        :x:         |
|   10   | :heavy_check_mark: | "Someday, Maybe" List | :heavy_check_mark: |
|   11   | :heavy_check_mark: | Weekly Planning       | :heavy_check_mark: |
|   12   |        :x:         | Weekly Review         |        :x:         |


### User Interface

| Number |    Implemented     | Feature              |       Tests        |
|:------:|:------------------:|----------------------|:------------------:|
|   13   | :heavy_check_mark: | Creation of Tasks    | :heavy_check_mark: |
|   14   | :heavy_check_mark: | Creation of Projects | :heavy_check_mark: |
|   15   | :heavy_check_mark: | Creation of Tags     | :heavy_check_mark: |
|   16   | :heavy_check_mark: | View Lists           | :heavy_check_mark: |
|   17   | :heavy_check_mark: | Calender             | :heavy_check_mark: |
|   18   | :heavy_check_mark: | Next Task            | :heavy_check_mark: |
|   19   | :heavy_check_mark: | Complete a Task      | :heavy_check_mark: |
|   20   | :heavy_check_mark: | Saving of TodoLists  | :heavy_check_mark: |
|   21   | :heavy_check_mark: | Loading of TodoLists | :heavy_check_mark: |


## Additional Dependencies

| Number | Dependency Name          | Dependency Description                   | Why is it necessary?                  |
|--------|--------------------------|------------------------------------------|---------------------------------------|
| 1      | org.mockito              | Mocking-Framework                        | Persistence tests                     |
| 2      | com.google.code.gson     | Library to create and parse JSON strings | JSON is used as format in persistence |
| 3      | net.javacrumbs.json-unit | Assertion library for JSON               | Simplifies tests handling JSON output |
