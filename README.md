
# Competitive-programming-problem-solving

<p align="center">
Here are set of logic and mathematical problems (PART 1) - solved using Kotlin ..


![Project Image](https://logos-download.com/wp-content/uploads/2016/10/Kotlin_logo_wordmark.png)

---

### Table of Problems

- [Metro Line](#metro-line)
- [Isosceles Triangle](#isosceles-triangle)
- [Author Info](#author-info)
- [Online Kotlin Compiler to test code](#online-kotlin-compiler-to-test-code)

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
>(Output):
```sh
14 , -1 , 11
```
###    Soultion ✨

```kotlin
import java.util.*
import kotlin.collections.ArrayList
import java.util.TreeSet

fun main() {
    val input = Scanner(System.`in`)
    val t = input.nextByte()
    for (testCases in 1..t){

        // input
        val n = input.nextShort()
        val m = input.nextShort()
        val s = input.nextShort()
        val e = input.nextShort()
        val x = input.nextInt()
        val y = input.nextInt()

        // input the set of metro lines
        val setOfMetroLines = ArrayList<MutableSet<Short>>()
        for (metroLine in 1..m){
            val lines = mutableSetOf<Short>()
            val liNumber = input.nextShort()
            for (liN in 1..liNumber){
                lines.add(input.nextShort())
            }
            setOfMetroLines.add(lines)
        }

        // output

        with (Graph(minTime(setOfMetroLines,m, x, y, e), true)) {   // directed
            dijkstra("$s")
            printPath("$e")
        }
    }
}

// operations when first and end station can include multiple lines - using dijkstra algorithm 
// here we will consider duration to be a distance and then apply it in the algorithm

fun minTime(setOfLines:ArrayList<MutableSet<Short>> , m:Short , x:Int , y:Int , E:Short): MutableSet<Edge> {
    val graph = mutableSetOf<Edge>()
    for (count in 0 until m) {
        val set = setOfLines[count]
        if (set.last().toInt() == (E.toInt())){
            graph.add(Edge("${set.first().toInt()}", "${set.last().toInt()}", ((set.last().toInt() - set.first().toInt())*x)))
        }else{
            graph.add(Edge("${set.first().toInt()}", "${set.last().toInt()}",  (((set.last().toInt() - set.first().toInt())*x)+y)))
        }
    }
return graph
}

class Edge(val v1: String, val v2: String, val dist: Int)
class Vertex(private val name: String) : Comparable<Vertex> {
    var dist = Int.MAX_VALUE  // MAX_VALUE assumed to be infinity
    var previous: Vertex? = null
    val neighbours = HashMap<Vertex, Int>()

    fun printPath() {
         if (previous == null) {
            print(-1)
        }
        else {
            print(dist)
        }
    }

    override fun compareTo(other: Vertex): Int {
        if (dist == other.dist) return name.compareTo(other.name)
        return dist.compareTo(other.dist)
    }

    override fun toString() = "($name, $dist)"
}

class Graph(
        edges: MutableSet<Edge>,
        directed: Boolean,
        private val showAllPaths: Boolean = false
) {
    // mapping of vertex names to Vertex objects, built from a set of Edges
    private val graph = HashMap<String, Vertex>(edges.size)

    init {
        // one pass to find all vertices
        for (e in edges) {
            if (!graph.containsKey(e.v1)) graph[e.v1] = Vertex(e.v1)
            if (!graph.containsKey(e.v2)) graph[e.v2] = Vertex(e.v2)
        }

        // another pass to set neighbouring vertices
        for (e in edges) {
            graph[e.v1]!!.neighbours[graph[e.v2]!!] = e.dist
            // also do this for an undirected graph if applicable
            if (!directed) graph[e.v2]!!.neighbours[graph[e.v1]!!] = e.dist
        }
    }

    fun dijkstra(startName: String) {
        val source = graph[startName]
        val q = TreeSet<Vertex>()

        // set-up vertices
        for (v in graph.values) {
            v.previous = if (v == source) source else null
            v.dist = if (v == source)  0 else Int.MAX_VALUE
            q.add(v)
        }
        dijkstra(q)
    }

    // Implementation of dijkstra's algorithm using a binary heap
    private fun dijkstra(q: TreeSet<Vertex>) {
        while (!q.isEmpty()) {
            // vertex with shortest distance (first iteration will return source)
            val u = q.pollFirst()
            // if distance is infinite we can ignore 'u' (and any other remaining vertices)
            // since they are unreachable
            if (u.dist == Int.MAX_VALUE) break

            //look at distances to each neighbour
            for (a in u.neighbours) {
                val v = a.key // the neighbour in this iteration
                val alternateDist = u.dist + a.value
                if (alternateDist < v.dist) { // shorter path to neighbour found
                    q.remove(v)
                    v.dist = alternateDist
                    v.previous = u
                    q.add(v)
                }
            }
        }
    }

    fun printPath(endName: String) {
        graph[endName]!!.printPath()
        if (showAllPaths) printAllPaths() else println()
    }

    private fun printAllPaths() {
        for (v in graph.values) {
            v.printPath()
        }
    }
}

// By Dev.Zaki
```
[Back To The Top](#competitive-programming-problem-solving)

---

## Isosceles Triangle

###    Problem Description

An Isosceles triangle is placed on X Y plane, such that its vertex angle lies on the Y-axis
initially at `point (0, h)` and its base angles lie on the X-axis, initially `at points (-a , 0) and (a, 0)`,
The vertex angle starts sliding down until it reaches the X-axis, and the triangle legs keep sliding along the X-axis keeping the area of the triangle the same.
There are `n points` in the plane.
Count the number of points that will be inside the triangle at any point in time.
Note: that if a point is on the side of the triangle at any moment, it is `considered inside the triangle`.

![Project Image](https://user-images.githubusercontent.com/46399191/99694799-7799e480-2a95-11eb-8b81-4c7019bc5a06.jpg)

###    Input

The input starts with an `integer T - The number of test cases`.
The first line of each test case contains `3 integers n, h and a.`
The number of points in the plane, the initial position of the vertex angle on the Y-axis, and the initial positions of the base angles on the X-axis.
`(1  <=  n, h, a < 10^5)`
The following `n lines` describe the points on the plane.
The `ith line` contains two integers `xi`, and `yi`, `(0 <= |xi|, yi <= 105, )` - the coordinates of point i.

###    Output

For each test case, output one line containing one number - `the number of points that will ever be inside the triangle at any point in time`.

###    Sample test cases

(Input):
```sh
1

4 4 3

2 1

-3 4

6 0

-3 1
```
>(Output):
```sh
3
```

###    Solution ✨

```kotlin
import java.util.*

fun main(args: Array<String>) {

    val inP = Scanner(System.`in`)
    val T = inP.nextInt()
    var Xi: Double
    var Yi: Int
    var count = 0
    var slopeY:Int
    for (TC in 1..T) {
    // input
        val n = inP.nextInt()
        val h = inP.nextDouble()
        val a = inP.nextDouble()
        val aM = a * -1
        for (i in 0 until n) {
            Xi = inP.nextDouble()
            Yi = inP.nextInt()
            // Check if the coordinates of the points are within the range of the x and y input
            if (Xi in aM..a && h >= Yi) {
                if (Xi > 0){
                // Find the max value of yi based on xi (Be on the leg of the triangle) that can be inside the triangle and after that compare it with entered y
                // Y = mx + b - slope = -y\x
                    slopeY = ((Xi*(h/-a))+h).toInt()
                    if (Yi <= slopeY){
                        count ++
                    }
                }else{
                    slopeY = ((Xi*(h/-aM))+h).toInt()
                    if (Yi <= slopeY){
                        count ++
                    }
                }
            }
        }
        println(count)
    }
}


// By Dev.Zaki
```

[Back To The Top](#competitive-programming-problem-solving)

---

## Online Kotlin Compiler to test code

> https://play.kotlinlang.org/

## Author Info

- Twitter - [@zaki_kurdya](https://twitter.com/zaki_kurdya)

[Back To The Top](#competitive-programming-problem-solving)
