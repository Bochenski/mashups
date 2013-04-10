package main 

import ( 
  "fmt"
  "encoding/hex"
  //"container/heap"
) 

func main() { 

  // test1 := "hello"
  // test1Bytes := []byte(test1)
  // resTest1 := XorWithSpace(test1Bytes)

  // fmt.Printf("% x\n", test1Bytes)
  // fmt.Printf("% x\n", resTest1)
  // fmt.Printf("%s\n", resTest1)
  // test2 := "     "
  // cipher2Hex := hex.EncodeToString([]byte(test2))
  cipherBytes := make([][]byte, 11)

  cipherBytes[0], _ = hex.DecodeString("315c4eeaa8b5f8aaf9174145bf43e1784b8fa00dc71d885a804e5ee9fa40b16349c146fb778cdf2d3aff021dfff5b403b510d0d0455468aeb98622b137dae857553ccd8883a7bc37520e06e515d22c954eba5025b8cc57ee59418ce7dc6bc41556bdb36bbca3e8774301fbcaa3b83b220809560987815f65286764703de0f3d524400a19b159610b11ef3e")
  cipherBytes[1], _ = hex.DecodeString("234c02ecbbfbafa3ed18510abd11fa724fcda2018a1a8342cf064bbde548b12b07df44ba7191d9606ef4081ffde5ad46a5069d9f7f543bedb9c861bf29c7e205132eda9382b0bc2c5c4b45f919cf3a9f1cb74151f6d551f4480c82b2cb24cc5b028aa76eb7b4ab24171ab3cdadb8356f")
  cipherBytes[2], _ = hex.DecodeString("32510ba9a7b2bba9b8005d43a304b5714cc0bb0c8a34884dd91304b8ad40b62b07df44ba6e9d8a2368e51d04e0e7b207b70b9b8261112bacb6c866a232dfe257527dc29398f5f3251a0d47e503c66e935de81230b59b7afb5f41afa8d661cb")
  cipherBytes[3], _ = hex.DecodeString("32510ba9aab2a8a4fd06414fb517b5605cc0aa0dc91a8908c2064ba8ad5ea06a029056f47a8ad3306ef5021eafe1ac01a81197847a5c68a1b78769a37bc8f4575432c198ccb4ef63590256e305cd3a9544ee4160ead45aef520489e7da7d835402bca670bda8eb775200b8dabbba246b130f040d8ec6447e2c767f3d30ed81ea2e4c1404e1315a1010e7229be6636aaa")
  cipherBytes[4], _ = hex.DecodeString("3f561ba9adb4b6ebec54424ba317b564418fac0dd35f8c08d31a1fe9e24fe56808c213f17c81d9607cee021dafe1e001b21ade877a5e68bea88d61b93ac5ee0d562e8e9582f5ef375f0a4ae20ed86e935de81230b59b73fb4302cd95d770c65b40aaa065f2a5e33a5a0bb5dcaba43722130f042f8ec85b7c2070")
  cipherBytes[5], _ = hex.DecodeString("32510bfbacfbb9befd54415da243e1695ecabd58c519cd4bd2061bbde24eb76a19d84aba34d8de287be84d07e7e9a30ee714979c7e1123a8bd9822a33ecaf512472e8e8f8db3f9635c1949e640c621854eba0d79eccf52ff111284b4cc61d11902aebc66f2b2e436434eacc0aba938220b084800c2ca4e693522643573b2c4ce35050b0cf774201f0fe52ac9f26d71b6cf61a711cc229f77ace7aa88a2f19983122b11be87a59c355d25f8e4")
  cipherBytes[6], _ = hex.DecodeString("32510bfbacfbb9befd54415da243e1695ecabd58c519cd4bd90f1fa6ea5ba47b01c909ba7696cf606ef40c04afe1ac0aa8148dd066592ded9f8774b529c7ea125d298e8883f5e9305f4b44f915cb2bd05af51373fd9b4af511039fa2d96f83414aaaf261bda2e97b170fb5cce2a53e675c154c0d9681596934777e2275b381ce2e40582afe67650b13e72287ff2270abcf73bb028932836fbdecfecee0a3b894473c1bbeb6b4913a536ce4f9b13f1efff71ea313c8661dd9a4ce")
  cipherBytes[7], _ = hex.DecodeString("315c4eeaa8b5f8bffd11155ea506b56041c6a00c8a08854dd21a4bbde54ce56801d943ba708b8a3574f40c00fff9e00fa1439fd0654327a3bfc860b92f89ee04132ecb9298f5fd2d5e4b45e40ecc3b9d59e9417df7c95bba410e9aa2ca24c5474da2f276baa3ac325918b2daada43d6712150441c2e04f6565517f317da9d3")
  cipherBytes[8], _ = hex.DecodeString("271946f9bbb2aeadec111841a81abc300ecaa01bd8069d5cc91005e9fe4aad6e04d513e96d99de2569bc5e50eeeca709b50a8a987f4264edb6896fb537d0a716132ddc938fb0f836480e06ed0fcd6e9759f40462f9cf57f4564186a2c1778f1543efa270bda5e933421cbe88a4a52222190f471e9bd15f652b653b7071aec59a2705081ffe72651d08f822c9ed6d76e48b63ab15d0208573a7eef027")
  cipherBytes[9], _ = hex.DecodeString("466d06ece998b7a2fb1d464fed2ced7641ddaa3cc31c9941cf110abbf409ed39598005b3399ccfafb61d0315fca0a314be138a9f32503bedac8067f03adbf3575c3b8edc9ba7f537530541ab0f9f3cd04ff50d66f1d559ba520e89a2cb2a83")
  cipherBytes[10], _ = hex.DecodeString("32510ba9babebbbefd001547a810e67149caee11d945cd7fc81a05e9f85aac650e9052ba6a8cd8257bf14d13e6f0a803b54fde9e77472dbff89d71b57bddef121336cb85ccb8f3315f4b52e301d16e9f52f904")

  // temp := make([][]byte, 55)
  // candidateMessages := make([][]byte, 110)
  // k :=0
  // for i := 0; i < 11; i ++ {
  //   for j:= i+1; j < 11; j++ {
  //     temp[i] = XorWithSpace(XorByteStrings(cipherBytes[i], cipherBytes[j]))
  //     // fmt.Printf("% x\n", temp[i])
  //     candidateMessages[k] = XorByteStrings(temp[i], cipherBytes[i])
  //     k++
  //     candidateMessages[k] = XorByteStrings(temp[i], cipherBytes[j])
  //     k++
  //   }
  // }

  maxLen :=200
  // for _, candidate := range candidateMessages {
  //   if len(candidate) > maxLen {
  //     maxLen = len(candidate)
  //   }

  // }

  guessedKey:= make([]byte, maxLen)

  // for i := 0; i < maxLen; i++  {
  //   keyCounts:= make(map[byte]int)
  //   for _, candidate := range candidateMessages {
  //     if i < len(candidate) {
  //       if candidate[i] > 65 && candidate[i] < 122 {
  //         _, ok := keyCounts[candidate[i]]
  //         if !ok {
  //           keyCounts[candidate[i]] = 0
  //         }
  //         keyCounts[candidate[i]] ++
  //       }
  //     }
  //   }
  //   //now push these key candidates into a heap
  //   //and pick the top one
  //   kc := make(MaxHeap, 0, len(keyCounts))
  //   for k, v := range keyCounts {
  //     charCountItem := &CharCount{value: k, count: v}
  //     heap.Push(&kc, charCountItem)
  //   }
  //   item := heap.Pop(&kc).(*CharCount)
  //   guessedKey[i] = item.value
  // }

  messageBytes := make([][]byte, 11)
  messageBytes[0] = []byte("we can factor the number 15 with quantum computing")
  messageBytes[1] = []byte("Euler would probably enjoy that now his theorem")
  //messageBytes[2] = []byte("THE NicE THING ABOUT{{{{{{{{{IS{HOW WE CAN P O R")
 messageBytes[3] = []byte("The ciphertext produced by a weak encryption algorithm looks as good as ciphertext produced by a strong encryption algorithm")
  //messageBytes[4] = []byte("YOU{DON{T{WANT{TO{BUY{A{SET{OF{CAR{KEYS{FROM{A{GUY{WHO{SPECIALIZES{IN{STEALING{CARS")
  messageBytes[4] = []byte("You don't want to buy a set of car keys from a guy who specializes in stealing cars - Marc Rotenberg")
  messageBytes[5] = []byte("There are two types of cryptography - that which will keep secrets safe from your little sister, and that which will keep secrets safe from your government")
  messageBytes[6] = []byte("There are two types of cyptography: one that allows the Government to use brute force to break the code, and one that requires the Government to use brute force to break you")
  //messageBytes[8] = []byte("A{{PRIVATE{KEY{{{ENCRYPTION{SCHEME{STATES{{{ALGORITHMS{{NAMELY{A{PROCEDURE{FOR{GENERATING")

  // messageBytes[9] = []byte("{THE{CONCISE{OXFORD{ICTIONARY")
  //messageBytes[10] = []byte("THE{SECRET{MESSAGE{IS:{WHEN{USING{A{STREAM{CIPHER{NEVER{USE{THE{KEY{MORE{THAN{ONCE")
  theKey := guessedKey

  for index, message := range messageBytes {
    for i:=0; i < len(message); i++ {
      if message[i] != 123 {
        theKey[i] = message[i] ^ cipherBytes[index][i]
      }
    }
  }

  // hexKey, _ := hex.DecodeString("66194e89")
  // for i :=0; i < len(hexKey); i ++ {
  //   theKey[i] = hexKey[i]
  // }

  for i:= 0; i < 11; i++ {
    fmt.Println("Cipher Text: ", i+1)
    //fmt.Printf("% x\n", cipherBytes[i])
    //fmt.Println("Key: ")
    //fmt.Printf("% x\n", theKey)
    //fmt.Println("Message Text")
    decryption := Decrypt(cipherBytes[i], theKey)
    //fmt.Printf("% x\n", decryption)
    fmt.Printf("%s\n", Pretty(decryption))
    fmt.Println()
  }

  plaintext := Decrypt(cipherBytes[10], theKey)
  fmt.Println("key")
  fmt.Printf("% x\n", theKey)
  fmt.Println("ciphertext")
  fmt.Printf("% x\n", cipherBytes[10])
  fmt.Println("plaintext")
  fmt.Printf("% x\n", plaintext)
  fmt.Printf("%s\n", Pretty(plaintext))
}

