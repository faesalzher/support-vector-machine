/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package svm;

/**
 *
 * @author Aditya Pratama
 */
public class hitung {

    public double b(double kernel[][], double y[][], double alpha[], double dataInput[], double dataNormalisasi[][]) {

        double vectorPositif = 0;
        double vectorNegatif = 0;

        for (int i = 0; i < kernel.length; i++) {
            vectorPositif += kernel[0][i] * alpha[i] * y[0][i];
            vectorNegatif += kernel[3][i] * alpha[i] * y[0][i];
        }
        System.out.print("\n-----------------------------------------------------");

        System.out.print("\n\nVector Positif : " + vectorPositif);
        System.out.print("V\nVector Negatif" + vectorNegatif);
        double b = -0.5 * (vectorPositif + vectorNegatif);
        System.out.println("\n\nb : " + b);

        double kernelUji[] = new double[5];
        for (int i = 0; i < kernel.length; i++) {
            kernelUji[i] = Math.pow((dataInput[0] * dataNormalisasi[i][0] + dataInput[1] * dataNormalisasi[i][1]) + 1, 2) * alpha[i] * y[0][i];
        }
        System.out.println("\nKernel uji*alpha*y : ");
        for (int i = 0; i < kernel.length; i++) {
            System.out.printf("%30f\n", kernelUji[i]);
        }

        double totalKernelUji = 0;
        for (int i = 0; i < kernel.length; i++) {
            totalKernelUji += kernelUji[i];
        }
        System.out.println("                     -------------------+");
        System.out.println("Total :              " + totalKernelUji);
        double a = (Math.signum(b + totalKernelUji));
        System.out.println("f(x) : " + a);
        return a;
    }
}
