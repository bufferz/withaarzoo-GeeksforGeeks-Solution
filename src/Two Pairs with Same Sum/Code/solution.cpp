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