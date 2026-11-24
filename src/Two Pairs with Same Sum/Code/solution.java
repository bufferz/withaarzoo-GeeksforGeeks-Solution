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