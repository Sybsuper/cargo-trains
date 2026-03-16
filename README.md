# Compiler Optimizations: Cargo Trains

Most important sections:
- [The Metaphor](#the-metaphor)
- [Behind the Code](#behind-the-code)
- [Tests](#tests)

## The Metaphor

The **Cargo Train Network** problem is a metaphor for data flow analysis in compilers. In this analogy:
- **Stations** are instructions or basic blocks.
- **Tracks** represent control flow (jumps, branches).
- **Cargo Items** represent data definitions or variables (e.g., reaching definitions).
- **Trains** propagate this information through the program.

This project implements an algorithm to determine the state of "cargo" at each station in the network.

## Getting Started

### Prerequisites

- JVM 25
- Kotlin 2.3.0

### Running the Project

You can compile and run the project using Gradle. Since it reads from standard input, you can pipe a problem file to it.

1.  **Run with input via Standard Input**:
    Create an input file (e.g., `problem.txt`) and run:
    ```bash
    cat problem.txt | ./gradlew run --console=plain -q
    ```
    *(Note: Using `--console=plain -q` is recommended to suppress Gradle build output, leaving only the application output)*

2.  **Run Interactive Mode**:
    Just run:
    ```bash
    ./gradlew run --console=plain -q
    ```
    Then type or paste the input problem manually.

## Input Format

The input describes the network topology and station logic:

1.  **First Line**: `S T`
    - `S`: Number of Stations
    - `T`: Number of Tracks
2.  **Stations** (`S` lines): `ID UnloadID LoadID`
    - `ID`: Station identifier
    - `UnloadID`: Cargo item to remove/unload
    - `LoadID`: Cargo item to add/load
3.  **Tracks** (`T` lines): `FromID ToID`
    - Defines a one-way track between stations.
4.  **Start**: `StartStationID`
    - The ID of the station where the train starts.

### Example

The following input defines a simple chain of stations (1 -> 2 -> 3).
- Station 1: Unload item 0 (nothing), Load item 10.
- Station 2: Unload item 10, Load item 20.
- Station 3: Unload item 20, Load item 30.
- Tracks: 1 -> 2, 2 -> 3.
- Start at Station 1.

**Input (`problem.txt`)**:
```
3 2
1 0 10
2 10 20
3 20 30
1 2
2 3
1
```

**Output**:
```
1 
2 10
3 20
Steps: 3
```

### Complex Example

The graph visualized:
[![](https://mermaid.ink/img/pako:eNptlMtupDAQRX8FVTYZhW65bGODFWUz2c5uVhEbZjBpFB4tGjTJtPrfU2AeRgkrzr2uom5J5gp_29yCgdcuO5-C389pE9CD9yng45_u6aCCB0zhh5M5yXySOcly1QXpYjnO-apL0uWko6aCzYjIiJyRkMFWQ5Gh3BcYGXo1NBl6rdj0mPR40knmW6OE9MSdF9RIrQayMRtzFt-Ni1NslxtjsjZnTI4uugweos0Yo6NYS7ad4BgeXXrtT4zB4fAUSB8QdyR2NJ_kE8Xz_r4CLdFt0JEPel6hX5RMwH2IfFA-aB92DZJlqd_QMtGCS0j0J8T9Org_1kxqR_GO1l2JXaGjxZPf0DLcpf-oLIUpyqoyd0VShJe-a9-suRNCzO-Hf2Xen4w8v0NIF6XMwfTdYEOobVdnI8J17JZCf7K1TcHQa26LbKj6FNLmRmXnrHlp23qp7Nrh9QSmyKoL0XDOs94-lxndwnpVO9vktvvZDk0PRuLUA8wV3sFgEh0RtVIs4lwqlsgQPsAk8igFeUIKhjrS8S2E_9NX2VFzxRFRMsYVChmHYPOyb7tf7v5Pv4HbJ4U-9LE?type=png)](https://mermaid.live/edit#pako:eNptlMtupDAQRX8FVTYZhW65bGODFWUz2c5uVhEbZjBpFB4tGjTJtPrfU2AeRgkrzr2uom5J5gp_29yCgdcuO5-C389pE9CD9yng45_u6aCCB0zhh5M5yXySOcly1QXpYjnO-apL0uWko6aCzYjIiJyRkMFWQ5Gh3BcYGXo1NBl6rdj0mPR40knmW6OE9MSdF9RIrQayMRtzFt-Ni1NslxtjsjZnTI4uugweos0Yo6NYS7ad4BgeXXrtT4zB4fAUSB8QdyR2NJ_kE8Xz_r4CLdFt0JEPel6hX5RMwH2IfFA-aB92DZJlqd_QMtGCS0j0J8T9Org_1kxqR_GO1l2JXaGjxZPf0DLcpf-oLIUpyqoyd0VShJe-a9-suRNCzO-Hf2Xen4w8v0NIF6XMwfTdYEOobVdnI8J17JZCf7K1TcHQa26LbKj6FNLmRmXnrHlp23qp7Nrh9QSmyKoL0XDOs94-lxndwnpVO9vktvvZDk0PRuLUA8wV3sFgEh0RtVIs4lwqlsgQPsAk8igFeUIKhjrS8S2E_9NX2VFzxRFRMsYVChmHYPOyb7tf7v5Pv4HbJ4U-9LE)

The train network above can be solved by the algorithm in `53` steps (a step is when the train changes stations, when a track is popped off the queue).

<details>

<summary>Input:</summary>

```
14 29
1 6 1
2 2 14
3 6 22
4 17 12
5 19 10
6 20 17
7 19 7
8 9 20
9 13 16
10 12 22
11 18 2
12 4 5
13 18 4
14 7 7
1 4
1 11
1 13
1 14
2 8
5 8
5 10
6 1
6 7
7 8
9 2
9 5
9 6
9 7
9 8
9 9
10 9
10 10
10 11
11 1
11 4
12 2
12 6
12 8
12 13
13 2
13 3
14 3
14 10
9
```

</details>

<details>
<summary>Output:</summary>


```
9 16 10 22 17 1 7 2
6 16 10 22 17 1 7 2
7 16 17 10 22 1 7 2
5 16 10 22 17 1 7 2
2 16 17 1 4 10 22 7 2
8 16 17 7 10 14 22 1 4 2
1 16 17 1 10 22 2 7
10 16 10 22 17 1 7 2
14 16 17 1 10 22 2 7
13 16 17 1 10 22 2 7
4 16 17 1 10 22 2 7
11 16 17 1 10 22 7 2
3 16 17 1 7 4 10 22 2
Steps: 53
```

</details>

## Behind the Code

The core solver uses a `LookupQueue` for efficient processing.
- **`Solver.kt`**: Main logic processing track traversals and load updates. It calculates the `deltas` (changes) to propagate only necessary updates.
- **`LookupQueue.kt`**: A specialized queue ensuring unique elements (O(1) contains/add/pop).
- **`Problem.kt`**: Data structure representing the cargo network.

## Tests
The project includes unit tests to verify the correctness of the solver.

This also includes a [Property Test](src/test/kotlin/PropertyTest.kt), that generates many random problem instances and checks the solver's solution by validating some paths of a load type.
More specifically it:
- Picks a station reachable from the starting station;
- Follows a random path that the load type loaded at that station should be able to follow;
- Validates at each station on the random path that that load type indeed does reach that station according to the solution;
- Ends the path when it reaches a dead end or when the load type is unloaded.

This effectively traces a `train` of a specific `cargo item` through the network, ensuring that the solver's output correctly reflects the expected behavior of the cargo as it moves through the stations and tracks.
Which in the metaphor could equal the path a variable could take from definition to use in a program, validating that the data flow analysis is accurate.

To run tests:

```bash
./gradlew test
```

## Building

To build the project and run tests:

```bash
./gradlew build
```



