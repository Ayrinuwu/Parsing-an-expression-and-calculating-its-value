package org.example;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class MathCalc {

    MathCalc( InputStream stream){
        this.stream=stream;
    }
    InputStream stream;
    private static final List<String> operations = List.of("(", ")", "*", "+", "-", "/", "^");

    /**
     * Проверка: является ли строка знаком операции
     * @param str проверяемая строка
     * @return является ли знаком операции
     */
    private boolean isOperation(String str) {
        return operations.contains(str);
    }
    /**
     * Получение приоритета операции
     * @param operation знак операции
     * @return приоритет операции
     * @throws Exception
     */
    private int getPriority(String operation) throws Exception {
        switch (operation) {
            case "(":
                return -1;
            case "*":
            case "/":
            case "^":
                return 1;
            case "+":
            case "-":
                return 2;
            default:
                throw new Exception("Неподдерживаемая операция");
        }
    }
    /**
     * Выполнение заданной операции
     * @param a первое число
     * @param b второе число
     * @param operation знак операции
     * @return результат операции
     * @throws Exception неподдерживаемая операция
     */
    private double performOperation(double a, double b, String operation) throws Exception {
        switch (operation) {
            case "*":
                return a * b;
            case "/":
                return a / b;
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "^":
                return Math.pow(a, b);
        }

        throw new Exception("Неподдерживаемая операция '" + operation + "'");
    }
    /**
     * Разделение строки на элементы
     * @param expression исходное выражение
     * @return список элементов в исходном порядке
     */
    private List<String> parseExpression(String expression) {
        List<String> result = new ArrayList<>();

        for (String operation : operations) {
            expression = expression.replace(operation, " " + operation + " ");
        }

        expression = expression.replaceAll("\\s+", " ");
        for (Object operation : operations.stream().filter(it->!it.equals(")")).collect(Collectors.toList())) {
            expression = expression.replace(operation + " - ", operation + " -");
        }

        Scanner scanner = new Scanner(expression);
        while (scanner.hasNext()) {
            result.add(scanner.next());
        }

        return result;
    }

    /**
     * Проверяем является ли строка числом
     * @param str проверяемая строка
     * @return является ли числом
     */
    private boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Проверка с помощью регулярных выражений (переменные начинаются с буквы любого регистра, а затем идут
     * любое количество букв или цифр любого регистра)
     * @param element строка, которая может являться переменной
     * @return результат проверки
     */
    private boolean isVariable(String element) {
        return element.matches("[a-zA-Z]+[a-zA-Z0-9]*");
    }
    /**
     * функция получения значений для переменных
     * @param element название переменной
     * @return значение переменной element,введённое пользователем
     */
    public static double getKey(String element, InputStream stream) {
        System.out.print("Введите значение для переменной " + element + ": ");
        Scanner scanner = new Scanner(stream);
        double value = scanner.nextDouble();
        return value;
    }

    /**
     * Подсчет выражение математического выражение
     * @param expression математическое выражение
     * @return результат выражения
     * @throws Exception ошибки связанные с невалидным математическим выражением
     */
    public double calc(String expression) throws Exception {

        expression = String.format("(%s)", expression);
        List<String> elements = parseExpression(expression);

        Stack<Double> numbers = new Stack<>();
        Stack<String> functions = new Stack<>();

        Map<String, Double> variables = new HashMap<>();

        for (String element : elements) {

            if (isNumber(element)) {
                numbers.push(Double.parseDouble(element));
            } else if (isOperation(element)) {
                if (")".equals(element)) {
                    while (!functions.isEmpty() && !"(".equals(functions.peek())) {
                        if (numbers.size() < 2)
                            throw new Exception();
                        double b = numbers.pop();
                        double a = numbers.pop();

                        String operation = functions.pop();

                        numbers.push(performOperation(a, b, operation));
                    }

                    if (functions.isEmpty())
                        throw new Exception("Ожидалась открытая скобка");
                    functions.pop();
                } else {
                    while (canPushOut(element, functions)) {
                        if (numbers.size() < 2)
                            throw new Exception();
                        double b = numbers.pop();
                        double a = numbers.pop();

                        String operation = functions.pop();

                        numbers.push(performOperation(a, b, operation));
                    }

                    functions.push(element);
                }
            } else if (isVariable(element)) {
                if (!variables.containsKey(element)) {

                    variables.put(element, getKey(element,stream));
                }

                numbers.push(variables.get(element));
            } else
                throw new Exception("Неожиданный элемент '" + element + "'");
        }

        if (numbers.size() > 1 || functions.size() > 0)
            throw new Exception("ошибки связанные с невалидным математическим выражением");

        return numbers.peek();

    }

    /**
     *
     * @param operation операция для проверки
     * @param operations стек с операциями
     * @return true, если operation может вытолкнуть операцию в вершине стека, false иначе
     * @throws Exception
     */
    public boolean canPushOut(String operation, Stack<String> operations) throws Exception {
        if (operations.isEmpty())
            return false;

        int firstPriority = getPriority(operation);
        int secondPriority = getPriority(operations.peek());

        return secondPriority >= 0 && firstPriority >= secondPriority;
    }


}