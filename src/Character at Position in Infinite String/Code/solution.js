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