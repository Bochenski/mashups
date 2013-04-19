package main 

import ( 
  "fmt"
  "encoding/hex"
) 

func main() { 
  text1, _  := hex.DecodeString("e0d541314e00102d6dfca8bc007b6c8a")
  text2, _  := hex.DecodeString("11111111111111111111111111111111")

 

  fmt.Printf("%x\n", Xor(text1, text2))

}

func Xor (a []byte, b [] byte) []byte {
  res := make([]byte, len(a))
  for i := 0; i< len(a); i ++ {
    res[i] = a[i] ^ b[i]
  }
  return res
}