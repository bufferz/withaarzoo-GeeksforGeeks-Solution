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