import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Queue;
import java.util.PriorityQueue;

public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Input is null!");
        }
        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Start doesn't exist in the graph!");
        }
        List<Vertex<T>> visitList = new ArrayList<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        visitList.add(start);
        queue.add(start);
        while (!queue.isEmpty()) {
            Vertex<T> vertex = queue.remove();
            List<VertexDistance<T>> vertexDistance = adjList.get(vertex);
            for (VertexDistance<T> v : vertexDistance) {
                Vertex<T> w = v.getVertex();
                if (!visitList.contains(w)) {
                    queue.add(w);
                    visitList.add(w);
                }
            }
        }
        return visitList;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Input is null!");
        }
        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Start doesn't exist in the graph!");
        }
        List<Vertex<T>> visitlist = new ArrayList<>();
        Set<Vertex<T>> visitSet = new HashSet<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        dfsHelper(start, adjList, visitlist, visitSet);
        return visitlist;


    }

    /**
     * Helps to perform a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex recursively.
     * @param start   starting vertex
     * @param adjList adjacent list
     * @param list    list of vertices in visited order
     * @param set     set of vertices in visited order
     * @param <T>     generic typing of the data
     */
    private static <T> void dfsHelper(Vertex<T> start, Map<Vertex<T>,
            List<VertexDistance<T>>> adjList, List<Vertex<T>> list,
                                      Set<Vertex<T>> set) {
        if (!set.contains(start)) {
            set.add(start);
            list.add(start);
            List<VertexDistance<T>> vertexDistances = adjList.get(start);
            for (VertexDistance<T> vertexDistance : vertexDistances) {
                Vertex<T> v = vertexDistance.getVertex();
                dfsHelper(v, adjList, list, set);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Input is null!");
        }
        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Start doesn't exist in the graph!");
        }
        Set<Vertex<T>> visit = new HashSet<>();
        Map<Vertex<T>, Integer> map = new HashMap<>();
        PriorityQueue<VertexDistance<T>> priorityQueue = new PriorityQueue<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        for (Vertex<T> vertex : adjList.keySet()) {
            if (start.equals(vertex)) {
                map.put(start, 0);
            } else {
                map.put(vertex, Integer.MAX_VALUE);
            }
        }
        VertexDistance<T> vertexDistance = new VertexDistance<>(start, 0);
        priorityQueue.add(vertexDistance);
        while (!priorityQueue.isEmpty() && visit.size() < graph.getVertices().size()) {
            VertexDistance<T> vertexDistance1 = priorityQueue.remove();
            Vertex<T> vertex = vertexDistance1.getVertex();
            List<VertexDistance<T>> vertexDistances = graph.getAdjList().get(vertex);
            visit.add(vertex);
            for (VertexDistance<T> vertexDistance2 : vertexDistances) {
                Vertex<T> vertex2 = vertexDistance2.getVertex();
                int distance1 = vertexDistance1.getDistance();
                int distance2 = vertexDistance2.getDistance();
                if (map.get(vertex2) > (distance1 + distance2)) {
                    map.put(vertexDistance2.getVertex(), distance1 + distance2);
                    VertexDistance<T> vertexDistance3 = new VertexDistance<>(vertexDistance2.getVertex(),
                            distance1 + distance2);
                    priorityQueue.add(vertexDistance3);
                }
            }
        }
        return map;
    }

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Input is null!");
        }
        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Start doesn't exist in the graph!");
        }
        Set<Vertex<T>> visitSet = new HashSet<>();
        Set<Edge<T>> edgeSet = new HashSet<>();
        Queue<Edge<T>> priorityQueue = new PriorityQueue<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        for (VertexDistance<T> vertexDistance : adjList.get(start)) {
            priorityQueue.add(new Edge<>(start, vertexDistance.getVertex(), vertexDistance.getDistance()));
        }
        visitSet.add(start);
        while (!priorityQueue.isEmpty() && visitSet.size() < graph.getVertices().size()) {
            Edge<T> edge = priorityQueue.remove();
            if (!visitSet.contains(edge.getV())) {
                visitSet.add(edge.getV());
                edgeSet.add(edge);
                edgeSet.add(new Edge<>(edge.getV(), edge.getU(), edge.getWeight()));
                for (VertexDistance<T> vertexDistance1 : adjList.get(edge.getV())) {
                    if (!visitSet.contains(vertexDistance1.getVertex())) {
                        priorityQueue.add(new Edge<>(edge.getV(),
                                vertexDistance1.getVertex(), vertexDistance1.getDistance()));
                    }
                }
            }
        }
        if (edgeSet.size() == 2 * (graph.getVertices().size() - 1)) {
            return edgeSet;
        } else {
            return null;
        }
    }
}
