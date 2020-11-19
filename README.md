
# Competitive-programming-problem-solving

<p align="center">
Here are set of logic and mathematical problems (PART 1) - solved using Kotlin ..


![Project Image](https://logos-download.com/wp-content/uploads/2016/10/Kotlin_logo_wordmark.png)

---

### Table of Problems

- [Metro Line](#metro-line)
- [Author Info](#author-info)

---

## Metro Line

###    Problem Description

There are n stations numbered from 1 to n, and m metro lines numbered from 1 to m Each line is represented by a sequence of distinct station,
(you can not have a cycle in a single line), and a single stations can belong to multiple lines.
If we want to go from one stations on a given line to another station, possibly on another line, we can make any of the following two operations zero or more times:

-	Move from the current station to the next station (the station right after the current station) on the given line.
-	Make a transition from the given line to another line which the current station also belongs to.

It takes x minutes to travel between two consecutive stations on the same line, and y minutes to do a transition between two lines at any station,
Given the description of the metro lines, a start station S and an end station E,
calculate the minimum time needed to travel between them, If we cannot reach the end station Output -1,
Note that there's a constant flow of trains on each line, Le, when a train leaves a station on any line, another train arrives immediately. If the start station belongs to multiple lines, you can start from any of them. Similarly, if the end station belongs to multiple lines, you can stop at any of them.

###    Input

The first line contains an integer T — The number of test cases.
The first line in each test case contains 6 integers n, m, S. E x, y, denoting:
the number of stations, the number of lines, the start station, the end station, the time taken between two consecutive stations on the 
same line and the time taken doing a transition at any station respectively.
(2 <= n <= 1000, 1 <= m <= 1000, 1 <= S,E <= n, 1 <= x,y <= 10^9).
The following m lines describe the metro lines.
The ith line starts with an integer Li - The number of stations on line i (1 <= Li <= n).
followed by Li integers, representing the sequence of stations in order on the line.

###    Output

For each test case, output one integer — The minimum time to travel from S to E.
If it is not possible to reach E from S, output -1.

###    Sample test cases

(Input):
```sh
3

5 3 1 5 2 3

3 1 2 3

2 3 4

2 4 5

5 2 1 5 2 3

3 1 2 3

2 4 5

5 7 1 5 2 3

3 1 2 3

2 3 4

2 4 5

2 1 2

2 2 3

3 3 4 5

2 4 5
```
(Output):
```sh
14 , -1 , 11
```

[Back To The Top](#competitive-programming-problem-solving)

---

## Author Info

- Twitter - [@zaki_kurdya](https://twitter.com/zaki_kurdya)

[Back To The Top](#competitive-programming-problem-solving)
