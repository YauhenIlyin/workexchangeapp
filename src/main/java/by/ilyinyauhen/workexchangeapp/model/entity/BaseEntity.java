package by.ilyinyauhen.workexchangeapp.model.entity;

public abstract class BaseEntity {
    private long id;

    public BaseEntity(long id) {
        this.id = id;
    }

    public BaseEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
