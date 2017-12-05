package com.kt.biz.model;

import com.google.gson.JsonObject;
import com.kt.biz.model.annotation.AttributeType;
import com.kt.biz.model.annotation.ModelAttribute;
import com.kt.biz.model.order.CompletionCheckResult;
import com.kt.biz.model.util.TransformUtil;

import com.kt.biz.types.SchemeType;
import com.kt.entity.mysql.crm.Attribute;
import com.kt.entity.mysql.crm.SchemeEntity;
import com.kt.exception.EntityNotFoundException;
import com.kt.exception.SchemeValidationException;
import com.kt.sys.SpringContextHolder;
import com.kt.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/11.
 */
public abstract class AbstractScheme<E extends SchemeEntity> implements IScheme {
    protected E entity;

    public AbstractScheme() {
        entity = getInitializedEntity();
    }

    public abstract E getInitializedEntity();

    public abstract Class<? extends Attribute> getAttributeClass();

    @Override
    public void save() {
        entity.setType(getType().toString());
        entity = getEntityRepository().save(entity);
        saveAttributes();
    }

    @Override
    public void load(String id) throws EntityNotFoundException {
        entity = getEntityRepository().findOne(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        loadAttributes();
    }

    @Override
    public void purge() {
        getEntityRepository().delete(getId());
        getAttributeRepository().deleteByEntityId(getId());
    }


    @Override
    public void validate() throws SchemeValidationException {

    }


    @Override
    public void setId(String id) {
        entity.setId(id);
    }

    @Override
    public String getId() {
        return entity.getId();
    }

    @Override
    public String getDisplayName() {
        return entity.getDisplayName();
    }

    @Override
    public void setDisplayName(String displayName) {
        entity.setDisplayName(displayName);
    }

    @Override
    public String getCreatedBy() {
        return entity.getCreatedBy();
    }

    @Override
    public void setCreatedBy(String owner) {
        entity.setCreatedBy(owner);
    }

    @Override
    public void setCreatedTime(String createdTime) {
        entity.setCreatedTime(createdTime);
    }

    @Override
    public String getCreatedTime() {
        return entity.getCreatedTime();
    }

    @Override
    public SchemeType getType() {
        return null;
    }

    @Override
    public IScheme instantiate() {
        return null;
    }

    @Override
    public CompletionCheckResult checkCompletion() {
        return null;
    }

    @Override
    public void loadFromJson(JsonObject json) {
        Object displayName = TransformUtil.getAttributeValue(json, "displayName", AttributeType.STRING);
        if (displayName != null) {
            this.setDisplayName(String.valueOf(displayName));
        }

        BeanWrapper target = new BeanWrapperImpl(this);
        for (Field field : this.getClass().getDeclaredFields()) {
            ModelAttribute annotation = field.getAnnotation(ModelAttribute.class);
            if (annotation != null && annotation.changeable()) {
                Object value = TransformUtil.getAttributeValue(json, annotation.value(), annotation.type());
                if (value != null) {
                    target.setPropertyValue(field.getName(), value);
                }
            }
        }
    }

    @Override
    public JsonObject saveAsJson() {
        JsonObject result = new JsonObject();
        BeanWrapper src = new BeanWrapperImpl(this);
        for (Field field : this.getClass().getDeclaredFields()) {
            ModelAttribute annotation = field.getAnnotation(ModelAttribute.class);
            if (annotation != null) {
                Object value = src.getPropertyValue(field.getName());
                TransformUtil.attachElement2JsonObject(result, annotation.value(), value, annotation.type());
            }
        }
        result.addProperty("displayName", getDisplayName());
        result.addProperty("type", getType().toString());

        return result;
    }

    protected abstract JpaRepository<E, String> getEntityRepository();


    protected abstract ISchemeEntityAttributeRepository getAttributeRepository();

    protected E cloneEntity() {
        E newEntity = getInitializedEntity();
        newEntity.setDisplayName(entity.getDisplayName());
        return newEntity;
    }


    private void saveAttributes() {
        List<? extends Attribute> attributes = convertFields2Attributes();
        ISchemeEntityAttributeRepository repository = getAttributeRepository();
        for (Attribute attribute : attributes) {
            repository.save(attribute);
        }
    }

    private void loadAttributes() {
        ISchemeEntityAttributeRepository repository = getAttributeRepository();
        List<Attribute> attributes = repository.findByEntityId(entity.getId());
        setFieldsWithAttributes(attributes);
    }


    private void setFieldsWithAttributes(List<Attribute> attributes) {
        BeanWrapper target = new BeanWrapperImpl(this);
        for( Field field : this.getClass().getDeclaredFields()) {
            ModelAttribute annotation = field.getAnnotation(ModelAttribute.class);
            if (annotation != null) {
                for (Attribute attribute : attributes) {
                    if (annotation.value().equals(attribute.getName())) {
                        target.setPropertyValue(field.getName(), convertString2Field(attribute.getValue(), annotation.type()));
                    }
                }
            }
        }
    }

    private Object convertString2Field(String value, AttributeType type) {
        switch (type) {
            case STRING:
                return value;
            case INT:
                if (value == null) {
                    return 0;
                } else {
                    return Integer.valueOf(value);
                }
            case FLOAT:
                if (value == null) {
                    return 0;
                } else {
                    return Float.valueOf(value);
                }
            case DOUBLE:
                if (value == null) {
                    return 0;
                } else {
                    return Double.valueOf(value);
                }
            case STRING_LIST:
                String[] array = StringUtils.split(value, ";");
                if (array == null) {
                    return new ArrayList<>();
                }
                return Arrays.asList(array);
            case INT_LIST:
                break;
            case FLOAT_LIST:
                break;
            case DATE:
                try {
                    return DateUtil.toDate(value);
                } catch (ParseException e) {
                    try {
                        return DateUtil.toShortDate(value);
                    } catch (ParseException ignore) {
                    }
                }
        }
        return null;
    }


    private List<Attribute> convertFields2Attributes() {
        List<Attribute> attributes = new ArrayList<>();
        for (Field field : this.getClass().getDeclaredFields()) {
            ModelAttribute annotation = field.getAnnotation(ModelAttribute.class);
            if (annotation != null) {
                try {
                    Attribute attribute = getAttributeClass().newInstance();
                    attribute.setEntityId(entity.getId());
                    attribute.setName(annotation.value());
                    attribute.setValue(covertField2String(field, annotation.type()));
                    attributes.add(attribute);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return attributes;
    }

    protected <I> I getRepository(Class<I> c) {
        return SpringContextHolder.getBean(c);
    }

    private String covertField2String(Field field, AttributeType type) {
        try {
            BeanWrapper src = new BeanWrapperImpl(this);
            Object value = src.getPropertyValue(field.getName());
            if (value == null) {
                return null;
            }
            switch (type) {
                case STRING:
                    return String.valueOf(value);
                case INT:
                    return String.valueOf(value);
                case FLOAT:
                    return String.valueOf(value);
                case DOUBLE:
                    return String.valueOf(value);
                case STRING_LIST:
                    if (value instanceof List) {
                        List list = (List) value;
                        return StringUtils.join(list, ";");
                    }
                    break;
                case INT_LIST:
                    //TODO: 目前未发现INT_LIST类型需求
                    break;
                case FLOAT_LIST:
                    //TODO: 目前未发现FLOAT_LIST需求
                    break;
                case DATE:
                    if (value instanceof Date) {
                        return DateUtil.formatDate((Date) value);
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
