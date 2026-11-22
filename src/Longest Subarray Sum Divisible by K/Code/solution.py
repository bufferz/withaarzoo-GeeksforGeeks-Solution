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