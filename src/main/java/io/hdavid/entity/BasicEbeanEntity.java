package io.hdavid.entity;

import io.ebean.Ebean;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import javax.persistence.*;

@Log
@Getter
@Setter
@MappedSuperclass
public abstract class BasicEbeanEntity {

    @Id
    private Long id;


    @Transient
    public boolean isPersisted() {
        return id != null;
    }

    public void refresh() {
        Ebean.refresh(this);
    }

    public void save() {
        Ebean.save(this);
    }
    public void markAsDirty() {
        Ebean.markAsDirty(this);
    }

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
            throw new IllegalStateException(this.getClass().getSimpleName()+".hashCode() invoked before having an id set");
        return id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o == this)
            return true;

        if (this.getId() == null)
            throw new IllegalStateException(this.getClass().getSimpleName()+".equals() invoked before having an id");

//        log.info("sefl: "+this.getClass().getSimpleName() + " comparing to "+o.getClass().getSimpleName());

        // ebean's enhancement doesn't make class names different at runtime as hibernate does
        if (!o.getClass().getSimpleName().equals(this.getClass().getSimpleName()))
            return false;

        BasicEbeanEntity beeo = (BasicEbeanEntity) o;

        if (beeo.getId() == null)
            throw new IllegalStateException(o.getClass().getSimpleName()+".equals() invoked before having an id");

        return beeo.getId().equals(id);

    }
}
