package mandelbrot;

import math.Complex;

import java.awt.*;

public class MandelbrotPoint
{
    public static int NUMBER_OF_ITERATIONS = 200;
    public Complex z;
    int iterationDepth = 0;
    public MandelbrotPoint(Complex z)
    {
        this.z = z;
    }

    public void iterate()
    {
        //our complex number to iterate, starting from 0
        Complex zit = new Complex(0,0);
        iterationDepth = 0;
        for (int i=0; i<NUMBER_OF_ITERATIONS; i++)
        {
            iterationDepth += 1;

            zit.pow(2);
            zit.add(z);

            //at this point, we know that the sequence will blow up
            if (zit.abs() > 2)
            {
                break;
            }
        }
    }

    public Color getColour()
    {
        float color = (float)(iterationDepth)/((float)NUMBER_OF_ITERATIONS*1.5f);
        return new Color(color, 0, 0.2f + color,1);
    }
}
