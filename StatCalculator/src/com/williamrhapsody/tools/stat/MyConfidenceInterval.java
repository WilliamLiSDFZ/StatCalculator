package com.williamrhapsody.tools.stat;

import com.williamrhapsody.data.StatResult;
import com.williamrhapsody.exceptions.WrongParameterException;
import com.williamrhapsody.interfaces.ConfidenceInterval;

/**
 * 计算置信区间。
 *
 * @author WilliamLi
 * @version 1.0
 * @date 2022/3/5 18:40
 */
public class MyConfidenceInterval implements ConfidenceInterval {

    private int accuracy = 5;

    public MyConfidenceInterval() {

    }

    public MyConfidenceInterval(int accuracy) {
        this();
        this.accuracy = accuracy;
    }

    /**
     * 计算单比例Z区间置信区间。
     *
     * @param x  成功数
     * @param N  实验数
     * @param CL 置信水平
     * @return 置信区间
     */
    public final StatResult one_prop_z_interval(final long x, final long N, final double CL) {

        if (CL < 0 || N <= 0)
            throw new WrongParameterException("错误：域错误");

        MyDistribution myDistribution = new MyDistribution();
        StatResult statResult = new StatResult("单比例z区间");

        double p_hat = ((double) x) / ((double) N);
        double critical_level = myDistribution.invNorm_Standard(CL + (1 - CL) / 2);
        double standard_error = Math.sqrt(p_hat * (1 - p_hat) / (double) N);
        double margin_of_error = critical_level * standard_error;

        String format = "%." + this.accuracy + "f";
        statResult.put("CLower", String.format(format, p_hat - margin_of_error));
        statResult.put("CUpper", String.format(format, p_hat + margin_of_error));
        statResult.put("p-hat", String.format(format, p_hat));
        statResult.put("ME", String.format(format, margin_of_error));
        statResult.put("n", String.format(format, (double) N));

        return statResult;
    }

    /**
     * 计算双比例Z区间置信区间。
     *
     * @param x1 成功数1
     * @param n1 样本总数1
     * @param x2 成功数2
     * @param n2 样本总数2
     * @param CL 置信水平
     * @return 置信区间
     */
    public final StatResult two_prop_z_interval(final long x1, final long n1, final long x2, final long n2, final double CL) {

        if (CL < 0 || n1 <= 0 || n2 < 0)
            throw new WrongParameterException("错误：域错误");

        MyDistribution myDistribution = new MyDistribution();
        StatResult statResult = new StatResult("双比例z区间");

        double p_hat1 = ((double) x1) / ((double) n1);
        double p_hat2 = ((double) x2) / ((double) n2);
        double critical_level = myDistribution.invNorm_Standard(CL + (1 - CL) / 2);
        double standard_error = Math.sqrt((p_hat1 * (1 - p_hat1) / n1) + (p_hat2 * (1 - p_hat2) / n2));
        double margin_of_error = critical_level * standard_error;

        String format = "%." + this.accuracy + "f";
        statResult.put("CLower", String.format(format, p_hat1 - p_hat2 - margin_of_error));
        statResult.put("CUpper", String.format(format, p_hat1 - p_hat2 + margin_of_error));
        statResult.put("p-hatDiff", String.format(format, p_hat1 - p_hat2));
        statResult.put("ME", "" + String.format(format, margin_of_error));
        statResult.put("p-hat1", String.format(format, p_hat1));
        statResult.put("p-hat2", String.format(format, p_hat2));
        statResult.put("n1", "" + String.format(format, (double) n1));
        statResult.put("n2", "" + String.format(format, (double) n2));

        return statResult;
    }

    /**
     * 计算单样本t区间置信区间。
     *
     * @param xBar 平均值
     * @param Sx   标准差
     * @param N    样本数
     * @param CL   置信水平
     * @return 置信区间
     */
    public final StatResult one_sample_t_interval(final long xBar, final double Sx, final long N, final double CL) {

        if (CL < 0 || N <= 0 || Sx < 0)
            throw new WrongParameterException("错误：域错误");

        MyDistribution myDistribution = new MyDistribution();
        StatResult statResult = new StatResult("单样本t区间");

        double SE = Sx / Math.sqrt(N);
        double criticalValue = myDistribution.invT(CL + (1 - CL) / 2, N - 1);
        double margin_of_error = criticalValue * SE;

        String format = "%." + this.accuracy + "f";
        statResult.put("CLower", String.format(format, xBar - margin_of_error));
        statResult.put("CUpper", String.format(format, xBar + margin_of_error));
        statResult.put("xBar", String.format(format, (double) xBar));
        statResult.put("ME", String.format(format, margin_of_error));
        statResult.put("df", String.format(format, (double) N - 1));
        statResult.put("Sx", String.format(format, Sx));
        statResult.put("n", String.format(format, (double) N));

        return statResult;
    }

    /**
     * 计算双样本t区间置信区间。
     *
     * @param x1Bar 第一个样本平均值
     * @param Sx1   第一个样本标准差
     * @param n1    第一个样本数量
     * @param x2Bar 第二个样本平均值
     * @param Sx2   第二个样本标准差
     * @param n2    第二个样本数量
     * @param CL    置信水平
     * @return 置信区间
     */
    public final StatResult two_sample_t_interval(final long x1Bar, final double Sx1, final long n1,
                                                  final long x2Bar, final double Sx2, final long n2,
                                                  final double CL) {

        if (CL < 0 || n1 <= 0 || n2 < 0 || Sx1 < 0 || Sx2 < 0)
            throw new WrongParameterException("错误：域错误");

        MyDistribution myDistribution = new MyDistribution();
        StatResult statResult = new StatResult("双样本t区间");

        double SE = Math.sqrt((Math.pow(Sx1, 2) / n1) + (Math.pow(Sx2, 2) / n2));
        double df = Math.pow(SE, 4) / ((Math.pow(Sx1 * Sx1 / n1, 2) / (n1 - 1)) + (Math.pow(Sx2 * Sx2 / n2, 2) / (n2 - 1)));
        double criticalValue = myDistribution.invT(CL + (1 - CL) / 2, df);
        double margin_of_error = criticalValue * SE;

        String format = "%." + this.accuracy + "f";
        statResult.put("CLower", String.format(format, (x1Bar - x2Bar) - margin_of_error));
        statResult.put("CUpper", String.format(format, (x1Bar - x2Bar) + margin_of_error));
        statResult.put("xBarDiff", String.format(format, (double)(x1Bar - x2Bar)));
        statResult.put("ME", String.format(format, margin_of_error));
        statResult.put("df", String.format(format, df));
        statResult.put("x1Bar", String.format(format, (double)x1Bar));
        statResult.put("x2bar", String.format(format, (double)x2Bar));
        statResult.put("Sx1", String.format(format, Sx1));
        statResult.put("Sx2", String.format(format, Sx2));
        statResult.put("n1", String.format(format, (double)n1));
        statResult.put("n2", String.format(format, (double)n2));

        return statResult;
    }
}
