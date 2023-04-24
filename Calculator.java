import java.util.Stack;
import java.util.function.BiFunction;

/**
 * Calculator.class
 *
 * @author xiecy
 * @date 2023/04/24
 */
public class Calculator {
    private double result;
    /**
     * 撤销日志
     */
    private Stack<Double> undoLog;
    /**
     * 重做日志
     */
    private Stack<Double> redoLog;

    public Calculator() {
        result = 0;
        undoLog = new Stack<>();
        redoLog = new Stack<>();
    }

    public void operator(BiFunction<Double, Double, Double> cmd, double a, double b) {
        undoLog.push(result);
        result = cmd.apply(a, b);
        redoLog.clear();
    }

    /**
     * 撤销
     */
    public void undo() {
        if (!undoLog.isEmpty()) {
            redoLog.push(result);
            result = undoLog.pop();
        }
    }

    /**
     * 重做
     */
    public void redo() {
        if (!redoLog.isEmpty()) {
            undoLog.push(result);
            result = redoLog.pop();
        }
    }

    public double getResult() {
        return result;
    }

    public static void main(String[] args) {
        //初始化计算器
        Calculator calculator = new Calculator();
        //开始增删改查
        calculator.operator(Operator::add, 3, 4);
        System.out.println(String.format("3 + 4 = %s", calculator.getResult()));
        calculator.operator(Operator::subtract, 7, 4);
        System.out.println(String.format("7 - 4 = %s", calculator.getResult()));
        calculator.operator(Operator::multiply, 5, 5);
        System.out.println(String.format("5 * 5 = %s", calculator.getResult()));
        calculator.operator(Operator::divide, 30, 2);
        System.out.println(String.format("30 / 2 = %s", calculator.getResult()));

        //测试撤消
        calculator.undo();
        System.out.println(String.format("撤销一次后的结果为：%s", calculator.getResult()));
        calculator.undo();
        System.out.println(String.format("再撤销一次后的结果为：%s", calculator.getResult()));

        //测试恢复
        calculator.redo();
        System.out.println(String.format("恢复操作一次后的结果为：%s", calculator.getResult()));
        calculator.redo();
        System.out.println(String.format("再恢复操作一次后的结果为：%s", calculator.getResult()));
    }
}

