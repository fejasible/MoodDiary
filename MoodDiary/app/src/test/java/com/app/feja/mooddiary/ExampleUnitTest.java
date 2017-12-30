package com.app.feja.mooddiary;

import android.util.Pair;
import android.util.SparseArray;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_001() throws Exception {
        assertEquals(1, 1 + 1 >> 1);
    }


    @Test
    public void test_002() throws Exception {
        String s = "";
        List<Polynomial> polynomials = calc(new int[]{37, 23, 59, 271}, 733);
        for(Polynomial polynomial: polynomials){
            System.out.println(polynomial);
        }
    }


    private List<Polynomial> calc(int[] a, int sum) {
        int[] b = new int[a.length];
        List<Polynomial> polynomials = new ArrayList<>();
        Arrays.sort(a);
        int limit = sum / a[0];
        for(int i=0; i<limit; i++){
            for(int j=0; j<limit; j++){
                for(int k=0; k<limit; k++){
                    for(int l=0; l<limit; l++){
                        if(a[0]*i + a[1]*j + a[2]*k + a[3]*l == sum){
                            b[0] = i;
                            b[1] = j;
                            b[2] = k;
                            b[3] = l;
                            boolean contain = false;
                            for(Polynomial polynomial: polynomials){
                                if(Arrays.equals(polynomial.getB(), b)){
                                    contain = true;
                                }
                            }
                            if(!contain){
                                polynomials.add(new Polynomial(a, b.clone()));
                            }
                        }
                    }
                }
            }
        }
        return polynomials;
    }

    private class Polynomial{
        private int[] a;
        private int[] b;

        public int[] getA() {
            return a;
        }

        public int[] getB() {
            return b;
        }

        public Polynomial(int[] a, int[] b) {
            if(a.length != b.length || a.length == 0)
                throw new IllegalArgumentException("a and b length must be equal and bigger than 0");
            this.a = a;
            this.b = b;
        }

        public int sum(){
            int sum = 0;
            for(int i=0; i< a.length; i++){
                sum += a[i] * b[i];
            }
            return sum;
        }

        @Override
        public String toString() {
            String s = "";
            for(int i=0; i< a.length; i++){
                s += a[i] + "*(" + b[i] + ")" + (i != a.length - 1 ? " + " : " = ");
            }
            return s + sum();
        }
    }

}