# QueuesSimulator

## Description
Simulates queues from a shop analysing and minimizing waiting times.

## Input
There are two ways to give input to the app. (at the beginning you will have to select one).

### 1 Text based
Reads a txt file with the name "In-test" and the following format:

_Line | Content_

* 1 Number of clients

* 2 Number of queues

* 3 Maximum simulation time

* 4 Minimum arrival time,maximum arrival time  (separated by comma)

* 5 Minimum service time,maximum service time (separated by comma)

_Arrival time = second at which the client enters a queue_

_Service time = number of seconds the client waits in front of the queue_

You can also opt to have the clients arriving randomly. In that case arrival time represents the minimum and maximum interval of waiting before creating a new client.

### 2 GUI based

There are sliders for everything , you see the queues and their contents changing, you can create queues, delete queues and change any value at runtime. In short, the Text based input is useless.

## Implementation
The main ideea behind this project was to get used with concurrency. (Threads, blocking queues...)

Bear in mind that not only was this the third time I ever wrote a Java/OOP project, but I also had to modify a large portion of it after an year. Thus, this thing is unreadable.
