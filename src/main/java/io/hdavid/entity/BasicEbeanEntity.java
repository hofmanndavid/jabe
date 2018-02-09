package io.ingenia.gambling2.entity;

import io.ebean.Ebean;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import javax.naming.InitialContext;
import javax.persistence.*;
import javax.transaction.Status;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.transaction.UserTransaction;

@Log
@Getter@Setter
@MappedSuperclass
public abstract class BasicEbeanEntity {

    @Id
    private Long id;


    @Transient
    public boolean isNewBean() {
        return id == null;
    }

    @SneakyThrows
    private UserTransaction lookupUserTransaction() {
        return  (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
    }
//    @SneakyThrows
//    private boolean insideCurrentJtaTrx() {
//        TransactionSynchronizationRegistry tsr = (TransactionSynchronizationRegistry) new InitialContext().lookup("java:comp/TransactionSynchronizationRegistry");
//        return Status.STATUS_ACTIVE == tsr.getTransactionStatus();
//    }
//
//    @SneakyThrows
//    private void saveInNewTransaction() {
//        UserTransaction ut = lookupUserTransaction();
//        try {
//            ut.begin();
//            Ebean.save(this);
//        } finally {
//            ut.commit();
//
//        }
//    }
//    @SneakyThrows
//    private void deleteInNewTransaction() {
//        UserTransaction ut = lookupUserTransaction();
//        try {
//            ut.begin();
//            Ebean.delete(this);
//        } finally {
//            ut.commit();
//        }
//    }

    @SneakyThrows
    public void refresh() {
        Ebean.refresh(this);
    }

    @SneakyThrows
    public void save() {
        Ebean.save(this);
    }

    @SneakyThrows
    public void delete() {
        Ebean.delete(this);
    }


    @Override
    public String toString() {
        return this.getClass().getSimpleName()+"#"+getId();
    }

    @Override
    public int hashCode() {
        if (id == null)
            throw new IllegalStateException(this.getClass().getSimpleName()+".hashCode() used before having an id");
        return id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o == this)
            return true;

        if (this.getId() == null)
            throw new IllegalStateException(this.getClass().getSimpleName()+".equals() used before having an id");

//        log.info("sefl: "+this.getClass().getSimpleName() + " comparing to "+o.getClass().getSimpleName());

        // ebean's enhancement doesn't make class names different at runtime as hibernate does
        if (!o.getClass().getSimpleName().equals(this.getClass().getSimpleName()))
            return false;

        BasicEbeanEntity beeo = (BasicEbeanEntity) o;

        if (beeo.getId() == null)
            throw new IllegalStateException(o.getClass().getSimpleName()+".equals() used before having an id");

        return beeo.getId().equals(id);

    }
}
