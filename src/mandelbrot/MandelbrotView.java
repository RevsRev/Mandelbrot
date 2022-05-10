package mandelbrot;/*
Class to encapsulate all the points we want to iterate for the mandelbrot set
 */

import math.Complex;

import java.awt.*;
import java.util.Vector;

public class MandelbrotView
{
    private int resolution_height;
    private int resolution_width;

    private double realMin;
    private double realMax;
    private double imagMin;
    private double imagMax;

    private final Object syncObj = new Object();

    Vector<Vector<MandelbrotPoint>> points = new Vector<Vector<MandelbrotPoint>>();

    public MandelbrotView(int resolution_width, int resolution_height)
    {
        this.resolution_height = resolution_height;
        this.resolution_width = resolution_width;
    }

    public void setPoints(double realMin, double realMax, double imagMin, double imagMax)
    {
        this.realMin = realMin;
        this.realMax = realMax;
        this.imagMin = imagMin;
        this.imagMax = imagMax;
    }

    public void update()
    {
        synchronized (syncObj)
        {
            if (points.size() == 0)
            {
                initPoints();
                return;
            }

            updatePoints();
        }
    }

    public void paint(Graphics2D g, Dimension windowSize)
    {
        synchronized (syncObj)
        {
            double stepx = ((double)windowSize.width)/((double)resolution_width);
            double stepy = ((double)windowSize.height)/((double)resolution_height);

            for (int i=0; i<resolution_width; i++)
            {
                for (int j=0; j<resolution_height; j++)
                {
                    MandelbrotPoint mandelbrotPoint = points.get(i).get(j);
                    //get the colour from the point!
                    g.setColor(mandelbrotPoint.getColour());
                    //g.setColor(Color.blue);
                    g.fillOval((int)stepx *i, (int)stepy  *j, 2, 2);
//                int x = (int)stepx *i;
//                int y = (int)stepy*j;
//                g.drawLine(x,y,x,y);
                }
            }
        }
    }

    private void initPoints()
    {
        double width = realMax - realMin;
        double height = imagMax - imagMin;

        double widthStep = width/resolution_width;
        double heightStep = height/resolution_height;

        for (int i=0; i<resolution_width; i++)
        {
            Vector<MandelbrotPoint> column = new Vector<MandelbrotPoint>();
            points.add(column);
            for (int j=0; j<resolution_height; j++)
            {
                Complex z = new Complex(realMin + widthStep*i, imagMin + heightStep*j);
                MandelbrotPoint point = new MandelbrotPoint(z);
                point.iterate();
                column.add(point);
            }
        }
    }
    private void updatePoints()
    {
        double width = realMax - realMin;
        double height = imagMax - imagMin;

        double widthStep = width/resolution_width;
        double heightStep = height/resolution_height;

        for (int i=0; i<resolution_width; i++)
        {
            for (int j=0; j<resolution_height; j++)
            {
                Complex z = new Complex(realMin + widthStep*i, imagMin + heightStep*j);
                points.get(i).get(j).z  =z;
                points.get(i).get(j).iterate();
            }
        }
    }

    public void zoom(Dimension size, Point mouseLocationOnScrn, int scrollAmount)
    {
        //scroll amount is going to give a % change to the zoom. Can tweak as necessary later.
        double scaleFactor = Math.max(1d-(double)scrollAmount/100, 0.05);

        //first job is to convert the mouse loc on scrn to its corresponding cplx number
        double real = realMin + (realMax - realMin) * (double)mouseLocationOnScrn.x / (double)size.width;
        double imag = imagMin + (imagMax - imagMin) * (double)mouseLocationOnScrn.y / (double)size.height;

        //Now adjus the quantities appropriately
        realMin = realMin + (1 - scaleFactor) * (real - realMin);
        realMax = realMax - (1 - scaleFactor) * (realMax - real);
        imagMin = imagMin + (1 - scaleFactor) * (imag - imagMin);
        imagMax = imagMax - (1 - scaleFactor) * (imagMax - imag);

        update();
    }
}
