import java.io.*;
import java.util.*;

public class Calculator {
    private Map<String, Double> vars = new HashMap<>();

    public Double process(String input) {
        input = input.trim();
        if (input.isEmpty()) return null;

        // Handle variable assignment
        if (input.contains("=")) {
            String[] parts = input.split("=", 2);
            String var = parts[0].trim();
            String expr = parts[1].trim();
            double val = evaluate(expr);
            vars.put(var, val);
            return val;
        }

        return evaluate(input);
    }

    private double evaluate(String expr) {
        List<String> tokens = tokenize(expr);
        List<String> rpn = toRPN(tokens);
        return evalRPN(rpn);
    }

    private List<String> tokenize(String s) {
        List<String> out = new ArrayList<>();
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (Character.isWhitespace(c)) { i++; continue; }
            if ("+-*/^()%".indexOf(c) >= 0) { out.add(Character.toString(c)); i++; continue; }
            if (Character.isDigit(c) || c == '.') {
                int j = i + 1;
                while (j < s.length() && (Character.isDigit(s.charAt(j)) || s.charAt(j) == '.')) j++;
                out.add(s.substring(i, j)); i = j; continue;
            }
            if (Character.isLetter(c) || c == '_') {
                int j = i + 1;
                while (j < s.length() && (Character.isLetterOrDigit(s.charAt(j)) || s.charAt(j)=='_')) j++;
                out.add(s.substring(i, j)); i = j; continue;
            }
            throw new RuntimeException("Unexpected: " + c);
        }
        // handle unary minus by inserting 0 when '-' appears at start or after '(' or operator
        List<String> fixed = new ArrayList<>();
        for (int k = 0; k < out.size(); k++) {
            String t = out.get(k);
            if (t.equals("-")) {
                if (k == 0 || "()+-*/^%".contains(out.get(k-1))) {
                    fixed.add("0");
                }
            }
            fixed.add(t);
        }
        return fixed;
    }

    private int prec(String op) {
        switch (op) {
            case "+": case "-": return 1;
            case "*": case "/": case "%": return 2;
            case "^": return 3;
        }
        return 0;
    }

    private List<String> toRPN(List<String> tokens) {
        List<String> output = new ArrayList<>();
        Deque<String> ops = new ArrayDeque<>();
        for (String t : tokens) {
            if (t.matches("\\d+(\\.\\d+)?")) {
                output.add(t);
            } else if (t.matches("[a-zA-Z_]\\w*")) {
                // push variable name as-is; evalRPN will substitute
                output.add(t);
            } else if ("+-*/^%".contains(t)) {
                while (!ops.isEmpty() && !"(".equals(ops.peek()) &&
                       ( (prec(ops.peek()) > prec(t)) ||
                         (prec(ops.peek()) == prec(t) && !t.equals("^")) )) {
                    output.add(ops.pop());
                }
                ops.push(t);
            } else if (t.equals("(")) {
                ops.push(t);
            } else if (t.equals(")")) {
                while (!ops.isEmpty() && !ops.peek().equals("(")) output.add(ops.pop());
                if (ops.isEmpty()) throw new RuntimeException("Mismatched parentheses");
                ops.pop(); // pop "("
            } else {
                throw new RuntimeException("Unknown token: " + t);
            }
        }
        while (!ops.isEmpty()) {
            String op = ops.pop();
            if (op.equals("(") || op.equals(")")) throw new RuntimeException("Mismatched parentheses");
            output.add(op);
        }
        return output;
    }

    private double evalRPN(List<String> rpn) {
        Deque<Double> stack = new ArrayDeque<>();
        for (String t : rpn) {
            if (t.matches("\\d+(\\.\\d+)?")) {
                stack.push(Double.parseDouble(t));
            } else if (t.matches("[a-zA-Z_]\\w*")) {
                if (!vars.containsKey(t)) throw new RuntimeException("Unknown variable: " + t);
                stack.push(vars.get(t));
            } else if ("+-*/^%".contains(t)) {
                if (stack.size() < 2) throw new RuntimeException("Insufficient operands for " + t);
                double b = stack.pop();
                double a = stack.pop();
                switch (t) {
                    case "+": stack.push(a + b); break;
                    case "-": stack.push(a - b); break;
                    case "*": stack.push(a * b); break;
                    case "/":
                        if (b == 0) throw new RuntimeException("Division by zero");
                        stack.push(a / b); break;
                    case "%": stack.push(a % b); break;
                    case "^": stack.push(Math.pow(a, b)); break;
                }
            } else {
                throw new RuntimeException("Unexpected RPN token: " + t);
            }
        }
        if (stack.size() != 1) throw new RuntimeException("Malformed expression");
        return stack.pop();
    }
}

