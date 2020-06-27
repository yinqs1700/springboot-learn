package com.yinqs.guava.basicutils;


import com.google.common.collect.ComparisonChain;

/**
 * 简化compareTo方法
 * @author yinqs
 */
class Person implements Comparable<Person> {
    private String lastName;
    private String firstName;
    private int zipCode;

//    @Override
//    public int compareTo(Person other) {
//        int cmp = lastName.compareTo(other.lastName);
//        if (cmp != 0) {
//            return cmp;
//        }
//        cmp = firstName.compareTo(other.firstName);
//        if (cmp != 0) {
//            return cmp;
//        }
//        return Integer.compare(zipCode, other.zipCode);
//    }

    @Override
    public int compareTo(Person other) {
        return ComparisonChain.start()
                .compare(this.lastName,other.lastName)
                .compare(this.firstName,other.firstName)
                .compare(this.zipCode,other.zipCode)
                .result();
    }


}