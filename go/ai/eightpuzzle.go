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
  problem := []int{8, 1, 7, 4, 5, 6, 2, 0, 3}
  searchSpace = append(searchSpace, Vertex{length: 0, state: problem, expanded: false, reverse: 0})
  
  bestNodeIndex := 0

  //is the best node a goal node? If so we're done
  for searchSpace[bestNodeIndex].computeManhattanDistance(3) > 0 {
    //pick the unexpanded node with the most promising estimate
    bestEstimate:= 100000
    found := false
    //fmt.Println("nodes in search space: ", len(searchSpace))
    for i := 0; i < len(searchSpace); i ++ {
      //fmt.Println("state: ",i, searchSpace[i].state, "expanded", searchSpace[i].expanded, "length", searchSpace[i].length, "manhattan", searchSpace[i].computeManhattanDistance(3))
      if !searchSpace[i].expanded {
        found = true
        estimate := searchSpace[i].length + searchSpace[i].computeManhattanDistance(3)
        if estimate < bestEstimate {
          bestEstimate = estimate
          bestNodeIndex = i
        }        
      }
    }

    if !found {
      fmt.Println("Run out of unexplored nodes - no solution")
      break
    }

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

          searchSpace = append(searchSpace, newVertex)
        }
        if math.Mod(float64(i), 3) < 2 && searchSpace[bestNodeIndex].reverse != 1 {
          //we can consider the swap to the right
          newState := make([]int, len(searchSpace[bestNodeIndex].state))
          copy(newState, searchSpace[bestNodeIndex].state)
          newVertex := Vertex{length: searchSpace[bestNodeIndex].length +1, state: newState, expanded: false, reverse: 2}
          newVertex.state[i] = newVertex.state[i+1]
          newVertex.state[i+1] = 0
          searchSpace = append(searchSpace, newVertex)
        }
        if math.Floor(float64(i) / 3) > 0 && searchSpace[bestNodeIndex].reverse != 4{
          //we can consider the swap above
          newState := make([]int, len(searchSpace[bestNodeIndex].state))
          copy(newState, searchSpace[bestNodeIndex].state)
          newVertex := Vertex{length: searchSpace[bestNodeIndex].length +1, state: newState, expanded: false, reverse: 3}
          newVertex.state[i] = newVertex.state[i-3]
          newVertex.state[i-3] = 0    
          searchSpace = append(searchSpace, newVertex)
        }
        if math.Floor(float64(i) / 3) < 2 && searchSpace[bestNodeIndex].reverse != 3{
          //we can consider the swap below
          newState := make([]int, len(searchSpace[bestNodeIndex].state))
          copy(newState, searchSpace[bestNodeIndex].state)
          newVertex := Vertex{length: searchSpace[bestNodeIndex].length +1, state: newState, expanded: false, reverse: 4}
          newVertex.state[i] = newVertex.state[i+3]
          newVertex.state[i+3] = 0
          searchSpace = append(searchSpace, newVertex)
        }
        break
      }
    }
    searchSpace[bestNodeIndex].expanded = true

  }

  fmt.Println("Best Length", searchSpace[bestNodeIndex].length)
  return
}

func (v *Vertex) computeManhattanDistance(edgeSize float64) int {

  var result float64 = 0
  for i := 0; i < len(v.state); i ++ {

    actualColumn := math.Mod(float64(i), edgeSize)
    actualRow := math.Floor(float64(i) / edgeSize)
    targetColumn := math.Mod(float64(v.state[i]) , edgeSize)
    targetRow := math.Floor(float64(v.state[i]) / edgeSize)

    dist := math.Abs(targetColumn - actualColumn) + math.Abs(targetRow - actualRow)

    result = result + dist
  }

  return int(result)
}