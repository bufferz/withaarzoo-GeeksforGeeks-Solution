# Character at Position in Infinite String - GeeksforGeeks Solution

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

You are given a string `s` and an integer `pos`. An infinite string is built by starting with `s` and repeatedly applying this rule: in the i-th step, append exactly i dollar signs (`$`) and then append the reverse of the current string.

Your task is to find and return the character that sits at the 1-based position `pos` in this never-ending string.

This is a classic competitive programming problem that tests your ability to work with exponentially growing strings without actually building them.

## Constraints

- 1 ≤ s.length() ≤ 10^5
- 1 ≤ pos ≤ 10^9
- The string `s` contains only English letters and digits

## Intuition

The string grows extremely fast because every step roughly doubles its length. Building even a few stages would be impossible when `pos` can reach a billion.  

The key observation is that every new string is completely defined by the previous one. So instead of going forward, I can find the smallest stage whose length is at least `pos` and then walk backward, mapping the target position into the previous stage until I land inside the original string.

## Approach

1. Start with the length of the given string and keep applying the length formula  
   `new_length = 2 * old_length + step`  
   until the length becomes greater than or equal to `pos`. Count how many steps were needed.

2. Now walk backward from that final stage. At every stage the string looks like:  
   previous string + (exactly `step` dollar signs) + reverse of previous string.

3. Check which of the three parts the current position falls into:
   - Inside the previous string → simply continue with the same position on the smaller string.
   - Inside the dollar-sign block → return `'$'`.
   - Inside the reversed part → map the position back to the corresponding index of the previous string and continue.

4. When the step counter reaches zero, the position has been reduced to an index inside the original string, so return that character.

This gives an efficient O(log pos) solution that never builds the infinite string.

## Data Structures Used

No advanced data structures are required. Only a handful of integer variables are used to track the current length, the number of steps, and the mapped position. The original string is accessed only at the very end.

## Operations & Behavior Summary

- Compute successive lengths until the length covers the given position.
- Record the number of construction steps performed.
- While steps remain:
  - Calculate the length of the previous stage.
  - Decide whether the position lies in the previous part, the dollar block, or the reversed part.
  - Update the position and reduce the step counter accordingly.
- When no steps are left, read the character from the original string at the final mapped index.

## Complexity

| Type              | Complexity   | Explanation                                      |
|-------------------|--------------|--------------------------------------------------|
| Time Complexity   | O(log pos)   | Each step roughly doubles the length, so only a logarithmic number of stages are needed. |
| Space Complexity  | O(1)         | Only a constant number of integer variables are used. No extra arrays or strings are created. |

## Multi-language Solutions

### C++
```cpp
class Solution {
  public:
    char posInfinite(string &s, int pos) {
        long long p = pos;
        long long len = s.size();
        int steps = 0;
        while (len < p) {
            steps++;
            len = 2 * len + steps;
        }
        long long cur_len = len;
        int cur_step = steps;
        while (cur_step > 0) {
            long long prev_len = (cur_len - cur_step) / 2;
            if (p <= prev_len) {
                cur_len = prev_len;
                cur_step--;
            } else if (p <= prev_len + cur_step) {
                return '$';
            } else {
                long long offset = p - prev_len - cur_step;
                p = prev_len - offset + 1;
                cur_len = prev_len;
                cur_step--;
            }
        }
        return s[p - 1];
    }
};
```

### Java
```java
class Solution {
    public char posInfinite(String s, int pos) {
        long p = pos;
        long len = s.length();
        int steps = 0;
        while (len < p) {
            steps++;
            len = 2 * len + steps;
        }
        long cur_len = len;
        int cur_step = steps;
        while (cur_step > 0) {
            long prev_len = (cur_len - cur_step) / 2;
            if (p <= prev_len) {
                cur_len = prev_len;
                cur_step--;
            } else if (p <= prev_len + cur_step) {
                return '$';
            } else {
                long offset = p - prev_len - cur_step;
                p = prev_len - offset + 1;
                cur_len = prev_len;
                cur_step--;
            }
        }
        return s.charAt((int)(p - 1));
    }
}
```

