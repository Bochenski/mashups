package main 

import ( 
  "flag" 
  "fmt" 
  "os" 
  "io"
  "bufio" 
  "strings"
  "strconv"
  // "container/heap"
) 

func main() { 
  fmt.Printf("Reading files...\n"); 
  flag.Parse(); 

  for i :=0; i < flag.NArg(); i++ { 
    fmt.Printf("[File: %v]\n", flag.Arg(i)); 
    fin, err := os.Open(flag.Arg(i)) 
    if (err==nil) { 
      r := bufio.NewReader(fin) 
      graph :=  Graph{make(map[int64]*Vertex), make(map[int64]*Edge)}
      for line, _, err := r.ReadLine(); err!=io.EOF; line, _, err = r.ReadLine() { 
        //split the string
        splits := strings.Fields(string(line))
        //see if the values are already in our graphs

        v1, _ := strconv.ParseInt(splits[0],10,32)

        _, ok := graph.vertices[v1]
        if !ok {
          graph.vertices[v1] = &Vertex{edges: make(map[int64]int64), explored: false, shortestPath: 0}
        }

        for i := 1; i < len(splits); i ++ {
          vSplit := strings.Split(splits[i], ",")
          v, _ := strconv.ParseInt(vSplit[0],10,32)
          weight, _ := strconv.ParseInt(vSplit[1],10,32)
          edgeId := 1000 * v1 + v //algorithm now only works for fewer than 999 vertices
          graph.edges[edgeId] = &Edge{head: v1,tail: v,weight: weight}
          graph.vertices[v1].edges[v] = weight
        }

      } 

      Dijkstra(&graph, 1)
      for name, value := range graph.vertices { 
        fmt.Println(name, value.shortestPath)
      }

    } else { 
      fmt.Printf("The file %v does not exist!\n", flag.Arg(i)) 
    } 
  } 
} 

type Edge struct {
  head int64
  tail int64
  weight int64
}

type Vertex struct { 
  edges map[int64]int64
  explored bool
  shortestPath int64
}

type Graph struct {
  vertices map[int64]*Vertex
  edges map[int64]*Edge
}

func Dijkstra(graph *Graph, start int64) {
  //mark our starting vertex as explored
  vertex := graph.vertices[start]
  vertex.explored = true
  bestVertex := start
  var shortestPath int64 = 10000000
  for _, v := range graph.vertices {
    if v.explored {
      startingDistance := v.shortestPath
      for tailVertex, distance := range v.edges {
        if !graph.vertices[tailVertex].explored {
          if startingDistance + distance < shortestPath {
            bestVertex = tailVertex
            shortestPath = startingDistance + distance
          }
        }
      }
    }
  }
  if bestVertex != start {
    graph.vertices[bestVertex].shortestPath = shortestPath
    Dijkstra(graph, bestVertex)
  }

}