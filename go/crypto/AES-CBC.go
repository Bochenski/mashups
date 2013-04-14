package main 

import ( 
  "os"
  "fmt"
  "encoding/hex"
  "crypto/aes"
) 

func main() { 
  //cipherText, _ := hex.DecodeString("4ca00ff4c898d61e1edbf1800618fb2828a226d160dad07883d04e008a7897ee2e4b7465d5290d0c0e6c6822236e1daafb94ffe0c5da05d9476be028ad7c1d81")
  //key, _ := hex.DecodeString("140b41b22a29beb4061bda66b6747e14")
  cipherText, _ := hex.DecodeString("5b68629feb8606f9a6667670b75b38a5b4832d0f26e1ab7da33249de7d4afc48e713ac646ace36e872ad5fb8a512428a6e21364b0c374df45503473c5242a253")
  key, _ := hex.DecodeString("140b41b22a29beb4061bda66b6747e14")
 
  messageText := make([]byte, len(cipherText))
  fmt.Printf("% x\n", key)
  blocks := len(cipherText) / 16
  c, err := aes.NewCipher(key)
  if err != nil {
    fmt.Println("Error: NewCipher(%d bytes) = %s", len(key), err);
    os.Exit(-1)
  }
  for i := blocks - 1; i > 0; i-- {
    startIndex := i * 16
    endIndex := startIndex + 16
    fmt.Println(startIndex, endIndex)
    decryption := make([]byte, 16)
    fmt.Printf("% x\n", cipherText[startIndex: endIndex])
    c.Decrypt(decryption, cipherText[startIndex: endIndex])
    for index, char := range Xor(decryption, cipherText[(startIndex -16): (endIndex -16)]) {
      messageText[startIndex - 16 + index] = char
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