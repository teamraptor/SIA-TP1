# 0h-h1 Solver

[0h-h1](http://www.0hh1.com) solver. Special assignment for SIA course at ITBA. It uses the [General Problem Solver](https://github.com/lmarzora/GeneralProblemSolver) as the engine.

## Building and running

This project uses [maven](https://maven.apache.org).


```bash
mvn clean package
java -jar target/uber-tp1-1.0-SNAPSHOT.jar -s [STRATEGY] -b [BOARD_PATH] -h [HEURISTIC_NUMBER] -c [CUT_CONDITION]
```

For example:

```bash
java -jar target/uber-tp1-1.0-SNAPSHOT.jar -s ASTAR -b ./boards/board2.txt -h 2 -c 5
```

Where the keys are:


| Key | Description |
| --- | --- |
| -s | Search Strategy  |
| -b | Board file path |
| -h | Heuristic number (codes can be found in the next section)|
| -c | Cut condition for IDDFS |

## Heuristics

The following heuristics were developed

| Heuristic | Code |
| --- | --- |
| 0 | Trivial  |
| 4 | Semi Sure Heuristic |

## Search Strategies

The following search strategies are available:

* DFS
* BFS
* IDDFS
* GREEDY
* ASTAR

## Credits

* [Marzoratti, Luis](https://github.com/lmarzora)
* [Soncini, Lucas](https://github.com/lsoncini)
* [Fraga, Matias](https://github.com/matifraga)
* [De Lucca, Tomas](https://github.com/tomidelucca)
