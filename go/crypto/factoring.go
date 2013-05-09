package main

import (
  "fmt"
  "math/big"
  "github.com/cznic/mathutil"
  "encoding/hex"
)

func main() {
  fmt.Println("Question 1:")
  q1()
  fmt.Println("Question 2:")
  q2()
  fmt.Println("Question 3:")
  q3()
}

func q1() {
  n := new(big.Int)
  sqrtN := new (big.Int)
  a := new(big.Int)
  asquared := new(big.Int)
  one := new(big.Int)
  x := new(big.Int)
  xsquared := new(big.Int)
  //p := new(big.Int)

  n.SetString("179769313486231590772930519078902473361797697894230657273430081157732675805505620686985379449212982959585501387537164015710139858647833778606925583497541085196591615128057575940752635007475935288710823649949940771895617054361149474865046711015101563940680527540071584560878577663743040086340742855278549092581", 10)
  one.SetString("1",10)

  sqrtN = mathutil.SqrtBig(n)
  a.Add(sqrtN, one)
  asquared.Mul(a,a)
  
  xsquared.Sub(asquared,n)

  x = mathutil.SqrtBig(xsquared)

  p := new(big.Int)
  q := new(big.Int)
  p.Sub(a,x)
  q.Add(a,x)

  fmt.Println(a.Sub(a,x).String())


  d := new(big.Int)
  phiN := new(big.Int)
  phiN.Mul(p.Sub(p,one),q.Sub(q,one))
  e := new(big.Int)
  e.SetString("65537", 10)

  //get my decryption exponent
  d.ModInverse(e,phiN)

  c := new(big.Int)
  c.SetString("22096451867410381776306561134883418017410069787892831071731839143676135600120538004282329650473509424343946219751512256465839967942889460764542040581564748988013734864120452325229320176487916666402997509188729971690526083222067771600019329260870009579993724077458967773697817571267229951148662959627934791540",10)

  m := new(big.Int)
  m.Exp(c,d,n)
  
  fmt.Println("decrypted message:")
  fmt.Printf("%x\n", m.Bytes())

  //020805907610b524330594e51d5dbbf643f09603731e9817111392d0c64e2739959a092d4daf979d387520ea7e577af9eb50a29f736925e810ab2fb4640e091a0f73252cb669d5b62b26764190ed188239fe71e1a7cb9e935d2db55c98b024e1dae46d00  
  answer, _ := hex.DecodeString("466163746f72696e67206c65747320757320627265616b205253412e")
  fmt.Printf("%s\n", answer)
}

func q2() {
  n := new(big.Int)
  a := new(big.Int)
  asquared := new(big.Int)
  one := new(big.Int)
  x := new(big.Int)
  xsquared := new(big.Int)
  p := new(big.Int)
  q := new(big.Int)
  candidate := new(big.Int)

  n.SetString("648455842808071669662824265346772278726343720706976263060439070378797308618081116462714015276061417569195587321840254520655424906719892428844841839353281972988531310511738648965962582821502504990264452100885281673303711142296421027840289307657458645233683357077834689715838646088239640236866252211790085787877", 10)
  one.SetString("1",10)

  a = mathutil.SqrtBig(n)
  for {
    a.Add(a, one)
    asquared.Mul(a,a)
    xsquared.Sub(asquared,n)
    x = mathutil.SqrtBig(xsquared)
    p.Sub(a,x)
    q.Add(a,x)
    if candidate.Mul(p,q).Cmp(n) == 0 {
      fmt.Println(p.String())
      break
    }
  }
}

func q3() {
  n := new(big.Int)
  a := new(big.Int)
  sixN := new(big.Int)
  one := new(big.Int)
  two := new(big.Int)
  four := new(big.Int)
  six := new(big.Int)
  twentyFour := new(big.Int)
  p := new(big.Int)
  q := new(big.Int)
  candidate := new(big.Int)
  asquared := new(big.Int)
  twentyFourN := new(big.Int)
  xsquared := new(big.Int)
  x:= new(big.Int)

  n.SetString("720062263747350425279564435525583738338084451473999841826653057981916355690188337790423408664187663938485175264994017897083524079135686877441155132015188279331812309091996246361896836573643119174094961348524639707885238799396839230364676670221627018353299443241192173812729276147530748597302192751375739387929", 10)
  one.SetString("1",10)
  two.SetString("2",10)
  four.SetString("4",10)
  six.SetString("6",10)
  twentyFour.SetString("24", 10)

  twentyFourN.Mul(n,twentyFour)
  sixN.Mul(n,six)
  a = mathutil.SqrtBig(sixN)
  a.Mul(a,two)
  for {
    a.Add(a, one)

    asquared.Mul(a,a)

    xsquared.Sub(asquared,twentyFourN)
    x = mathutil.SqrtBig(xsquared)
    p.Sub(a,x)
    p.Div(p,six)

    q.Add(a,x)
    q.Div(q,four)
    if candidate.Mul(p,q).Cmp(n) == 0 {
      fmt.Println(p.String())
      break
    }
  }
}