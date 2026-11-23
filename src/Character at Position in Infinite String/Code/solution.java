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