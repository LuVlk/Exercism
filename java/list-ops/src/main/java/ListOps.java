import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

class ListOps {

    static <T> List<T> append(List<T> list1, List<T> list2) {
        var result = new ArrayList<T>();
        for (T e : list1) {
            result.add(e);
        }
        for (T e : list2) {
            result.add(e);
        }
        return result;
    }

    static <T> List<T> concat(List<List<T>> listOfLists) {
        var result = new ArrayList<T>();
        for (List<T> list : listOfLists) {
            for (T e : list) {
                result.add(e);
            }
        }
        return result;
    }

    static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        var result = new ArrayList<T>();
        for (T elem : list) {
            if (predicate.test(elem)) {
                result.add(elem);
            }
        }
        return result;
    }

    static <T> int size(List<T> list) {
        int count = 0;
        for (@SuppressWarnings("unused") var e : list) {
            count++;
        }
        return count;
    }

    static <T, U> List<U> map(List<T> list, Function<T, U> transform) {
        var result = new ArrayList<U>();
        for (T e : list) {
            result.add(transform.apply(e));
        }
        return result;
    }

    static <T> List<T> reverse(List<T> list) {
        var result = new ArrayList<T>();
        var end = size(list) - 1;
        for (int i = end; i >= 0; i--) {
            result.add(list.get(i));
        }
        return result;
    }

    static <T, U> U foldLeft(List<T> list, U initial, BiFunction<U, T, U> f) {
        var result = initial;
        for (var e : list) {
            result = f.apply(result, e);
        }
        return result;
    }

    static <T, U> U foldRight(List<T> list, U initial, BiFunction<T, U, U> f) {
        var result = initial;
        for (var e : reverse(list)) {
            result = f.apply(e, result);
        }
        return result;
    }

    private ListOps() {
        // No instances.
    }

}