### JavaScript
```javascript
/**
 * @param {string} s
 * @param {number} pos
 * @return {char}
 */

class Solution {
    posInfinite(s, pos) {
        let p = pos;
        let len = s.length;
        let steps = 0;
        while (len < p) {
            steps++;
            len = 2 * len + steps;
        }
        let cur_len = len;
        let cur_step = steps;
        while (cur_step > 0) {
            let prev_len = Math.floor((cur_len - cur_step) / 2);
            if (p <= prev_len) {
                cur_len = prev_len;
                cur_step--;
            } else if (p <= prev_len + cur_step) {
                return '$';
            } else {
                let offset = p - prev_len - cur_step;
                p = prev_len - offset + 1;
                cur_len = prev_len;
                cur_step--;
            }
        }
        return s[p - 1];
    }
}
```

### Python3
```python
class Solution:
    def posInfinite(self, s: str, pos: int) -> str:
        p = pos
        length = len(s)
        steps = 0
        while length < p:
            steps += 1
            length = 2 * length + steps
        cur_len = length
        cur_step = steps
        while cur_step > 0:
            prev_len = (cur_len - cur_step) // 2
            if p <= prev_len:
                cur_len = prev_len
                cur_step -= 1
            elif p <= prev_len + cur_step:
                return '$'
            else:
                offset = p - prev_len - cur_step
                p = prev_len - offset + 1
                cur_len = prev_len
                cur_step -= 1
        return s[p - 1]
```

## Step-by-step Detailed Explanation (C++, Java, JavaScript, Python3)

The core logic is identical across all four languages; only syntax differs.

First I convert the given `pos` into a 64-bit integer so that intermediate calculations never overflow. Then I start with the length of the input string and keep doubling it (plus the current step) until the length is large enough to contain the target position. This loop runs only about 30 times even for the maximum possible `pos`.

Once I know the final length and the number of steps, I begin the backward walk. At every stage I compute the previous length with the simple formula `(current_length - step) / 2`.  

- If the position is still smaller than or equal to the previous length, I just shrink the current length and decrease the step counter.  
- If the position falls inside the block of dollar signs, I immediately return `'$'`.  
- Otherwise the position is inside the reversed portion. I calculate the offset from the start of the reverse section and map it back to the corresponding index of the previous string. Then I continue with the new position and a reduced step count.

When the step counter finally reaches zero, the position now points inside the original string, so I return the character at that index (remembering that strings are 0-based).

Edge cases such as `pos` already lying inside the original string are handled automatically because the while-loop that finds the required steps never runs and the answer is taken directly from `s`.

## Examples

**Example 1**  
Input: `s = "12345"`, `pos = 3`  
Output: `3`  

The original string already covers position 3, so the answer is simply the third character.

**Example 2**  
Input: `s = "12345"`, `pos = 10`  
Output: `2`  

After the first construction step the string becomes `12345$54321`. Position 10 lies inside the reversed part and maps back to the character `'2'`.

**Example 3**  
Input: `s = "abc"`, `pos = 7`  
Output: `a`  

After one step the string is `abc$cba`. Position 7 maps to the first character of the original string.

## How to Use / Run Locally

**C++**  
```bash
g++ -std=c++17 solution.cpp -o solution
./solution
```

**Java**  
```bash
javac Solution.java
java Solution
```

**JavaScript**  
```bash
node solution.js
```

**Python3**  
```bash
python3 solution.py
```

Replace the driver code with your own test cases that call the `posInfinite` method.

## Notes & Optimizations

- Because the length roughly doubles at every step, the number of stages never exceeds 40 even for the largest allowed `pos`. This keeps the solution well inside time limits.
- Using 64-bit integers is essential; 32-bit integers would overflow while computing successive lengths.
- An alternative approach of simulating the construction forward while storing only the lengths also works and has the same complexity.
- The solution correctly handles the case when `pos` is already covered by the original string without any special if-statements.

## Author
[Md Aarzoo Islam](https://www.instagram.com/codewithaarzoo.in/)
