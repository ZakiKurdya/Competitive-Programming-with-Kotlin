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

// operations when first and end station can include multiple lines

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