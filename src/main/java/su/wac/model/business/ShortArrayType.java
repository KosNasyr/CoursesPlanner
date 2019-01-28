package su.wac.model.business;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShortArrayType implements UserType {
    private final int[] arrayTypes = new int[]{Types.ARRAY};

    public int[] sqlTypes() {
        return arrayTypes;
    }

    public Class<List> returnedClass() {
        return List.class;
    }

    public boolean equals(Object x, Object y) throws HibernateException {
        return x == null ? y == null : x.equals(y);
    }

    public int hashCode(Object x) throws HibernateException {
        return x == null ? 0 : x.hashCode();
    }


    @Override
    public Object nullSafeGet(ResultSet rs, String[] names,SharedSessionContractImplementor sharedSessionContractImplementor, Object owner)
            throws HibernateException, SQLException {
        if (names != null && names.length > 0 && rs != null && rs.getArray(names[0]) != null) {
            Object array = rs.getArray(names[0]).getArray();
            if (array instanceof Integer[])
                return Arrays.asList((Integer[]) array);
            else
                return Arrays.asList(convertShortArrayToInt((Short[]) array));
        }
        return null;
    }

    private Integer[] convertShortArrayToInt(Short[] array) {
        Integer[] intArray = new Integer[array.length];
        for (int i = 0; i < array.length; i++)
            intArray[i] = Integer.valueOf(array[i]);
        return intArray;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor sharedSessionContractImplementor)
            throws HibernateException, SQLException {
        if (value != null && st != null) {
            List<Integer> list = (List<Integer>) value;
            Integer[] castObject = list.toArray(new Integer[list.size()]);
            Array array = sharedSessionContractImplementor.connection().createArrayOf("smallint", castObject);
            st.setArray(index, array);
        } else {
            st.setNull(index, arrayTypes[0]);
        }
    }

    public Object deepCopy(Object value) throws HibernateException {
        if (value == null)
            return null;

        List<Integer> list = (List<Integer>) value;
        ArrayList<Integer> clone = new ArrayList<Integer>();
        for (Object intOn : list)
            clone.add((Integer) intOn);
        return clone;
    }

    public boolean isMutable() {
        return false;
    }

    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }
}