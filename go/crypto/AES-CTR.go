package main 

import ( 
  "os"
  "fmt"
  "encoding/hex"
  "crypto/aes"
  "bytes"
  "encoding/binary"
) 

func main() { 
  cipherText, _ := hex.DecodeString("770b80259ec33beb2561358a9f2dc617e46218c0a53cbeca695ae45faa8952aa0e311bde9d4e01726d3184c344510000000000000000000000")
  key, _ := hex.DecodeString("36f18357be4dbd77f050515c73fcf9f2")
  //cipherText, _ := hex.DecodeString("69dda8455c7dd4254bf353b773304eec0ec7702330098ce7f7520d1cbbb20fc388d1b0adb5054dbd7370849dbf0b88d393f252e764f1f5f7ad97ef79d59ce29f5f51eeca32eabedd9afa9329000000000000000000000000")
  //key, _ := hex.DecodeString("36f18357be4dbd77f050515c73fcf9f2")
 
  messageText := make([]byte, len(cipherText))
  fmt.Printf("% x\n", key)
  blocks := len(cipherText) / 16
  fmt.Println(blocks)
  c, err := aes.NewCipher(key)
  if err != nil {
    fmt.Println("Error: NewCipher(%d bytes) = %s", len(key), err);
    os.Exit(-1)
  }

  var iv int64
  //fmt.Printf("% x\n", cipherText[8: 16])
  buf := bytes.NewBuffer(cipherText[8: 16])
  err = binary.Read(buf, binary.BigEndian, &iv)
  if err != nil {
    fmt.Println("Error: ", err)
    os.Exit(-1)
  }
  fmt.Println("iv:", iv)
  for i := 1; i < blocks ; i ++ {
    ivPlus := iv + int64(i) - 1
    buf2 := new(bytes.Buffer)
    err3 := binary.Write(buf2, binary.BigEndian, ivPlus)
    if err != nil {
      fmt.Println("binary.Write failed:", err3)
    }
    //fmt.Printf("% x", buf2.Bytes())
    decryption := make([]byte, 16)
    message := make([]byte, 16)
    for j:=0; j < 8; j ++ {
      message[j] = cipherText[j]
    }
    for k:=0; k<8; k++ {
      message[k+8] = buf2.Bytes()[k]
    }

    fmt.Printf("% x\n", message)

    c.Encrypt(decryption, message)

    startIndex := i * 16
    endIndex := startIndex + 16
    for index, char := range Xor(decryption, cipherText[startIndex : endIndex]) {
      messageText[startIndex + index - 16] = char
    }
  }

  fmt.Printf("% x\n", messageText)
  fmt.Printf("%s\n", messageText)

}

func Xor (a []byte, b [] byte) []byte {
  res := make([]byte, len(a))
  for i := 0; i< len(a); i ++ {
    res[i] = a[i] ^ b[i]
  }
  return res
}