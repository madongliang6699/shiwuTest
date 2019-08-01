package myAOPtest.staticProxy;

public class PersonDaoProxy implements PersonDao {

    PersonDao personDao;
    Transaction transaction;

    public PersonDaoProxy(PersonDao personDao, Transaction transaction) {
        this.personDao = personDao;
        this.transaction = transaction;
    }


    @Override
    public void savePerson() {
        this.transaction.beginTransaction();
        this.personDao.savePerson();
        this.transaction.commit();
    }
}
