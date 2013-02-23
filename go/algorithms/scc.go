package main 

import ( 
  "flag" 
  "fmt" 
  "os" 
  "io"
  "bufio" 
  "strings"
  "strconv"
) 

func main() { 
  fmt.Printf("Reading files...\n"); 
  flag.Parse(); 

  for i :=0; i < flag.NArg(); i++ { 
    fmt.Printf("[File: %v]\n", flag.Arg(i)); 
    fin, err := os.Open(flag.Arg(i)) 
    if (err==nil) { 
      r := bufio.NewReader(fin) 
      graph :=  Graph{make(map[int64]*Vertex), 0, []int64{}, map[int64]int64{}, make(map[int64]*scc)}
      graph_rev := Graph{make(map[int64]*Vertex), 0, []int64{}, map[int64]int64{}, make(map[int64]*scc)}
      for line, _, err := r.ReadLine(); err!=io.EOF; line, _, err = r.ReadLine() { 
        //split the string
        splits := strings.Split(string(line), " ")
        //see if the values are already in our graphs

        v1, _ := strconv.ParseInt(splits[0],10,32)
        v2, _ := strconv.ParseInt(splits[1],10,32)
        _, ok := graph.vertices[v1]
        if !ok {
          graph.vertices[v1] = &Vertex{edges: make(map[int64]int64), explored: false}
        }
        _, ok = graph.vertices[v2]
        if !ok {
          graph.vertices[v2] = &Vertex{edges: make(map[int64]int64), explored: false}
        }
        _, ok =  graph_rev.vertices[v1]
        if !ok {
          graph_rev.vertices[v1] = &Vertex{edges: make(map[int64]int64), explored: false}
        }
        _, ok =  graph_rev.vertices[v2]
        if !ok {
          graph_rev.vertices[v2] = &Vertex{edges: make(map[int64]int64), explored: false}
        }
        graph.vertices[v1].edges[v2] = v2
        graph_rev.vertices[v2].edges[v1] = v1

      } 
      DFS_Loopback(&graph_rev)
      DFS_Loop(&graph, graph_rev.ordering)
      for name, value := range graph.highscoretable { 
        fmt.Println(name, value.name, value.value)
      }

    } else { 
      fmt.Printf("The file %v does not exist!\n", flag.Arg(i)) 
    } 
  } 
} 

type Vertex struct { 
  edges map[int64]int64
  explored bool
  leader int64
}

type Graph struct {
  vertices map[int64]*Vertex
  t int64
  ordering []int64
  leaders map[int64]int64
  highscoretable map[int64]*scc
}

type scc struct {
  name int64
  value int64
}

func DFS_Loopback(graph *Graph) {
  //initialize the highscoretable
  for i := 0; i< 5; i ++ {
    graph.highscoretable[int64(i)] = &scc{name: 0, value: 0}
  }

  fmt.Println("DFS Loopback")
  for name, vertex := range graph.vertices { 
    if !vertex.explored {
      DFS(graph, name, name)
    }
  }
}

func DFS_Loop(graph *Graph, order []int64) {
  //loop through the nodes in the order specified
  for i := 0; i< 5; i ++ {
    graph.highscoretable[int64(i)] = &scc{name: 0, value: 0}
  }
  for i := len(order) - 1; i >= 0; i-- {
    name := order[i]
    vertex := graph.vertices[name]
    if !vertex.explored {
      DFS(graph, name, name)
    }
  }
}

func DFS(graph *Graph, start int64, leader int64) {
  //explore all the edges we can reach from this vertex
  vertex := graph.vertices[start]
  vertex.explored = true
  vertex.leader = leader
  graph.leaders[leader] = graph.leaders[leader] + 1


  //maintain leaders list
  lowestIndex := 5
  lowestValue := graph.leaders[leader]
  found:= false
  for i := 0; i < 5; i++ {
    if graph.highscoretable[int64(i)].name == leader {
      graph.highscoretable[int64(i)].value ++
      //we're done
      found = true
      break;
    }
    if graph.highscoretable[int64(i)].value < lowestValue {
      lowestIndex = i
      lowestValue = graph.highscoretable[int64(i)].value
    }
  }
  if !found && lowestIndex < 5 {
    graph.highscoretable[int64(lowestIndex)].name = leader
    graph.highscoretable[int64(lowestIndex)].value = graph.leaders[leader]
  }

  // for name, value := range graph.highscoretable { 
  //   fmt.Println(name, value.name, value.value)
  // }
  // var bob int
  // fmt.Scanf("%d", &bob)

  for name, _ := range graph.vertices[start].edges {
    newvert := graph.vertices[name]
    if !newvert.explored {
      DFS(graph, name, leader)
    }
  }

  graph.t ++
  graph.ordering = append(graph.ordering, start)
}