package myAOPtest.A_静态代理;

public class Transaction {

    void beginTransaction(){
        System.out.println("begin Transaction");
    }

    void commit(){
        System.out.println("commit");
    }
}