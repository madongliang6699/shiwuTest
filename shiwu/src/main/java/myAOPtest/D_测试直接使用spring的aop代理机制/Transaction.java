package myAOPtest.D_测试直接使用spring的aop代理机制;

public class Transaction {

    void beginTransaction(){
        System.out.println("begin Transaction");
    }

    void commit(){
        System.out.println("commit");
    }
}