func Pretty(m []byte) []byte {
  n := len(m)
  b := make([]byte, n)
  for i := 0; i<n; i++ {
    if m[i] < 32 || m[i] > 126 {
      b[i] = 63
    } else {
      b[i] = m[i]
    }
  }
  return b
}

func Decrypt(m []byte, k []byte ) []byte {
  n := len(m)
  b := make([]byte, n)
  for i := 0; i< n; i++ {
    if i < len(k) {
      b[i] = m[i] ^ k[i]
    }
  }
  return b
}

func RemoveSpaces(s []byte) []byte {
  n := len(s)
  b := make([]byte, n)
  for i := 0; i< n; i++ {
    if s[i] < 33 || s[i] > 90 {
      b[i] = 0
    } else {
      b[i] = s[i]
    }
  }
  return b
}

func AZOnly(s []byte) []byte {
  n := len(s)
  b := make([]byte, n)
  for i := 0; i< n; i++ {
    if s[i] < 33 || s[i] > 90 {
      b[i] = 0
    } else {
      b[i] = s[i]
    }
  }
  return b
}

func AZSpaceOnly(s []byte) []byte {
  n := len(s)
  b := make([]byte, n)
  for i := 0; i< n; i++ {
    if s[i] < 32 || s[i] > 127 {
      b[i] = 32
    } else {
      b[i] = s[i]
    }
  }
  return b
}


