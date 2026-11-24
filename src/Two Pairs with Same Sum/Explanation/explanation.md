# Two Pairs with Same Sum - GeeksforGeeks Solution

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

You are given an array of positive integers. The task is to find four different positions a, b, c and d in the array such that the sum of the values at a and b is exactly the same as the sum of the values at c and d.

The indices must satisfy a < b and c < d. The pair (a, b) must come before the pair (c, d) in lexicographical order. If several such sets of four indices exist, you must return the one that is lexicographically smallest when written as [a, b, c, d]. If no such four indices exist, return [-1, -1, -1, -1].

This is a classic “two pairs with same sum” problem that appears frequently in competitive programming and coding interviews. The goal is to locate the lexicographically smallest quadruplet of indices that produce equal pair sums.

## Constraints

- 4 ≤ array size ≤ 10³
- 1 ≤ each array element ≤ 10⁷
- Expected time complexity: O(n²)
- Expected auxiliary space: O(n²)

## Intuition

The first thing I noticed is that n is only 1000. That means I can safely look at every possible pair of indices. There are roughly n²/2 pairs, which is fine.

If two different pairs produce the same sum, I have a candidate answer. The only extra work is making sure the four indices are all different and that I keep the lexicographically smallest set of four indices.

Grouping every pair by its sum immediately shows me which pairs can form a valid answer. Once the pairs that share a sum are collected, I only need to search inside each group for two pairs that do not share any index.

## Approach

I generate every possible pair (i, j) where i < j and store the pair under the key equal to arr[i] + arr[j]. Because the pairs are generated from left to right, each list of pairs that share a sum is already sorted by the first index and then by the second index.

For every sum that has at least two pairs I do the following:

- Take the very first pair in the list.
- Scan the remaining pairs looking for the earliest pair that uses four completely different indices.
- If such a pair exists, I have found a valid quadruplet that starts with the smallest possible first pair for that sum. I keep it as a candidate.
- If the first pair has no disjoint partner, the list can contain only a linear number of pairs. In that special case I simply check every combination of two pairs and keep the best ordered quadruplet.

After examining every sum I compare all candidates and return the overall lexicographically smallest one. If nothing was found I return [-1, -1, -1, -1].

This approach guarantees the lexicographically smallest answer while staying inside the expected O(n²) time bound.

## Data Structures Used

- Hash map (unordered_map / HashMap / Map / defaultdict) whose key is the pair sum and whose value is a list of index pairs that produce that sum.  
  Chosen because it gives constant-time average access to all pairs that share the same sum.

- Dynamic arrays / vectors / ArrayLists that store the actual (i, j) pairs for each sum.  
  They keep the pairs in the order they were generated, which is already the lexicographical order we need.

## Operations & Behavior Summary

1. Walk through every possible pair of indices and insert the pair into the map under its sum.
2. For each group of pairs that share a sum:
   - Try to match the earliest pair with the earliest later pair that has four distinct indices.
   - If that succeeds, record the quadruplet.
   - If it fails, fall back to a complete check of all pairs inside the (now small) group.
3. Keep a global best quadruplet and update it whenever a lexicographically smaller one is found.
4. After all groups are processed, return the best quadruplet or the failure indicator.

## Complexity

| Complexity          | Value   | Explanation |
|---------------------|---------|-------------|
| Time Complexity     | O(n²)   | Generating all pairs takes O(n²). For each sum the work is proportional to the size of its pair list; the total stays O(n²) in the common case. |
| Space Complexity    | O(n²)   | In the worst case every pair is stored in the map. |

## Multi-language Solutions

### C++
```cpp
class Solution {
  public:
    vector<int> findPairs(vector<int>& arr) {
        int n = arr.size();
        unordered_map<long long, vector<pair<int,int>>> mp;
        for(int i = 0; i < n; i++) {
            for(int j = i + 1; j < n; j++) {
                long long s = arr[i] + arr[j];
                mp[s].push_back({i, j});
            }
        }
        vector<int> ans = {INT_MAX, INT_MAX, INT_MAX, INT_MAX};
        for(auto &p : mp) {
            auto &pairs = p.second;
            if(pairs.size() < 2) continue;
            int a = pairs[0].first, b = pairs[0].second;
            bool found = false;
            for(size_t k = 1; k < pairs.size(); k++) {
                int c = pairs[k].first, d = pairs[k].second;
                if(a != c && a != d && b != c && b != d) {
                    vector<int> cand = {a, b, c, d};
                    if(cand < ans) ans = cand;
                    found = true;
                    break;
                }
            }
            if(!found) {
                for(size_t i = 0; i < pairs.size(); i++) {
                    for(size_t j = i + 1; j < pairs.size(); j++) {
                        int a = pairs[i].first, b = pairs[i].second;
                        int c = pairs[j].first, d = pairs[j].second;
                        if(a != c && a != d && b != c && b != d) {
                            vector<int> cand = {a, b, c, d};
                            if(cand < ans) ans = cand;
                        }
                    }
                }
            }
        }
        if(ans[0] == INT_MAX) return {-1, -1, -1, -1};
        return ans;
    }
};
```

