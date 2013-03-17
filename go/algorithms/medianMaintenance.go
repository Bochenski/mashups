package main 

import ( 
  "flag" 
  "fmt" 
  "os" 
  "io"
  "bufio" 
  "strconv"
  "container/heap"
) 

type MinHeap []int64
type MaxHeap []int64

func (hp MinHeap) Len() int { return len(hp) }
func (hp MaxHeap) Len() int { return len(hp) }

func (hp MinHeap) Less(i, j int) bool { 
  //in a min heap we want to return the smallest
  return hp[i] < hp[j]
}

func (hp MaxHeap) Less(i, j int) bool {
  //in a max heap we want to return the largest
  return hp[i] > hp[j]
}

func (hp MinHeap) Swap(i, j int) {
  hp[i], hp[j] = hp[j], hp[i]
}

func (hp MaxHeap) Swap(i, j int) {
  hp[i], hp[j] = hp[j], hp[i]
}

func (hp *MaxHeap) Push(x interface{}) {
  a := *hp
  n := len(a)
  a = a[0 : n+1]
  a[n] = x.(int64)
  *hp = a
}

func (hp *MinHeap) Push(x interface{}) {
  a := *hp
  n := len(a)
  a = a[0 : n+1]
  a[n] = x.(int64)
  *hp = a  
}

func (hp *MaxHeap) Pop() interface{} {
  a := *hp
  n := len(a)
  item := a[n-1]
  *hp = a[0 : n-1]
  return item
}

func (hp *MinHeap) Pop() interface{} {
  a := *hp
  n := len(a)
  item := a[n-1]
  *hp = a[0 : n-1]
  return item
}

func main() { 
  fmt.Printf("Reading files...\n"); 
  flag.Parse(); 

  for i :=0; i < flag.NArg(); i++ { 
    fmt.Printf("[File: %v]\n", flag.Arg(i)); 
    fin, err := os.Open(flag.Arg(i)) 
    if (err==nil) { 
      r := bufio.NewReader(fin) 
      leftHeap := make(MaxHeap,0,10000)
      rightHeap := make(MinHeap,0,10000)
      
      var median, medianSum int64 = 0, 0
      for line, _, err := r.ReadLine(); err!=io.EOF; line, _, err = r.ReadLine() { 
        //split the string
        next, _ := strconv.ParseInt(string(line),10,32)
        
        if next < int64(median) {
          fmt.Println("pushing to left heap", next)
          heap.Push(&leftHeap,next)
        } else {
          fmt.Println("pushing to right heap", next)
          heap.Push(&rightHeap,next)
        }
       
        fmt.Println("left heap length", len(leftHeap))
        fmt.Println("right heap length", len(rightHeap))

        if len(leftHeap) - len(rightHeap) > 1 {
          fmt.Println("switching left to right")
          move := heap.Pop(&leftHeap)
          heap.Push(&rightHeap,move)
          fmt.Println("left heap length", len(leftHeap))
          fmt.Println("right heap length", len(rightHeap))
        }

        if len(rightHeap) - len(leftHeap) > 1 {
          fmt.Println("switching right to left")
          move := heap.Pop(&rightHeap)
          heap.Push(&leftHeap,move)
          fmt.Println("left heap length", len(leftHeap))
          fmt.Println("right heap length", len(rightHeap))
        }


        if len(leftHeap) >= len(rightHeap) {
          median = heap.Pop(&leftHeap).(int64)
          heap.Push(&leftHeap,median)
        } else {
          median = heap.Pop(&rightHeap).(int64)
          heap.Push(&rightHeap, median)
        }

        medianSum += median
        fmt.Println(next,median,medianSum)

      }

    } else { 
      fmt.Printf("The file %v does not exist!\n", flag.Arg(i)) 
    } 
  } 
} 
