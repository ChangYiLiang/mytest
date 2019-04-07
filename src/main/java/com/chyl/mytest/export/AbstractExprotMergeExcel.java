package com.chyl.mytest.export;


import com.chyl.mytest.export.annotation.ExcelAttribute;
import com.chyl.mytest.export.annotation.ExcelElement;
import com.chyl.mytest.export.config.ElementTypePath;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author only-lilei
 * @create 2018-01-23 上午10:06
 **/
public class AbstractExprotMergeExcel {

    /**
     *
     * 获取所有字段
     * @param clazz
     * @param listField
     * @return  List<Field> 返回类型
     * @throws
     */
    protected List<Field> getAllField(Class<?> clazz, List<Field> listField) {
        if (listField == null) {
            listField = new ArrayList<>();
        }
        // 获取所有属性
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {

            if (field.isAnnotationPresent(ExcelAttribute.class)) {
                listField.add(field);
                // 类名,属性名
            } else if (field.isAnnotationPresent(ExcelElement.class)) {
                /**
                 * TODO 类型判断
                 */
                switch (ElementTypePath.getElementTypePath(field.getType().getName())) {
                    case SET:
                    case LIST:
                        Type genericFieldType = field.getGenericType();
                        getAllField(getClass(genericFieldType, 0), listField);
                        break;
                    case MAP:
                        listField.add(field);
                        break;
                    default:
                        getAllField(field.getClass(), null);
                        break;
                }
            }
        }

        return listField;
    }

    /**
     *
     * 得到泛型类对象 @param type @param i @return Class 返回类型 @throws
     */
    @SuppressWarnings("rawtypes")
    protected static Class getClass(Type type, int i) {
        if (type instanceof ParameterizedType) { // 处理泛型类型
            return getGenericClass((ParameterizedType) type, i);
        } else if (type instanceof TypeVariable) {
            return (Class) getClass(((TypeVariable) type).getBounds()[0], 0); // 处理泛型擦拭对象
        } else {// class本身也是type，强制转型
            return (Class) type;
        }
    }

    @SuppressWarnings("rawtypes")
    private static Class getGenericClass(ParameterizedType parameterizedType, int i) {
        Object genericClass = parameterizedType.getActualTypeArguments()[i];
        if (genericClass instanceof ParameterizedType) { // 处理多级泛型
            return (Class) ((ParameterizedType) genericClass).getRawType();
        } else if (genericClass instanceof GenericArrayType) { // 处理数组泛型
            return (Class) ((GenericArrayType) genericClass).getGenericComponentType();
        } else if (genericClass instanceof TypeVariable) { // 处理泛型擦拭对象
            return (Class) getClass(((TypeVariable) genericClass).getBounds()[0], 0);
        } else {
            return (Class) genericClass;
        }
    }
}
