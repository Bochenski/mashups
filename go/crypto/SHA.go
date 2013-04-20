package main

import (
  "io/ioutil"
  "fmt"
  "crypto/sha256"
  "encoding/hex"
)

func main() {
    // read whole the file
    b, err := ioutil.ReadFile("file")
    if err != nil { panic(err) }
    blocks := (len(b) / 1024)
    if len(b) % 1024 == 0 {
      blocks --
    }
    firstBlock := true
    currentHash := sha256.New()
    for i := blocks; i>=0; i -- {
      startIndex := i * 1024
      endIndex := startIndex + 1024
      block := b[startIndex:endIndex]
      if firstBlock{
        block = b[startIndex:]
      } else { 
        //append current hash to block
        block = currentHash.Sum(block)
      }
      firstBlock = false
      //hash current block
      currentHash = sha256.New()
      currentHash.Write(block)
    }
    resultingHash := hex.EncodeToString(currentHash.Sum(make([]byte, 0)))
    fmt.Println(resultingHash)
}