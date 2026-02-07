package dao;

import user.Customer;

public class CustomerDao extends AbstractJpaDao{
    public CustomerDao() {
        setClazz(Customer.class);
    }
}
