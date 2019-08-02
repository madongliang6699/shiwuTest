package myAOPtest.A_静态代理;


public class PersonDaoImpl implements PersonDao {

    @Override
    public void savePerson() {
        System.out.println("save person");
    }
}
