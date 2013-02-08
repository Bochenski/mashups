import std.stdio;
import std.file;
import std.conv;
import std.math;

int main()
{
  int[] initialConfiguration = new int[100000];
  int i = 0;
  foreach( line; File("IntegerArray.txt").byLine())
  {
    initialConfiguration[i] = parse!int(line);
    i ++;
  }
  writeln("array imported");
  ulong inversions = sortAndCount(initialConfiguration);
  writeln("inversions:");
  writeln(inversions);
  return 0;

}

ulong sortAndCount(ref int[] A) {
  
  //base case
  if (A.length == 1)
    return 0;


  int halfLength = to!int(floor(A.length / 2));

  //calling .dup makes a full copy of the array
  //we pass A by ref all the way through as 
  auto B = A[0 .. halfLength].dup;
  auto C = A[halfLength .. A.length].dup;

  auto x = sortAndCount(B);
  auto y = sortAndCount(C);
  auto z = mergeAndCountSplitInv(A,B,C);

  return x + y + z;
}

int mergeAndCountSplitInv(ref int[] A, int[] B, int[] C)
{
  int i = 0;
  int j = 0;
  int invCount = 0;
  foreach (k; 0 .. A.length) {

    //if we have run through all elements of B
    if (i == B.length) {
      A[k] = C[j];
      j ++;
      continue;
    }
    //if we have run through all elements of C
    if (j == C.length) {
      A[k] = B[i];
      i ++;
      continue;
    }
    if (B[i] < C[j]) {
      A[k] = B[i];
      i++;
    }
    else {
      A[k] = C[j];
      invCount += B.length - i;
      j++;
    }
  }

  return invCount; 
}
