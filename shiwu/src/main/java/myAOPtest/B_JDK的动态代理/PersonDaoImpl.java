package myAOPtest.B_JDK的动态代理;


public class PersonDaoImpl implements PersonDao {/**CGLIB的动态代理不需要接口，jdk的动态代理必须接口,这应该和设计方式有关*/

    @Override
    public void savePerson() {
        System.out.println("save person");
    }

    @Override
    public void updatePerson() {
        System.out.println("udate person");
    }

    @Override
    public void delPerson() {
        System.out.println("del person");
    }
}
