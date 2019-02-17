/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package svm;

import java.util.Scanner;

/**
 *
 * @author Aditya Pratama
 */
public class Svm {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner input = new Scanner(System.in);
        int lambda = 3;
        double gama = 0.005;
        int c = 5;
        System.out.println("lambda : " + lambda);
        System.out.println("gama : " + gama);
        System.out.println("C : " + c);

        double data[][] = {
            {60, 165, 1},
            {70, 160, 1},
            {80, 165, 1},
            {100, 155, -1},
            {40, 175, -1}
        };
        String status[] = {
            "normal",
            "normal",
            "normal",
            "tidak",
            "tidak"
        };

        //data
        System.out.println("Data :");
        for (int i = 0; i < data.length; i++) {
            System.out.printf("%.0f%12.0f%12.0f%12s\n", data[i][0], data[i][1], data[i][2], status[i]);
        }
        System.out.println("");

        //data uji
        double dataUji[] = new double[2];
        double inputNormalisasi[] = new double[2];
        System.out.print("Berat : ");
        dataUji[0] = input.nextDouble();
        System.out.print("Tinggi: ");
        dataUji[1] = input.nextDouble();

        //normalisasi
        System.out.println("Data Normalisasi :");
        double dataNormalisasi[][] = new double[data.length][data[0].length];
        normalisasi(data, dataNormalisasi, dataUji, inputNormalisasi);
        for (int i = 0; i < data.length; i++) {
            System.out.printf("%10.6f%10.6f\n", dataNormalisasi[i][0], dataNormalisasi[i][1]);
        }
        System.out.println("");
        //kernel
        System.out.println("K(xi,xj)");
        double kernel[][] = new double[data.length][data.length];
        for (int i = 0; i < data.length; i++) {
            kernel[i][0] = Math.pow(dataNormalisasi[i][0] * dataNormalisasi[0][0] + dataNormalisasi[i][1] * dataNormalisasi[0][1] + 1, 2);
            kernel[i][1] = Math.pow(dataNormalisasi[i][0] * dataNormalisasi[1][0] + dataNormalisasi[i][1] * dataNormalisasi[1][1] + 1, 2);
            kernel[i][2] = Math.pow(dataNormalisasi[i][0] * dataNormalisasi[2][0] + dataNormalisasi[i][1] * dataNormalisasi[2][1] + 1, 2);
            kernel[i][3] = Math.pow(dataNormalisasi[i][0] * dataNormalisasi[3][0] + dataNormalisasi[i][1] * dataNormalisasi[3][1] + 1, 2);
            kernel[i][4] = Math.pow(dataNormalisasi[i][0] * dataNormalisasi[4][0] + dataNormalisasi[i][1] * dataNormalisasi[4][1] + 1, 2);
        }
        for (int i = 0; i < kernel.length; i++) {
            for (int j = 0; j < kernel[i].length; j++) {
                System.out.printf("%10.6f", kernel[i][j]);
            }
            System.out.println("");
        }

        //kernel+lambdaKuadrat
        System.out.println("\nK+lamdaKuadrat");
        double kLambdaKuadrat[][] = new double[kernel.length][kernel.length];
        for (int i = 0; i < kernel.length; i++) {
            for (int j = 0; j < kernel[i].length; j++) {
                kLambdaKuadrat[i][j] = kernel[i][j] + Math.pow(lambda, 2);
            }
        }
        for (int i = 0; i < kernel.length; i++) {
            for (int j = 0; j < kernel[i].length; j++) {
                System.out.printf("%10.6f", kLambdaKuadrat[i][j]);
            }
            System.out.println("");
        }

        //yiyj
        System.out.println("\nYiYj");
        double y[][] = new double[kernel.length][kernel.length];
        for (int i = 0; i < kernel.length; i++) {
            y[i][0] = data[0][2] * data[i][2];
            y[i][1] = data[1][2] * data[i][2];
            y[i][2] = data[2][2] * data[i][2];
            y[i][3] = data[3][2] * data[i][2];
            y[i][4] = data[4][2] * data[i][2];
        }
        for (int i = 0; i < kernel.length; i++) {
            for (int j = 0; j < kernel[i].length; j++) {
                System.out.printf("%10.0f", y[i][j]);
            }
            System.out.println("");
        }

