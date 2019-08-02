package myAOPtest.C_CGLIB的动态代理;

import org.junit.jupiter.api.Test;

public class TestCglibProxy {

    @Test
    public void testSave(){

        Object target = new PersonDaoImpl();
        Transaction transaction = new Transaction();
        Interceptor interceptor = new Interceptor(target, transaction);

        PersonDaoImpl personDaoImpl = (PersonDaoImpl) interceptor.createProxy();

        personDaoImpl.savePerson();
        System.out.println("============================");
        personDaoImpl.updatePerson();
    }

}
