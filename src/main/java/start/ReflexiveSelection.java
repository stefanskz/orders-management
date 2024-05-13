package start;

import java.lang.reflect.Field;

import java.util.List;

public class ReflexiveSelection {
    /**
     * Method to retrieve header of the object and the values from the list
     *
     * @param objects List of Objects
     * @return String[][]
     */
    public static String[][] retrieveProp(List<Object> objects) {
        String[][] outData = new String[objects.size() + 1][objects.get(0).getClass().getDeclaredFields().length];
        int k = 0, n = 0;
        for (Field fieldName : objects.get(0).getClass().getDeclaredFields()) {
            outData[0][k++] = fieldName.getName();
        }
        for (Object index : objects) {
            k = 0;
            n++;
            for (Field field : index.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object values;
                try {
                    values = field.get(index);
                    outData[n][k++] = String.valueOf(values);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return outData;
    }
}