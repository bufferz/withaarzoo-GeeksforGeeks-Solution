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