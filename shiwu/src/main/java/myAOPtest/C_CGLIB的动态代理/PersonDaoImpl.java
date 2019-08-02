package myAOPtest.C_CGLIB的动态代理;



public class PersonDaoImpl { /**CGLIB的动态代理不需要接口，jdk的动态代理必须接口*/

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
