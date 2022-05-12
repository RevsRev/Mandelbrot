package mandelbrot;

import ui.ApplicationFrame;

public class Main
{
    /*
    Our entry point to the program
     */
    public static void main(String[] args)
    {
        ApplicationFrame frame = new ApplicationFrame(new MandelbrotView(3000, 1600));
        //frame.pack();
        frame.setVisible(true);
        //System.exit(0);
    }
}