### Java
```java
class Solution {
    public ArrayList<Integer> findPairs(int[] arr) {
        int n = arr.length;
        HashMap<Long, ArrayList<int[]>> mp = new HashMap<>();
        for(int i = 0; i < n; i++) {
            for(int j = i + 1; j < n; j++) {
                long s = (long)arr[i] + arr[j];
                mp.putIfAbsent(s, new ArrayList<>());
                mp.get(s).add(new int[]{i, j});
            }
        }
        int[] ans = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};
        for(ArrayList<int[]> pairs : mp.values()) {
            if(pairs.size() < 2) continue;
            int a = pairs.get(0)[0], b = pairs.get(0)[1];
            boolean found = false;
            for(int k = 1; k < pairs.size(); k++) {
                int c = pairs.get(k)[0], d = pairs.get(k)[1];
                if(a != c && a != d && b != c && b != d) {
                    int[] cand = {a, b, c, d};
                    if(compare(cand, ans) < 0) ans = cand;
                    found = true;
                    break;
                }
            }
            if(!found) {
                for(int i = 0; i < pairs.size(); i++) {
                    for(int j = i + 1; j < pairs.size(); j++) {
                        int aa = pairs.get(i)[0], bb = pairs.get(i)[1];
                        int cc = pairs.get(j)[0], dd = pairs.get(j)[1];
                        if(aa != cc && aa != dd && bb != cc && bb != dd) {
                            int[] cand = {aa, bb, cc, dd};
                            if(compare(cand, ans) < 0) ans = cand;
                        }
                    }
                }
            }
        }
        ArrayList<Integer> res = new ArrayList<>();
        if(ans[0] == Integer.MAX_VALUE) {
            res.add(-1); res.add(-1); res.add(-1); res.add(-1);
        } else {
            for(int x : ans) res.add(x);
        }
        return res;
    }
    private int compare(int[] x, int[] y) {
        for(int i = 0; i < 4; i++) {
            if(x[i] != y[i]) return x[i] - y[i];
        }
        return 0;
    }
}
```

### JavaScript
```javascript
/**
 * @param {number[]} arr
 * @return {number[]}
 */

class Solution {
    findPairs(arr) {
        const n = arr.length;
        const mp = new Map();
        for(let i = 0; i < n; i++) {
            for(let j = i + 1; j < n; j++) {
                const s = arr[i] + arr[j];
                if(!mp.has(s)) mp.set(s, []);
                mp.get(s).push([i, j]);
            }
        }
        let ans = [Infinity, Infinity, Infinity, Infinity];
        for(const pairs of mp.values()) {
            if(pairs.length < 2) continue;
            const [a, b] = pairs[0];
            let found = false;
            for(let k = 1; k < pairs.length; k++) {
                const [c, d] = pairs[k];
                if(a !== c && a !== d && b !== c && b !== d) {
                    const cand = [a, b, c, d];
                    if(this.lexLess(cand, ans)) ans = cand;
                    found = true;
                    break;
                }
            }
            if(!found) {
                for(let i = 0; i < pairs.length; i++) {
                    for(let j = i + 1; j < pairs.length; j++) {
                        const [aa, bb] = pairs[i];
                        const [cc, dd] = pairs[j];
                        if(aa !== cc && aa !== dd && bb !== cc && bb !== dd) {
                            const cand = [aa, bb, cc, dd];
                            if(this.lexLess(cand, ans)) ans = cand;
                        }
                    }
                }
            }
        }
        if(ans[0] === Infinity) return [-1, -1, -1, -1];
        return ans;
    }
    lexLess(x, y) {
        for(let i = 0; i < 4; i++) {
            if(x[i] !== y[i]) return x[i] < y[i];
        }
        return false;
    }
}
```

### Python3
```python
class Solution:
    def findPairs(self, arr: list[int]) -> list[int]:
        n = len(arr)
        from collections import defaultdict
        mp = defaultdict(list)
        for i in range(n):
            for j in range(i + 1, n):
                s = arr[i] + arr[j]
                mp[s].append((i, j))
        ans = [float('inf')] * 4
        for pairs in mp.values():
            if len(pairs) < 2:
                continue
            a, b = pairs[0]
            found = False
            for k in range(1, len(pairs)):
                c, d = pairs[k]
                if len({a, b, c, d}) == 4:
                    cand = [a, b, c, d]
                    if cand < ans:
                        ans = cand
                    found = True
                    break
            if not found:
                for i in range(len(pairs)):
                    for j in range(i + 1, len(pairs)):
                        a, b = pairs[i]
                        c, d = pairs[j]
                        if len({a, b, c, d}) == 4:
                            cand = [a, b, c, d]
                            if cand < ans:
                                ans = cand
        if ans[0] == float('inf'):
            return [-1, -1, -1, -1]
        return ans
```

