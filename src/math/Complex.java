package math;

import java.lang.Math;

public class Complex
{
    private double real;
    private double imag;

    private double r;
    private double theta;

    public Complex(double real, double imag)
    {
        this.real  = real;
        this.imag = imag;
        calcRTheta();
    }
    public Complex(Complex cplx)
    {
        this.real = cplx.real;
        this.imag = cplx.imag;
        calcRealImag();
    }

    public double abs()
    {
        return r;
    }

    public void pow(double pow)
    {
        r = Math.pow(r, pow);
        theta = theta*pow;
        normalizeTheta();
        calcRealImag();
    }

    private void calcRTheta()
    {
        r = Math.sqrt(real * real + imag * imag);

        //todo - proper epsilon value!
        if (Math.abs(imag) < 0.0001)
        {
            if (imag > 0)
                theta = 0;
            else
                theta = 2*Math.PI;
            return;
        }

        theta = Math.atan(imag/real);

        //we want an angle between 0 and 2pi
        if (theta < 0)
            theta += Math.PI;
        if (imag < 0)
            theta += Math.PI;
    }
    private void calcRealImag()
    {
        real = r*Math.cos(theta);
        imag = r*Math.sin(theta);
    }

    //Addition is easier in real + i*imag form
    public void add(Complex cplx)
    {
        real += cplx.real;
        imag += cplx.imag;

        calcRTheta();
    }
    public void subtract(Complex cplx)
    {
        real -= cplx.real;
        imag -= cplx.imag;

        calcRTheta();
    }

    //multiplication/division is easier in e^i*theta form
    public void multiply(Complex cplx)
    {
        r *= cplx.r;
        theta += cplx.theta;
        normalizeTheta();

        calcRealImag();
    }
    public void divide(Complex cplx)
    {
        r /= cplx.r;
        theta -= cplx.theta;
        normalizeTheta();

        calcRealImag();
    }

    private void normalizeTheta()
    {
        while (theta < 0)
        {
            theta += 2*Math.PI;
        }

        while (theta > 2* Math.PI)
        {
            theta -= 2*Math.PI;
        }
    }
}
