package com.ias.common.utils;

import groovy.lang.GroovyClassLoader;
import groovy.lang.Script;  
  
public class GroovyTest {
    @SuppressWarnings({ "unchecked", "resource" })
    public static void main(String args[]) {
        StringBuffer sb = new StringBuffer();
        sb.append("package junit.test;");
        sb.append("public class Test1{");
        sb.append("  public static void main(String[] args){");  
        sb.append("    System.out.println(\"ok.\");");  
        sb.append("  }");  
        sb.append("}");
        try {
            GroovyClassLoader loader = new GroovyClassLoader();
            Class<Script> fileCreator = loader.parseClass(sb.toString());
            System.out.println(fileCreator.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