## Step-by-step Detailed Explanation (C++, Java, JavaScript, Python3)

The core logic is identical in all four languages; only the concrete map and list types change.

First I create an empty map that will hold lists of pairs. I then run two nested loops. The outer loop chooses the left index i, the inner loop chooses the right index j > i. For every such pair I compute the sum and append the pair (i, j) to the list that belongs to that sum. Because the loops themselves walk the indices from left to right, every list ends up sorted.

After the map is filled I initialise a four-element answer array with very large values. This acts as a sentinel so the first valid quadruplet I discover becomes the current best automatically.

I then iterate over every list stored in the map. If a list contains fewer than two pairs I skip it. Otherwise I take the first pair (a, b). I walk forward through the remaining pairs looking for the first pair (c, d) whose four indices are all different. As soon as I find one I form the candidate [a, b, c, d] and keep it if it is better than the current answer. Because the list is already sorted, this candidate has the smallest possible first pair for the current sum.

If the scan never finds a disjoint partner, every remaining pair must touch either a or b. Consequently the whole list is only O(n) long. In that situation I fall back to a complete double loop over the list, form every valid ordered quadruplet, and keep the best one.

When every sum has been examined I look at the answer array. If it still contains the sentinel values I return [-1, -1, -1, -1]. Otherwise I return the four indices I kept.

Edge-case handling is the same in every language:
- Arrays of size 4 that contain no matching pairs correctly return the failure indicator.
- Arrays in which many pairs share the same sum are handled by the first-pair scan, so the quadratic fallback is rarely used.
- Duplicate values are allowed; only the indices must be distinct.

## Examples

**Example 1**  
Input: arr = [3, 4, 7, 1, 2, 9, 8]  
Output: [0, 2, 3, 5]  

Trace:  
- Pair (0, 2) gives sum 10.  
- Pair (3, 5) also gives sum 10.  
- The four indices 0, 2, 3, 5 are distinct and form the lexicographically smallest valid quadruplet.

**Example 2**  
Input: arr = [1, 2, 3, 3]  
Output: [-1, -1, -1, -1]  

Trace:  
- Possible pairs produce sums 3, 4, 5, 5, 6.  
- No two pairs share the same sum and use four distinct indices, so the algorithm correctly returns the failure indicator.

**Example 3**  
Input: arr = [5, 5, 5, 5]  
Output: [0, 1, 2, 3]  

Trace:  
- Every pair sums to 10.  
- The earliest pair is (0, 1).  
- The next pair that uses completely different indices is (2, 3).  
- The resulting quadruplet is the lexicographically smallest possible answer.

## How to Use / Run Locally

**C++**  
1. Copy the C++ code into a file named `main.cpp`.  
2. Compile with `g++ -std=c++17 main.cpp -o main`.  
3. Run with `./main` and supply the array according to the input format required by your driver code.

**Java**  
1. Place the Java solution inside a class that matches the expected driver.  
2. Compile with `javac Solution.java`.  
3. Run with `java Solution` (or the name of the class that contains the main method).

**JavaScript**  
1. Save the code in a file named `solution.js`.  
2. Run with Node.js: `node solution.js`.  
3. Adjust the driver code to read the array from standard input or from a hard-coded test case.

**Python3**  
1. Save the code in a file named `solution.py`.  
2. Run with `python3 solution.py`.  
3. The driver usually reads the size of the array followed by the array elements; feed the input accordingly.

In each language make sure the function signature matches the one expected by the online judge or by your local test harness.

## Notes & Optimizations

- The algorithm already meets the expected O(n²) time and space bounds.  
- When all elements are equal the first-pair scan finds a valid partner almost immediately, so the quadratic fallback is never triggered.  
- If the problem constraints were larger (n ≥ 5000) a more sophisticated data structure would be needed to avoid the rare O(n³) worst-case path; under the given limits the present solution is sufficient.  
- Returning the indices rather than the values guarantees correctness even when the array contains duplicate numbers.  
- The same map-of-pairs idea can be reused for many other “equal-sum pairs” problems that appear in coding interviews and competitive programming contests.

## Author
[Md Aarzoo Islam](https://www.instagram.com/codewithaarzoo.in/)
