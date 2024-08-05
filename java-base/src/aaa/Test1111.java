package aaa;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Salmon
 * @since 2024-08-04
 */
public class Test1111 {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        System.out.println("hello");
        Result<?> result = new Result<>();
        System.out.println(result.getSuccess2("aaaa"));
        Result<String> result2 = new Result<>();
        System.out.println(result2.getSuccess("bbbbb"));

        SunObj2 sunObj2 = SunObj2.class.getDeclaredConstructor().newInstance();
        sunObj2.setA(2);
        System.out.println(sunObj2.getA());
        sunObj2.aa();
        sunObj2.cc();
    }
}
