package com.williamrhapsody.tools.math;

/**
 * 此类用于做数学运算。
 *
 * @author WilliamLi
 * @version 1.0
 * @date 2021/12/16 11:03
 */
public class ExtraMath {

    /**
     * 计算阶乘。
     *
     * @param num 被阶乘数
     * @return 结成结果
     */
    public static final long factorial(final long num) {
        if (num == 0) {
            return 0;
        }
        if (num == 1) {
            return 1;
        }
        return num * factorial(num - 1);
    }

    /**
     * 计算排列组合中的组合。
     *
     * @param m 总数
     * @param n 排列个数
     * @return 组合种类个数
     */
    public static final long nCr(final long m, final long n) {
        if (n == 0 || m == n)
            return 1;
        return (ExtraMath.factorial(m) / (ExtraMath.factorial(n) * ExtraMath.factorial(m - n)));
    }

}
