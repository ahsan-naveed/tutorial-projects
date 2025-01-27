import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

public class Apples {
    
    enum Color {
        GREEN,
        RED
    }

    // abstract on the List type to go beyond the problem domain
    static public interface Predicate<T> {
        boolean test(T t);
    }

    // program to an interface not an implementation

    static public interface AppleFormatter {
        String accept(Apple apple);
    }

    static public class PrintWeight implements AppleFormatter {
        public String accept(Apple apple) {
            return "Weight: " + apple.getWeight().toString();
        }
    }

    static public class PrintColorAndWeight implements AppleFormatter {
        public String accept(Apple apple) {
            StringBuilder result = new StringBuilder();
            String color = Color.GREEN.equals(apple.getColor()) ? "Green" : "Red";
            
            result.append(color + " apple with " + apple.getWeight().toString() + " weight");

            return result.toString();
        }
    }


    static public class PrintHeavyLightColorApple implements AppleFormatter {
        public String accept(Apple apple) {
            StringBuilder result = new StringBuilder();
            String color = Color.GREEN.equals(apple.getColor()) ? "green" : "red";
            String weightCategory = apple.getWeight() > 150 ? "Heavy" : "Light";
            
            result.append(weightCategory + " " + color + " " + "apple");

            return result.toString();
        }
    }

    // class of algorithms (strategies)
    static public interface ApplePredicate {
        boolean test(Apple apple);
    }

    // strategy pattern

    // strategy I: green apples
    static public class AppleGreenColorPredicate implements ApplePredicate {
        public boolean test(Apple apple) {
            return Color.GREEN.equals(apple.getColor());
        }
    }

    // strategy II: heavy apples
    static public class AppleHeavyWeightPredicate implements ApplePredicate {
        public boolean test(Apple apple) {
            return apple.getWeight() > 150;
        }
    }

    // strategy III: red and heavy apples
    static public class AppleRedAndHeavyPredicate implements ApplePredicate {
        public boolean test(Apple apple) {
            return Color.RED.equals(apple.getColor()) && apple.getWeight() > 150;
        }
    }

    static public class Apple {
        Color _color;
        Integer _weight;
    
        public Apple(Color color, Integer weight){
            _color = color;
            _weight = weight;
        }
        
        public Color getColor() {
            return _color;
        }

        public Integer getWeight() {
            return _weight;
        }
    }

    static public List<Apple> filterApplesByColor(List<Apple> apples, Color c) {
        return apples
            .stream()
            .filter(a -> a.getColor().equals(c))
            .collect(Collectors.toList());
    }

    // behavior parameterization
    static public List<Apple> filterApples(List<Apple> apples, ApplePredicate p) {
        return apples
            .stream()
            .filter(a -> p.test(a))
            .collect(Collectors.toList());
    }

    static public String prettyPrintApple(List<Apple> apples, AppleFormatter f) {
        StringBuilder result = new StringBuilder(); 
        apples.forEach(a -> result.append(f.accept(a) + "\n"));
        return result.toString();
    }

    // abstract on the List type to go beyond the problem domain
    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        return list
            .stream()
            .filter(item -> p.test(item))
            .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        
        List<Apple> apples = new ArrayList<>();
        
        IntStream.range(0, 20).forEach(i -> {
            if ( i % 2 == 0) {
                apples.add(new Apple(Color.GREEN, i * 25));
            } else {
                apples.add(new Apple(Color.RED, i * 15));
            }
        });
        
        // brittle filtering
        System.out.println(filterApplesByColor(apples, Color.GREEN));

        // flexible filtering
        System.out.println(filterApples(apples, new AppleGreenColorPredicate()));
        System.out.println(filterApples(apples, new AppleHeavyWeightPredicate()));
        System.out.println(filterApples(apples, new AppleRedAndHeavyPredicate()));

        System.out.println(prettyPrintApple(apples, new PrintWeight()));
        System.out.println(prettyPrintApple(apples, new PrintColorAndWeight()));
        System.out.println(prettyPrintApple(apples, new PrintHeavyLightColorApple()));

        // Note:
        // the behavior of the filterApples method depends on the code we pass to it
        // via the ApplePredicate object. We've parameterized the behavior of the
        // filterApples method

        // by using lambdas, you can directly pass the expression RED.equals(apple.
        // getColor()) && apple.getWeight() > 150 to the filterApples method without 
        // having to define multiple ApplePredicate classes. This removes unnecessary 
        // verbosity.

        // anonymous classes => declare and intialize the class at the same time
        // Parameterizes the behavior of the method filterApples with an anonymous class
        List<Apple> redApples = filterApples(apples, new ApplePredicate() {
            public boolean test(Apple apple) {
                return Color.RED.equals(apple.getColor());
            }
        });

        System.out.println(prettyPrintApple(redApples, new PrintHeavyLightColorApple()));

        // Even though anonymous classes somewhat tackle the verbosity associated
        // with declaring multiple concrete classes for an interface, they’re still
        // unsatisfactory.
        List<Apple> redHeavyApples = filterApples(apples, (Apple apple) -> {
            return Color.RED.equals(apple.getColor()) && apple.getWeight() > 150;
        });

        System.out.println(prettyPrintApple(
            redHeavyApples,
            new PrintHeavyLightColorApple())
        );

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Apple> lightApples = filter(apples, (Apple apple) -> apple.getWeight() < 150);
        List<Integer> evenNumbers = filter(numbers, (Integer num) -> num % 2 == 0);

        System.out.println(prettyPrintApple(lightApples, new PrintHeavyLightColorApple()));
        System.out.println(evenNumbers);
    }   
}