        //Dij
        double D[][] = new double[kernel.length][kernel.length];
        System.out.println("\nDij");
        for (int i = 0; i < kernel.length; i++) {
            for (int j = 0; j < kernel[i].length; j++) {
                D[i][j] = kLambdaKuadrat[i][j] * y[i][j];
            }
        }
        for (int i = 0; i < kernel.length; i++) {
            for (int j = 0; j < kernel[i].length; j++) {
                System.out.printf("%10.6f", D[i][j]);
            }
            System.out.println("");
        }
        System.out.print("-----------------------------------------------------");

        //mencari alpha
        double E[] = new double[kernel.length];
        double delta[] = new double[kernel.length];
        double alpha[] = new double[data.length];
        System.out.print("\nAlpha : ");
        for (int i = 0; i < kernel.length; i++) {
            System.out.printf("%14f", alpha[i]);
        }

        for (int j = 0; j < 7; j++) {
            System.out.print("\n\nEi : ");
            for (int i = 0; i < kernel.length; i++) {
                E[0] += (D[0][i] * alpha[i]);
                E[1] += (D[1][i] * alpha[i]);
                E[2] += (D[2][i] * alpha[i]);
                E[3] += (D[3][i] * alpha[i]);
                E[4] += (D[4][i] * alpha[i]);
            }
            for (int i = 0; i < kernel.length; i++) {
                System.out.printf("%14f", E[i]);
            }

            System.out.print("\nDelta i : ");
            for (int i = 0; i < kernel.length; i++) {
                delta[i] = Double.min(Double.max(gama * (1 - E[i]), -alpha[i]), c - alpha[i]);
                System.out.printf("%12f", delta[i]);
            }

            System.out.print("\nAlpha : ");
            for (int i = 0; i < kernel.length; i++) {
                alpha[i] = alpha[i] + delta[i];
            }
            for (int i = 0; i < kernel.length; i++) {
                System.out.printf("%14f", alpha[i]);
            }
        }

        //perhitungan b
        hitung cari = new hitung();
        if (cari.b(kernel, y, alpha, inputNormalisasi, dataNormalisasi) == 1.0) {
            System.out.println("Status : Normal");
        } else {
            System.out.println("Status : Tidak");
        }

    }

//normalisasi
    static void normalisasi(double data[][], double dataNormalisasi[][], double input[], double inputNormalisasi[]) {
        double dataBerat[] = new double[data.length];
        double dataTinggi[] = new double[data.length];
        double inputDataBerat[] = new double[data.length];
        double inputDataTinggi[] = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            dataBerat[i] = data[i][0];
            dataTinggi[i] = data[i][1];
            inputDataBerat[i] = data[i][0];
            inputDataTinggi[i] = data[i][1];
        }

        for (int i = 0; i < dataNormalisasi.length; i++) {
            dataNormalisasi[i][0] = (data[i][0] - min(dataBerat)) / (max(dataBerat) - min(dataBerat));
            dataNormalisasi[i][1] = (data[i][1] - min(dataTinggi)) / (max(dataTinggi) - min(dataTinggi));
            inputNormalisasi[0] = (input[0] - min(inputDataBerat)) / (max(inputDataBerat) - min(inputDataBerat));
            inputNormalisasi[1] = (input[1] - min(inputDataTinggi)) / (max(inputDataTinggi) - min(inputDataTinggi));
        }
    }

    static double max(double data[]) {
        double max = data[0];
        for (int i = 0; i < data.length; i++) {
            if (data[i] > max) {
                max = data[i];
            }
        }
        return max;
    }

    static double min(double data[]) {
        double min = data[0];
        for (int i = 0; i < data.length; i++) {
            if (data[i] < min) {
                min = data[i];
            }
        }
        return min;
    }
}
