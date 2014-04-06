import java.util.Scanner;

public class AddDriver {
    static int[] a, b,c;
    static int carry = 0;
    static int size = 20;
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int one = scan.nextInt();
        int two = scan.nextInt();
        a = new int[size];
        b = new int[size];
        c = new int[size + 1];
        for (int i = 0; i < size; i++) {
            a[i] = one % 2;
            b[i] = two % 2;
            one /= 2;
            two /= 2;
        }
        for (int i = 0; i < size; i++) {
            System.out.print(a[size - i - 1]);
        }
        System.out.println();
        for (int i = 0; i < size; i++) {
            System.out.print(b[size - i - 1]);
        }
        System.out.println();
        for (int i = 0; i < size; i++) {
            if ((a[i] + b[i] + carry) == 0) {
                c[i] = 0;
                carry = 0;
            } else if ((a[i] + b[i] + carry) == 1) {
                c[i] = 1;
                carry = 0;
            } else if ((a[i] + b[i] + carry) == 2) {
                c[i] = 0;
                carry = 1;
            } else {
                c[i] = 1;
                carry = 1;
            }
        }
        c[size] = carry;
        int ans = 0;
        for (int i = 0; i < size + 1; i++) {
            System.out.print(c[size - i]);
            ans += c[i] * Math.pow(2, i);
        }
        System.out.println();
        System.out.println(ans);
        scan.close();
    }
}
