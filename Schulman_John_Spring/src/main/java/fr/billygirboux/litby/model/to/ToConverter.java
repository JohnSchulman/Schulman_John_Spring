package fr.billygirboux.litby.model.to;

// une convertor DTO
public interface ToConverter<T> {

    void fromEntity(T t);
}
