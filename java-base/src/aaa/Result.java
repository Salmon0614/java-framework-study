package aaa;

/**
 * @author Salmon
 * @since 2024-08-04
 */
public class Result<T> {

    public Result() {
    }

    public T getSuccess(T t) {
        return t;
    }

    public <U> U getSuccess2(U t) {
        return t;
    }
}
