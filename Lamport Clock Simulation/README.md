# Lamport's Logical Clock

This project implements a distributed system simulation using Lamport's Logical Clock algorithm. It demonstrates how logical time is maintained across multiple processes in a distributed environment.

## Table of Contents
1. [How to Run the Simulation](#how-to-run-the-simulation)
2. [Implementation of Lamport's Logical Clock](#implementation-of-lamports-logical-clock)
3. [Testing the System](#testing-the-system)
4. [Simulated Scenario](#simulated-scenario)
5. [Additional Features](#additional-features)

## How to Run the Simulation

1. Ensure you have Java Development Kit (JDK) installed on your system.
2. Compile the Java file:
   ```
   javac LamportClock.java
   ```
3. Run the compiled program:
   ```
   java LamportClock
   ```
4. The simulation will run for 30 seconds and then display the event logs for each process.

## Implementation of Lamport's Logical Clock

Lamport's Logical Clock is implemented as follows:

1. Each process maintains its own logical clock, initialized to 0.
2. The clock is updated according to these rules:
   - Internal Event: Increment the clock by 1.
   - Send Event: Increment the clock by 1 and attach the clock value to the message.
   - Receive Event: Set the clock to max(local_clock, received_clock) + 1.

3. The `LamportClock` class represents a process in the distributed system:
   - `logicalClock`: Stores the current logical time.
   - `updateClock()`: Updates the clock based on local and received time.
   - `internalEvent()`: Simulates an internal event.
   - `sendMessage()`: Sends a message to another process.
   - `receiveMessage()`: Processes a received message.

## Testing the System

To test the system:

1. Run the simulation as described in the "How to Run" section.
2. Observe the output, which shows events occurring in each process.
3. Verify that:
   - Logical clocks always increase.
   - Receive events have higher clock values than their corresponding send events.
   - The order of events within each process is consistent with their clock values.

To modify the test parameters:
- Adjust the `numProcesses` variable in the `main` method to change the number of processes.
- Modify the sleep duration in the `main` method to change the simulation time.
- Alter the random intervals in the `run` method to change event frequency.

## Simulated Scenario

The simulation creates a distributed system with multiple processes that:
1. Perform internal computations (internal events).
2. Send messages to random peers (send events).
3. Receive and process incoming messages (receive events).

Each process randomly decides to perform an internal event or send a message to another process. This simulates a real-world scenario where distributed processes perform local computations and communicate asynchronously.

## Additional Features

1. **Asynchronous Message Handling**: Uses `ExecutorService` for non-blocking message reception.
2. **UDP Communication**: Implements inter-process communication using UDP for realistic network simulation.
3. **Event Logging**: Maintains a detailed log of all events in each process.
4. **Random Event Generation**: Simulates realistic, non-deterministic behavior in distributed systems.
5. **Flexible Process Creation**: Easily adjustable number of processes for scaling the simulation.

This simulation provides a practical demonstration of Lamport's Logical Clock algorithm and offers a foundation for further exploration of distributed systems concepts.
