//package com.fundfun.fundfund.domain.user;
//
//import javax.persistence.AttributeConverter;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Optional;
//
//public class RoleConverter implements AttributeConverter<Role, Integer> {
//
//    @Override
//    public Integer convertToDatabaseColumn(Role role) {
//        if(role == null) return null;
//        return role.getValue();
//    }
//
//    @Override
//    public Role convertToEntityAttribute(Integer dbData) {
//        if(dbData == null) return null;
//
//        return Role.values()[dbData];
//    }
//}
