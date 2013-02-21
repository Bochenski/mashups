package main

import (
  "fmt"
  "math"
)

type Vertex struct {
  length int
  state []int
  expanded bool
  reverse int
}

func main() {
  var searchSpace []Vertex
  visitedStates := make(map[float64]Vertex)
  pathLengths := make(map[int]int)

  problem := []int{0, 1, 2, 3, 4, 5, 6, 7, 8}
  searchSpace = append(searchSpace, Vertex{length: 0, state: problem, expanded: false, reverse: 0})
  
  bestNodeIndex := 0

  //is the best node a goal node? If so we're done
  for {
    //pick the first unexpanded node

    found := false
    //fmt.Println("nodes in search space: ", len(searchSpace))
    for i := 0; i < len(searchSpace); i ++ {
      //fmt.Println("state: ",i, searchSpace[i].state, "expanded", searchSpace[i].expanded, "length", searchSpace[i].length, "manhattan", searchSpace[i].computeManhattanDistance(3))
      if !searchSpace[i].expanded {
        found = true
        bestNodeIndex = i
        break
      }
    }

    if !found {
      fmt.Println("Search is complete")
      break
    }

    var ok bool

    //otherwise we'd better carry on our search
    for i := 0; i < len(searchSpace[bestNodeIndex].state); i ++ {
      if searchSpace[bestNodeIndex].state[i] == 0 {
        if math.Mod(float64(i), 3) > 0 && searchSpace[bestNodeIndex].reverse != 2 {

          //we can consider the swap to the left
          newState := make([]int, len(searchSpace[bestNodeIndex].state))
          copy(newState, searchSpace[bestNodeIndex].state)
          newVertex := Vertex{length: searchSpace[bestNodeIndex].length +1, state: newState, expanded: false, reverse: 1}
          newVertex.state[i] = newVertex.state[i-1]
          newVertex.state[i-1] = 0
          hash := newVertex.hash()
          _, ok = visitedStates[hash]
          if !ok {
            searchSpace = append(searchSpace, newVertex)
            visitedStates[hash] = newVertex
            pathLengths[newVertex.length] = pathLengths[newVertex.length] + 1
          } 
        }
        if math.Mod(float64(i), 3) < 2 && searchSpace[bestNodeIndex].reverse != 1 {
          //we can consider the swap to the right
          newState := make([]int, len(searchSpace[bestNodeIndex].state))
          copy(newState, searchSpace[bestNodeIndex].state)
          newVertex := Vertex{length: searchSpace[bestNodeIndex].length +1, state: newState, expanded: false, reverse: 2}
          newVertex.state[i] = newVertex.state[i+1]
          newVertex.state[i+1] = 0
          hash := newVertex.hash()
          _, ok = visitedStates[hash]
          if !ok {
            searchSpace = append(searchSpace, newVertex)
            visitedStates[hash] = newVertex
            pathLengths[newVertex.length] = pathLengths[newVertex.length] + 1
          } 
        }
        if math.Floor(float64(i) / 3) > 0 && searchSpace[bestNodeIndex].reverse != 4{
          //we can consider the swap above
          newState := make([]int, len(searchSpace[bestNodeIndex].state))
          copy(newState, searchSpace[bestNodeIndex].state)
          newVertex := Vertex{length: searchSpace[bestNodeIndex].length +1, state: newState, expanded: false, reverse: 3}
          newVertex.state[i] = newVertex.state[i-3]
          newVertex.state[i-3] = 0    
          hash := newVertex.hash()
          _, ok = visitedStates[hash]
          if !ok {
            searchSpace = append(searchSpace, newVertex)
            visitedStates[hash] = newVertex
            pathLengths[newVertex.length] = pathLengths[newVertex.length] + 1
          } 
        }
        if math.Floor(float64(i) / 3) < 2 && searchSpace[bestNodeIndex].reverse != 3{
          //we can consider the swap below
          newState := make([]int, len(searchSpace[bestNodeIndex].state))
          copy(newState, searchSpace[bestNodeIndex].state)
          newVertex := Vertex{length: searchSpace[bestNodeIndex].length +1, state: newState, expanded: false, reverse: 4}
          newVertex.state[i] = newVertex.state[i+3]
          newVertex.state[i+3] = 0
          hash := newVertex.hash()
          _, ok = visitedStates[hash]
          if !ok {
            searchSpace = append(searchSpace, newVertex)
            visitedStates[hash] = newVertex
            pathLengths[newVertex.length] = pathLengths[newVertex.length] + 1
          }
        }
        break
      }
    }
    searchSpace[bestNodeIndex].expanded = true
  }

  for i := 0; i < len(pathLengths); i ++ {
    fmt.Println(i,  pathLengths[i])
  }

  return
}


func (v *Vertex) hash() float64 {
  var result float64 = 0
  for i := 0; i < len(v.state); i ++ {
    result = result + float64(v.state[i]) * math.Pow(10,float64(i))
  }

  return result
}