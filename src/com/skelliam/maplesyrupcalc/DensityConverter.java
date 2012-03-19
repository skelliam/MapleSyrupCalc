package com.skelliam.maplesyrupcalc;

import optimization.Fmin;
import optimization.Fmin_methods;
import optimization.Fzero;
import optimization.Fzero_methods;

public class DensityConverter implements Fzero_methods {
    
    private double FMIN_OFFSET = 0;
    
    public double getBrixFromSG(double sgval) {
        double brixval = 0.0;
        //Formula brix = -160.63sg^2 + 573.98sg - 413.26
        if (sgval > 1.0)
            brixval = (-160.63 * Math.pow(sgval, 2)) + (573.98 * sgval) - 413.26;
        return brixval;
    }
    
    public double getSGFromBrix(double brixval) {
        int[] array;
        double[] b, c;
        this.FMIN_OFFSET = brixval;  /* used for optimization */
        Fzero.fzero(this, b, c, 1.02, 0.001, 0.001, array);
    }

    @Override
    public double f_to_zero(double arg0) {
        return getBrixFromSG(arg0)-this.FMIN_OFFSET;
    }     
       
}