func XorWithSpace(s []byte) []byte {
  n := len(s)
  b := make([]byte, n)
  for i := 0; i< n; i++ {
    b[i] = s[i] ^ byte(' ')
  }
  return b
}

func XorByteStrings(string1 []byte, string2 []byte) []byte{

  // make string1 the shorter string
  if len(string2) < len(string1) {
    temp := string1
    string1 = string2
    string2 = temp
  }

  n := len(string1)
  m := len(string2)
  b := make([]byte, m)
  for i := 0; i < n; i++ {
    b[i] = string1[i] ^ string2[i]
  }

  for i := n; i < m; i++ {
    b[i] = string2[i]
  }
  // fmt.Printf("% x\n", string(b))
  return b
}

type CharCount struct {
  value byte
  count int
  index int
}

type MaxHeap []*CharCount

func (hp MaxHeap) Len() int { return len(hp) }

func (hp MaxHeap) Less(i, j int) bool {
  //in a max heap we want to return the largest
  return hp[i].count > hp[j].count
}

func (hp MaxHeap) Swap(i, j int) {
  hp[i], hp[j] = hp[j], hp[i]
  hp[i].index = i
  hp[j].index = j
}

func (hp *MaxHeap) Push(x interface{}) {
  a := *hp
  n := len(a)
  a = a[0 : n+1]
  item := x.(*CharCount)
  item.index = n
  a[n] = item
  *hp = a
}

func (hp *MaxHeap) Pop() interface{} {
  a := *hp
  n := len(a)
  item := a[n-1]
  item.index = -1 //for safety
  *hp = a[0 : n-1]
  return item
}