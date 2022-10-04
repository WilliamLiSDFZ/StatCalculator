package com.williamrhapsody.tools.stat;

import com.liyuze.JIntegration;
import com.williamrhapsody.exceptions.WrongParameterException;
import com.williamrhapsody.tools.Constants;
import com.williamrhapsody.tools.math.ExtraMath;
import org.apache.commons.math3.distribution.TDistribution;

/**
 * 此类用于处理所有关于统计学分布的方法。
 *
 * @author WilliamLi
 * @version 1.0
 * @date 2021/12/15 14:52
 */
public class Distribution {

    public Distribution() {
    }

    /**
     * 此方法用于计算标准正态分布下的Cdf。
     *
     * @param low  下界
     * @param high 上界
     * @return 上下界之间面积
     */
    public final double normCdf_Standard(final double low, final double high) {
        final double coefficient = 0.398942280401;
        JIntegration jIntegration = new JIntegration() {
            @Override
            public double f(double v) {
                double z_score = v;
                double z_scoreSquire = Math.pow(z_score, 2);
                return coefficient * Math.pow(Math.E, -z_scoreSquire / 2);
            }
        };
        return jIntegration.process(low, high, 1000000);
    }

    /**
     * 此方法用于计算所有正态分布下的Cdf。首先将分布转换成标准正态分布，完后进行计算。
     *
     * @param low  下界
     * @param high 上界
     * @param mean 均值
     * @param sd   标准差
     * @return 上下界之间面积
     */
    public final double normCdf(final double low, final double high, final double mean, final double sd) {
        if (sd <= 0) {
            throw new WrongParameterException("standard deviation out of bounds.");
        }
        final double low_z_score = (low - mean) / sd;
        final double high_z_score = (high - mean) / sd;
        return this.normCdf_Standard(low_z_score, high_z_score);
    }

    /**
     * 此方法用于计算标准T分布下的Cdf。注意，当计算从负无穷到上限的Cdf时，请使用重载方法。
     *
     * @param low  下限
     * @param high 上限
     * @param df   自由度
     * @return 上下界之间面积
     */
    public final double tCdf(final double low, final double high, final long df) {
        TDistribution tDistribution = new TDistribution(df);
        double p1 = tDistribution.cumulativeProbability(low);
        double p2 = tDistribution.cumulativeProbability(high);
        return p2 - p1;
    }

    /**
     * 此方法用于计算标准T分布下的Cdf。当计算从负无穷到上限时，请使用此方法。
     *
     * @param high 上限
     * @param df   自由度
     * @return 负无穷到上界之间的面积
     */
    public final double tCdf(final double high, final long df) {
        TDistribution tDistribution = new TDistribution(df);
        return tDistribution.cumulativeProbability(high);
    }

    /**
     * 此方法用于计算标准正态分布下的反向正态分布。
     *
     * @param area 密度曲线下的面积
     * @return 对应Z—score
     */
    public final double invNorm_Standard(final double area) {
        return this.invNorm(area, 0, 1);
    }

    /**
     * 此方法用于计算所有正态分布下的反向正态分布。
     *
     * @param area 密度曲线下的面积
     * @param mean 均值
     * @param sd   标准差
     * @return 对应X值
     */
    public final double invNorm(final double area, final double mean, final double sd) {
        if (area <= 0) {
            throw new WrongParameterException("area can not be equal or lower than 0.");
        }
        if (sd <= 0) {
            throw new WrongParameterException("standard deviation out of bounds.");
        }
        double low = -Constants.INFINITY;
        double high = Constants.INFINITY;
        double mid;
        while ((low + 0.000001) < high) {
            mid = (high + low) / 2;
            double currentArea = normCdf(-Constants.INFINITY, mid, mean, sd);
            if ((currentArea - area) > 0.000001) {
                high = mid;
            } else {
                low = mid;
            }
        }
        return low + 0.0000005;
    }

    /**
     * 此方法用于计算标准T分布下的反向T。
     *
     * @param area 密度曲线下的面积
     * @param df   自由度
     * @return T值
     */
    public final double invT(final double area, final double df) {
        TDistribution tDistribution = new TDistribution(df);
        return tDistribution.inverseCumulativeProbability(area);
    }

    /**
     * 此方法用于计算二项式Pdf。
     *
     * @param n 实验数
     * @param p 成功率
     * @param x 成功数
     * @return 二项式Pdf结果
     * @see ExtraMath
     */
    public final double binomPdf(final long n, final double p, final long x) {
        double result = ExtraMath.nCr(n, x) * Math.pow(p, x) * Math.pow((1 - p), (n - x));
        return result;
    }

    /**
     * 此方法用于计算所有可能成功数的二项式Pdf结果数组。
     *
     * @param n 实验数
     * @param p 成功率
     * @return 二项式Pdf结果数组
     */
    public final double[] binomPdf(final int n, final double p) {
        double[] result = new double[n + 1];
        for (int i = 0; i <= n; i++) {
            result[i] = binomPdf(n, p, i);
        }
        return result;
    }

    /**
     * 此方法用于计算几何Pdf。
     *
     * @param p 成功概率
     * @param x 第X次成功
     * @return 第X次成功概率
     */
    public final double geomPdf(final double p, final long x) {
        if (x < 0)
            throw new WrongParameterException("X can not be negative.");
        if (x == 0)
            return 0;
        return Math.pow((1 - p), (x - 1)) * p;
    }

    /**
     * 此方法用于计算几何Cdf。
     *
     * @param p    成功概率
     * @param low  下限
     * @param high 上限
     * @return 从第low次到第high次成功的可能性的和
     */
    public final double geomCdf(final double p, final long low, final long high) {
        if (low < 0 || high < 0)
            throw new WrongParameterException();
        double result = 0;
        for (long i = low; i <= high; i++) {
            result += geomPdf(p, i);
        }
        return result;
    }
}
