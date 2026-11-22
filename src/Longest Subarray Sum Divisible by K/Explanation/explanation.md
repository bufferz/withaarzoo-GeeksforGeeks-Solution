# Longest Subarray Sum Divisible by K - GeeksforGeeks Solution

## Table of Contents
- [Problem Summary](#problem-summary)
- [Constraints](#constraints)
- [Intuition](#intuition)
- [Approach](#approach)
- [Data Structures Used](#data-structures-used)
- [Operations & Behavior Summary](#operations--behavior-summary)
- [Complexity](#complexity)
- [Multi-language Solutions](#multi-language-solutions)
  - [C++](#c)
  - [Java](#java)
  - [JavaScript](#javascript)
  - [Python3](#python3)
- [Step-by-step Detailed Explanation (C++, Java, JavaScript, Python3)](#step-by-step-detailed-explanation-c-java-javascript-python3)
- [Examples](#examples)
- [How to Use / Run Locally](#how-to-use--run-locally)
- [Notes & Optimizations](#notes--optimizations)
- [Author](#author)

## Problem Summary

You are given an array of integers and a positive integer k. Your task is to find the length of the longest contiguous subarray whose sum is divisible by k.

If no such subarray exists, return 0.

This is a classic competitive programming problem that appears frequently on platforms like GeeksforGeeks. It tests your understanding of prefix sums, modular arithmetic, and efficient searching of previous remainders.

## Constraints

- 1 ≤ arr.size(), k ≤ 10⁶
- -10⁶ ≤ arr[i] ≤ 10⁶

The large constraints mean a brute-force O(n²) solution will not pass. We need a linear time approach.

## Intuition

When I first saw this problem, I thought about checking every possible subarray. That works for small arrays but fails here because n can be up to one million.

I then remembered that subarray sums are easy to handle with prefix sums. The key observation is this: if two prefix sums have the same remainder when divided by k, the sum of the elements between them is divisible by k.

So instead of storing full prefix sums, I only need to remember the first time each remainder appears. That way I can always calculate the longest possible subarray for that remainder.

## Approach

I maintain a running prefix sum while iterating through the array. At every index I compute the current sum modulo k.

Because array elements can be negative, the remainder might also be negative in some languages. I normalize it so that the remainder always stays in the range [0, k-1].

I use a hash map to store the earliest index where each remainder was seen. I also store remainder 0 mapped to index -1. This handles the case when the prefix sum from the very beginning is divisible by k.

Whenever I see a remainder that already exists in the map, I calculate the distance from the earlier index to the current index and keep the maximum length found so far.

If a remainder is new, I simply record the current index.

At the end I return the maximum length.

## Data Structures Used

- Hash Map (unordered_map in C++, HashMap in Java, Map in JavaScript, dict in Python): Stores the first occurrence of each remainder. Chosen because we need fast lookups and the number of possible remainders is at most k.
- Long integer for the running sum: Prevents overflow since the sum of the array can become very large.

## Operations & Behavior Summary

1. Initialize a map and put remainder 0 at index -1.
2. Start with sum = 0 and maxLen = 0.
3. For each index i in the array:
   - Add arr[i] to the running sum.
   - Compute the normalized remainder of the sum with respect to k.
   - If this remainder already exists in the map, update maxLen with the larger of its current value and (i - previous index).
   - If the remainder is new, store the current index for it.
4. After the loop, return maxLen.

## Complexity

| Type              | Complexity     | Explanation |
|-------------------|----------------|-------------|
| Time Complexity   | O(n)           | We make a single pass through the array. Each map operation takes average constant time. |
| Space Complexity  | O(min(n, k))   | In the worst case we store one entry for every distinct remainder, and there can be at most k different remainders. |

## Multi-language Solutions

### C++
```cpp
class Solution {
  public:
    int longestSubarrayDivK(vector<int>& arr, int k) {
        unordered_map<int, int> first;
        long long sum = 0;
        int maxLen = 0;
        first[0] = -1;
        for (int i = 0; i < arr.size(); i++) {
            sum += arr[i];
            int rem = ((sum % k) + k) % k;
            if (first.count(rem)) {
                maxLen = max(maxLen, i - first[rem]);
            } else {
                first[rem] = i;
            }
        }
        return maxLen;
    }
};
```

### Java
```java
class Solution {
    public int longestSubarrayDivK(int[] arr, int k) {
        Map<Integer, Integer> first = new HashMap<>();
        long sum = 0;
        int maxLen = 0;
        first.put(0, -1);
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            int rem = (int)(((sum % k) + k) % k);
            if (first.containsKey(rem)) {
                maxLen = Math.max(maxLen, i - first.get(rem));
            } else {
                first.put(rem, i);
            }
        }
        return maxLen;
    }
}
```

### JavaScript
```javascript
/**
 * @param {number[]} arr
 * @param {number} k
 * @returns {number}
 */

class Solution {
    longestSubarrayDivK(arr, k) {
        let first = new Map();
        let sum = 0;
        let maxLen = 0;
        first.set(0, -1);
        for (let i = 0; i < arr.length; i++) {
            sum += arr[i];
            let rem = ((sum % k) + k) % k;
            if (first.has(rem)) {
                maxLen = Math.max(maxLen, i - first.get(rem));
            } else {
                first.set(rem, i);
            }
        }
        return maxLen;
    }
}
```

### Python3
```python
class Solution:
	def longestSubarrayDivK(self, arr, k):
		first = {}
		summ = 0
		max_len = 0
		first[0] = -1
		for i in range(len(arr)):
			summ += arr[i]
			rem = ((summ % k) + k) % k
			if rem in first:
				max_len = max(max_len, i - first[rem])
			else:
				first[rem] = i
		return max_len
```

## Step-by-step Detailed Explanation (C++, Java, JavaScript, Python3)

The core logic is identical across all four languages. The differences are only in syntax and how each language handles maps and negative modulo.

I start by creating a map that will remember the first time each remainder appears. I insert the pair (0, -1) right at the beginning. This is important. When the entire prefix from index 0 is divisible by k, the length becomes i - (-1) which equals i + 1. Without this entry the code would miss those cases.

I keep a running sum that starts at zero. I walk through the array from left to right. At every position I add the current element to the sum.

Next I calculate the remainder. In C++, Java, and JavaScript the % operator can return a negative value when the sum is negative. To fix this I write ((sum % k) + k) % k. This forces the remainder into the non-negative range. Python’s % operator already behaves correctly for negative numbers, but using the same formula keeps the code consistent.

After getting the remainder I check whether it already exists in the map. If it does, the subarray between the stored index and the current index has a sum divisible by k. I update the maximum length if this new length is larger.

If the remainder has never been seen before, I store the current index. I only store the first occurrence because a later occurrence of the same remainder would give a shorter subarray, and we are interested in the longest one.

When the loop finishes, the variable holding the maximum length contains the answer. If no valid subarray was found, it remains zero, which matches the problem statement.

Edge cases handled automatically:
- Empty subarray is never considered because we only update the length when we find a matching remainder.
- Single element arrays work correctly.
- All negative numbers are handled by the normalized remainder calculation.
- When k is larger than any possible sum, the only possible answer is a subarray whose sum is exactly zero.

## Examples

**Example 1**

Input: arr = [2, 7, 6, 1, 4, 5], k = 3  
Output: 4  

Trace:  
- Prefix sums: 2, 9, 15, 16, 20, 25  
- Remainders: 2, 0, 0, 1, 2, 1  
- When remainder 0 appears at index 1 and later at index 2, the longest length becomes 2.  
- When remainder 2 appears again at index 4, length becomes 4 (from index 0 to 4? wait, actually the subarray [7,6,1,4] from index 1 to 4 gives length 4).  
- Final answer is 4.

**Example 2**

Input: arr = [-2, 2, -5, 12, -11, -1, 7], k = 3  
Output: 5  

The subarray [2, -5, 12, -11, -1] has sum -3 which is divisible by 3. The algorithm correctly finds this by matching remainders.

**Example 3**

Input: arr = [1, 2, -2], k = 2  
Output: 2  

The subarray [2, -2] has sum 0. Remainder 0 is matched with the initial entry, giving length 2.

## How to Use / Run Locally

**C++**  
1. Copy the C++ code into a file named `main.cpp`.  
2. Open a terminal and run:  
   `g++ -std=c++17 main.cpp -o main`  
3. Run the executable:  
   `./main`  
   (You will need to add a main function that reads input and calls the method if you want to test with custom input.)

**Java**  
1. Copy the Java code into a file named `Solution.java`.  
2. Compile:  
   `javac Solution.java`  
3. Run (after adding a main method for testing):  
   `java Solution`

**JavaScript**  
1. Copy the JavaScript code into a file named `solution.js`.  
2. Run with Node.js:  
   `node solution.js`  
   (Again, add a small driver code at the bottom to test.)

**Python3**  
1. Copy the Python code into a file named `solution.py`.  
2. Run:  
   `python3 solution.py`  
   Add a few test cases at the bottom of the file if you want to verify the output quickly.

## Notes & Optimizations

- Using an array of size k instead of a hash map is possible and can be slightly faster when k is not too large. However the hash map version is more flexible and still passes all constraints.
- The solution correctly handles negative numbers because of the remainder normalization step. Skipping that step is a common source of wrong answers.
- If the problem asked for the number of such subarrays instead of the longest length, the same idea can be extended by counting frequencies of remainders instead of storing the first index.
- Space can be reduced to O(k) in the worst case, which is acceptable given the constraints.

## Author
[Md Aarzoo Islam](https://www.instagram.com/codewithaarzoo.in/)
