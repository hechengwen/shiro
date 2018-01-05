package shiro.hcw.utils;

/**
 * Copyright (C), 2017，jumore Tec.
 * Author: hechengwen
 * Version:
 * Date: 2017/12/26 10:35
 * Description:
 * Others:
 */
public class TestAnd {
    public static void main(String[] args) {
        int a = 129;
        int b = 128;
        System.out.println("a 和b 与的结果是：" + (a & b));
        System.out.println("a 和b 或的结果是：" + (a | b));
        int c = -13;
        System.out.println("c 非的结果是：" + (~c));

        int d = 15;
        int e = 2;
        System.out.println("d 与 e 异或的结果是：" + (d ^ e));

        int f = 96;
        int g = 3;
        System.out.println("f 与 g 左位移运算的结果是：" + (f<<g));
        System.out.println("f 与 g 右位移运算的结果是：" + (f>>g));
    }
}
