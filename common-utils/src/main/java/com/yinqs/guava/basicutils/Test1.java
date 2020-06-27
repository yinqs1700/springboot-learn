package com.yinqs.guava.basicutils;

import com.google.common.base.Preconditions;

import java.util.Optional;

/**
 * @author yinqs
 */
public class Test1 {

    public static void main(String[] args) {


    }

    /**
     * Preconditions.checkArgument(boolean,"message")用于检查传递给方法的参数是
     * Preconditions.checkElementIndex(index, size) 检查index在是否有效
     * Preconditions.checkNotNull(i) 检查value是否为空
     * 否合法
     */
    public static void checkArgument(){
        int i = 12;
        int j = 13;
        Preconditions.checkArgument(i == j,"i == j");
        int index = 0;
        int size = 21;
        Preconditions.checkElementIndex(index, size);

        //
        Integer integer = Preconditions.checkNotNull(i);
    }
}
