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