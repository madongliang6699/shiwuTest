package myAOPtest.D_测试直接使用spring的aop代理机制;



public class PersonDaoImpl implements PersonDao{

    public void savePerson() {
        System.out.println("save person");
    }

    public void updatePerson() {
        System.out.println("udate person");
    }

    public void delPerson() {
        System.out.println("del person");
    }
}
