package main 

import ( 
  "flag" 
  "fmt" 
  "os" 
  "io"
  "bufio" 
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
      hash := make(map[int64]int64)
      
      for line, _, err := r.ReadLine(); err!=io.EOF; line, _, err = r.ReadLine() { 
        //split the string
        res, _ := strconv.ParseInt(string(line),10,32)
        
        _, ok := hash[res]
        if !ok {
          hash[res] = 1
        } else {
          hash[res] = hash[res] + 1
        }
      }

      count := 0
      for i := 2500; i < 4001; i ++ {
        fmt.Println(i)
        for num, _ := range hash {
          target:= int64(i) - num
          val2, ok := hash[target]
          if ok {
            if target == num {
              if val2 > 1 {
                count ++
                break
              }
            } else {
              count ++
              break
            }
          }
        }
      }


      fmt.Println(count)

    } else { 
      fmt.Printf("The file %v does not exist!\n", flag.Arg(i)) 
    } 
  } 
} 
