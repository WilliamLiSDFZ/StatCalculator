package com.williamrhapsody.interfaces;

/**
 * Interface for Distribution class.
 *
 * @author WilliamLi
 * @version 1.0
 * @date 2022/10/4 15:14
 */
public interface Distribution {

    double normCdf_Standard(final double low, final double high);

    double normCdf(final double low, final double high, final double mean, final double sd);

    double tCdf(final double low, final double high, final long df);

    double tCdf(final double high, final long df);

    double invNorm_Standard(final double area);

    double invNorm(final double area, final double mean, final double sd);

    double invT(final double area, final double df);

    double binomPdf(final long n, final double p, final long x);

    double[] binomPdf(final int n, final double p);

    double geomPdf(final double p, final long x);

    double geomCdf(final double p, final long low, final long high);

}
