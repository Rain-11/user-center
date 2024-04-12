package com.crazy.rain.usercenter.utils;

import java.util.List;
import java.util.Objects;

/**
 * @ClassName: AlgorithmUtils
 * @Description: 算法工具类
 * @author: CrazyRain
 * @date: 2024/4/9 下午6:48
 */
public class AlgorithmUtils {

    public static int minDistance(List<String> word1, List<String> word2) {
        int n = word1.size();
        int m = word2.size();

        if (n * m == 0)
            return n + m;

        int[][] d = new int[n + 1][m + 1];
        for (int i = 0; i < n + 1; i++) {
            d[i][0] = i;
        }

        for (int j = 0; j < m + 1; j++) {
            d[0][j] = j;
        }

        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < m + 1; j++) {
                int left = d[i - 1][j] + 1;
                int down = d[i][j - 1] + 1;
                int leftDown = d[i - 1][j - 1];
                if (!Objects.equals(word1.get(i - 1), word2.get(j - 1))) {
                    leftDown += 1;
                }
                d[i][j] = Math.min(left, Math.min(down, leftDown));
            }
        }
        return d[n][m];
    }

}
