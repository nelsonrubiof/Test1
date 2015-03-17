package com.scopix.periscope.periscopefoundation.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Entity;
import org.apache.log4j.Logger;

/**
 * This class transform a Entity into a DTO, DTO into a Entity, 
 * list of DTOs into a list of Entities and list of Entities into a list of DTOs with some rules. 
 * Rules: 
 * The DTO has the same name of properties and the name of DTO is [EntityName]DTO.
 * The properties has getters and setters with sun conventions.
 * @author César Abarza Suazo
 * @version 1.0.0
 */
public class DTOTranformUtil {

    private Logger log = Logger.getLogger(DTOTranformUtil.class);
    //CHECKSTYLE:OFF
    private static DTOTranformUtil INSTANCE = null;
    private static int recursiveLevels = 4;
    private volatile int currentRecursion = 0;

    private DTOTranformUtil() {
    }

    public static synchronized DTOTranformUtil getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DTOTranformUtil();
        }
        return INSTANCE;
    }
    //CHECKSTYLE:ON

    /**
     * This method permit transform a Entity objet into a DTO object
     * @param entity this object is the entity 
     * @param dto this object is the dto instanceinstance
     * @param fillCollection this mean if you wanna fill the list attributes or not.
     */
    public void transformEntityToDTO(Object entity, Object dto, boolean fillCollection) {
        log.debug("[transformEntityToDTO] Entity: " + entity + " DTO: " + dto + " fillCollection: " + fillCollection);
        List<Field> fields = new ArrayList<Field>();
        //todo los fields deben corresponder al dto y no al entity
        Class ent = entity.getClass();
        while (ent.getSuperclass() != null) {
            fields.addAll(Arrays.asList(ent.getSuperclass().getDeclaredFields()));
            ent = ent.getSuperclass();
        }
        fields.addAll(Arrays.asList(entity.getClass().getDeclaredFields()));
        for (Field f : fields) {
            try {
                log.debug("[transformEntityToDTO] Field: " + f.getName());
                PropertyDescriptor propertyEntity = new PropertyDescriptor(f.getName(), entity.getClass());
                PropertyDescriptor propertyDTO = new PropertyDescriptor(f.getName(), dto.getClass());
                Method setter = propertyDTO.getWriteMethod();
                log.debug("[transformEntityToDTO] Setter: " + setter.getName());
                Method getter = propertyEntity.getReadMethod();
                log.debug("[transformEntityToDTO] getter: " + getter.getName());
                if (isAList(f.getType()) && fillCollection) {
                    List list = (List) getter.invoke(entity);
                    log.debug("[transformEntityToDTO] List: " + list);
                    if (list != null && !list.isEmpty()) {
                        Class dtoClass = list.get(0).getClass();
                        log.debug("[transformEntityToDTO] DTO Class: " + dtoClass);
                        if (isAEntity(list.get(0).getClass())) {
                            dtoClass = Class.forName(getDTOName(list.get(0).getClass().getName()));
                        }
                        list = transformEntityListToDOTList(list, dtoClass, fillCollection);
                        log.debug("[transformEntityToDTO] transformEntityListToDOTList: " + list);
                        setter.invoke(dto, list);
                    }
                } else if (isAEntity(f.getType())) {
                    log.debug("[transformEntityToDTO] f.getType: " + f.getType());
                    log.debug("[transformEntityToDTO] currentRecursion: " + currentRecursion + " recursiveLevels: " +
                            recursiveLevels);
                    if (currentRecursion < recursiveLevels) {
                        try {
                            currentRecursion++;
                            Object obj = Class.forName(getDTOName(f.getType().getName())).newInstance();
                            log.debug("[transformEntityToDTO] obj: " + obj);
                            transformEntityToDTO(getter.invoke(entity), obj, fillCollection);
                            currentRecursion--;
                            log.debug("[transformEntityToDTO] obj: " + obj);
                            setter.invoke(dto, obj);
                        } catch (Exception e) {
                            currentRecursion--;
                            throw e;
                        }
                    }
                } else {
                    setter.invoke(dto, getter.invoke(entity));
                }
            } catch (Exception e) {
                log.debug("[transformEntityToDTO] Error " + e.getMessage());
            }
        }
    }

    /**
     * This method permit transform a DTO objet into a Entity object
     * @param dto this object is the dto instance
     * @param entity this object is the entity instance
     * @param fillCollection this mean if you wanna fill the list attributes or not.
     */
    public void transformDTOToEntity(Object dto, Object entity, boolean fillCollection) {
        log.debug("[transformDTOToEntity] Entity: " + entity + " DTO: " + dto + " fillCollection: " + fillCollection);
        Class entityClass = null;
        List<Field> fields = new ArrayList<Field>();
        Class ent = entity.getClass();
        while (ent.getSuperclass() != null) {
            fields.addAll(Arrays.asList(ent.getSuperclass().getDeclaredFields()));
            ent = ent.getSuperclass();
        }
        fields.addAll(Arrays.asList(entity.getClass().getDeclaredFields()));
        for (Field f : fields) {
            try {
                log.debug("[transformDTOToEntity] Field: " + f.getName());
                PropertyDescriptor propertyEntity = new PropertyDescriptor(f.getName(), entity.getClass());
                PropertyDescriptor propertyDTO = new PropertyDescriptor(f.getName(), dto.getClass());
                Method setter = propertyEntity.getWriteMethod();
                Method getter = propertyDTO.getReadMethod();
                log.debug("[transformDTOToEntity] Setter: " + setter.getName());
                log.debug("[transformDTOToEntity] getter: " + getter.getName());
                if (isAList(f.getType()) && fillCollection) {
                    List list = (List) getter.invoke(dto);
                    if (list != null && !list.isEmpty()) {
                        log.debug("[transformDTOToEntity] List: " + list);
                        //CHECKSTYLE:OFF
                        if ((entityClass = getEntityAssociated(list.get(0).getClass().getName())) == null) {
                            entityClass = list.get(0).getClass();
                        }
                        //CHECKSTYLE:ON
                        list = transformDTOListToEntity(list, entityClass, fillCollection);
                        log.debug("[transformDTOToEntity] transformDTOListToEntity: " + list);
                        setter.invoke(entity, list);
                    }
                    //CHECKSTYLE:OFF
                } else if ((entityClass = getEntityAssociated(f.getType().getName())) != null) {
                    log.debug("[transformDTOToEntity] f.getType: " + f.getType());
                    log.debug("[transformDTOToEntity] currentRecursion: " + currentRecursion + " recursiveLevels: " +
                            recursiveLevels);
                    if (currentRecursion < recursiveLevels) {
                        currentRecursion++;
                        Object obj = entityClass.newInstance();
                        log.debug("[transformDTOToEntity] obj: " + obj);
                        transformDTOToEntity(getter.invoke(dto), obj, fillCollection);
                        currentRecursion--;
                        log.debug("[transformDTOToEntity] obj: " + obj);
                        setter.invoke(entity, obj);
                    }
                    //CHECKSTYLE:ON
                } else {
                    setter.invoke(entity, getter.invoke(dto));
                }
            } catch (Exception e) {
                log.debug("[transformDTOToEntity] Error " + e.getMessage());
            }
        }
    }

    /**
     * This method permit transform a Entity list into a DTO list
     * @param entityList List that contain the Entity object
     * @param dtoClass represent the DTO Class
     * @param fillSubCollection this mean if you wanna fill sub collections or not
     * @return a list of instances of dtoClass 
     */
    public List transformEntityListToDOTList(List entityList, Class dtoClass, boolean fillSubCollection) {
        List result = null;
        if (entityList != null && !entityList.isEmpty()) {
            try {
                result = new ArrayList();
                Object dto = null;
                for (Object entity : entityList) {
                    dto = dtoClass.newInstance();
                    transformEntityToDTO(entity, dto, fillSubCollection);
                    result.add(dto);
                }
            } catch (Exception e) {
                log.debug("[transformEntityListToDOTList] error: " + e.getMessage());
            }
        }
        return result;
    }

    /**
     * This method permit transform a Entity list into a DTO list
     * @param dtoList List that contain the DTO object
     * @param entityClass represent the Entity Class
     * @param fillSubCollection this mean if you wanna fill sub collections or not
     * @return a list of instances of entityClass
     */
    public List transformDTOListToEntity(List dtoList, Class entityClass, boolean fillSubCollection) {
        List result = null;
        if (dtoList != null && !dtoList.isEmpty()) {
            try {
                result = new ArrayList();
                Object entity = null;
                for (Object dto : dtoList) {
                    dto = entityClass.newInstance();
                    transformDTOToEntity(dto, entity, fillSubCollection);
                    result.add(entity);
                }
            } catch (Exception e) {
                log.debug("[transformDTOListToEntity] error: " + e.getMessage());
            }
        }
        return result;
    }

    /**
     * This method return if the class is a java.lang.* or a primitive
     * @param type class to make camparation
     * @return true if is in the package java.lang or is a java primitive type
     */
    private boolean isJavaType(Class type) {
        boolean result = false;
        if (type.isPrimitive() || type.getPackage().getName().indexOf("java.lang") >= 0) {
            result = true;
        }
        return result;
    }

    /**
     * This method permit know if is a java.util.List subClass
     * @param type class to make camparation
     * @return true if is a subclass of java.util.List, otherwise return false
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     */
    private boolean isAList(Class type) throws InstantiationException, IllegalAccessException {
        boolean result = false;
        if (type.isInterface() && type == List.class) {
            result = true;
        } else if (!isJavaType(type)) {
            Object obj = type.newInstance();
            if (obj instanceof List) {
                result = true;
            }
        }
        log.debug("[isAList] class " + type + " result " + result);
        return result;
    }

    /**
     * This method permit know if the type is a entity or not
     * @param type class to make the comparation
     * @return true if the class has the @Entity annotation, otherwise false
     */
    private boolean isAEntity(Class type) {
        boolean result = false;
        if (type.isAnnotationPresent(Entity.class)) {
            return true;
        }
        log.debug("[isAEntity] class " + type + " result " + result);
        return result;
    }

    private String getDTOName(String entityName) {
        String dtoName = entityName.substring(0, entityName.lastIndexOf(".") + 1) + "dto" + entityName.substring(entityName.
                lastIndexOf("."), entityName.length());
        log.debug("[getDTOName] entityName " + entityName + " dtoName " + dtoName + "DTO");
        return dtoName + "DTO";
    }

    //CHECKSTYLE:OFF
    private String getEntityName(String DTOName) {
        String entityName = DTOName.replaceFirst(".dto.", ".");
        log.debug("[getEntityName] DTOName " + DTOName + " entityName " + entityName.substring(0, entityName.length() - 3));
        return entityName.substring(0, entityName.length() - 3);
    }
    //CHECKSTYLE:ON

    private Class getEntityAssociated(String className) {
        Class entityClass = null;
        try {
            entityClass = Class.forName(getEntityName(className));
        } catch (Exception e) {
            log.debug("[getEntityAssociated] error: " + e.getMessage());
        }
        return entityClass;
    }

    public int getRecursiveLevels() {
        return recursiveLevels;
    }

    public void setRecursiveLevels(int recursiveLevels) {
        DTOTranformUtil.recursiveLevels = recursiveLevels;
    }
}
