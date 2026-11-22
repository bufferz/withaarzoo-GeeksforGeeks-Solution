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