package com.williamrhapsody.interfaces;

import com.williamrhapsody.data.StatResult;

/**
 * Interface for ConfidenceInterval class.
 *
 * @author WilliamLi
 * @version 1.0
 * @date 2022/10/4 15:20
 */
public interface ConfidenceInterval {

    StatResult one_prop_z_interval(final long x, final long N, final double CL);

    StatResult two_prop_z_interval(final long x1, final long n1, final long x2, final long n2,
                                   final double CL);

    StatResult one_sample_t_interval(final long xBar, final double Sx, final long N, final double CL);

    StatResult two_sample_t_interval(final long x1Bar, final double Sx1, final long n1,
                                     final long x2Bar, final double Sx2, final long n2,
                                     final double CL);

}
