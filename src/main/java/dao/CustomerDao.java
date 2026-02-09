package dao;

import user.Customer;

public class CustomerDao extends AbstractJpaDao<Long, Customer>{
    public CustomerDao() {
        this.setClazz(Customer.class);
    }
}
