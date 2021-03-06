package org.bonn.se.ws17.uebung10_03;


import java.util.Scanner;

public class Matrix {
    private final double[][] matrix;

    public Matrix(int zeilen, int spalten) {
        matrix = new double[zeilen][spalten];
    }

    public Matrix(int n) {
        matrix = new double[n][n];
    }

    public Matrix(Matrix m) {
        matrix = new double[m.zeilen()][m.spalten()];
        for (int i = 0; i < m.zeilen(); i++) {
            for (int k = 0; k < m.spalten(); k++) {
                matrix[i][k] = m.get(i, k);
            }
        }

    }

    public static double zwischensumme(double[] zeile, Matrix spalte, int spaltennummer) {
        double ergebnis = 0;
        double[] spalteneu;
        spalteneu = new double[zeile.length];

        for (int k = 0; k < zeile.length; k++) {
            spalteneu[k] = spalte.get(k, spaltennummer);
        }

        for (int i = 0; i < zeile.length; i++) {
            ergebnis = ergebnis + zeile[i] * spalteneu[i];
        }
        return ergebnis;
    }

    public int zeilen() {
        return matrix.length;
    }

    public int spalten() {
        return matrix[0].length;
    }

    public double get(int z, int s) {
        return matrix[z][s];
    }

    public Matrix set(int z, int s, double e) {
        matrix[z][s] = e;
        return this;
    }

    public Matrix setAll(double e) {
        for (int i = 0; i < matrix.length; i++) {
            for (int k = 0; k < matrix[0].length; k++) {
                matrix[i][k] = e;
            }
        }
        return this;
    }

    public Matrix setZeile(int z, double[] a) {
        for (int k = 0; k < matrix[0].length; k++) {
            matrix[z][k] = a[k];
        }
        return this;
    }

    public Matrix setSpalte(int s, double[] a) {
        for (int k = 0; k < matrix.length; k++) {
            matrix[k][s] = a[k];
        }
        return this;
    }

    public boolean equals(Matrix m) {
        if (matrix.length != m.zeilen()) {
            return false;
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int k = 0; k < matrix[0].length; k++) {
                if (!Zahlen.equals(matrix[i][k], m.get(i, k))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String ausgabe = "";
        for (int i = 0; i < matrix.length; i++) {
            ausgabe += "(";
            for (int k = 0; k < matrix[0].length; k++) {
                ausgabe += " " + matrix[i][k];
            }
            ausgabe += " )\n";
        }

        return ausgabe;
    }

    /* Aufgabe 3 folgende Methoden:*/

    public Matrix eingabe(Scanner sc) {
        int z = 0;
        int s = 0;

        while (sc.hasNext()) {
            if (sc.next().equals("(")) {
                while (sc.hasNext()) {
                    if (sc.hasNextDouble()) {
                        matrix[z][s] = sc.nextDouble();
                        s++;
                    } else if (sc.next().equals(")")) {
                        z++;
                        s = 0;
                        break;
                    }
                }
            }

        }
        return this;
    }

    public Matrix trans() {
        // Erstelle neue Matrix mit vertauschten Zeeilen/Spalten
        Matrix x = new Matrix(matrix[0].length, matrix.length);
        // Algorythmus der die neue Matrix mit den Werten füllt.
        for (int i = 0; i < x.zeilen(); i++) {
            for (int j = 0; j < x.spalten(); j++) {
                x.set(i, j, matrix[j][i]);
            }
        }
        return x;
    }

    public Matrix add(Matrix m) {
        Matrix summe = new Matrix(m.zeilen(), m.spalten());
        double wert;
        for (int i = 0; i < summe.zeilen(); i++) {
            for (int j = 0; j < summe.spalten(); j++) {
                wert = matrix[i][j] + m.get(i, j);
                summe.set(i, j, wert);
            }
        }
        return summe;
    }

    public Matrix sub(Matrix m) {
        Matrix differenz = new Matrix(m.zeilen(), m.spalten());
        double wert;
        for (int i = 0; i < differenz.zeilen(); i++) {
            for (int j = 0; j < differenz.spalten(); j++) {
                wert = matrix[i][j] - m.get(i, j);
                differenz.set(i, j, wert);
            }
        }
        return differenz;
    }

    public Matrix mul(double x) {
        Matrix produkt = new Matrix(matrix.length, matrix[0].length);
        double wert;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                wert = matrix[i][j] * x;
                produkt.set(i, j, wert);
            }
        }
        return produkt;
    }

    public Matrix mul(Matrix m) {
        Matrix produkt = new Matrix(matrix.length, m.spalten());
        double wert;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < m.spalten(); j++) {
                produkt.set(i, j, Matrix.zwischensumme(matrix[i], m, j)); //Die Rechnung selber findet in einer Hilfsmethode statt
            }
        }

        return produkt;
    }
}