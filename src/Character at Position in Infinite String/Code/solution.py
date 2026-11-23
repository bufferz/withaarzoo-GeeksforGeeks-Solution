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