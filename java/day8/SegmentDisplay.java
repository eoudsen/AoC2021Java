package day8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SegmentDisplay {

    private final String[] leftDigits;
    private String zero, one, two, five, six, nine;
    private Character topRight, bottomRight;
    private Integer resultValue = 0;

    public SegmentDisplay(final String[] leftDigits) {
        this.leftDigits = leftDigits;
    }

    public void determineNumbers() {
        for (var digit : this.leftDigits) {
            if (digit.length() == 2) {
                this.one = digit;
            }
        }

        for (var digit : this.leftDigits) {
            if (digit.length() == 6) {
                for (var chr : this.one.toCharArray()) {
                    if (digit.indexOf(chr) == -1) {
                        this.six = digit;
                        this.topRight = chr;
                        break;
                    }
                }
            }
        }

        for (var chr : this.one.toCharArray()) {
            if (chr != this.topRight) {
                this.bottomRight = chr;
            }
        }

        var countingMap = new String(String.join("", leftDigits).toCharArray()).chars().mapToObj(i->(char)i).collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        Character bottomLeft = new ArrayList<>(countingMap.keySet()).get(new ArrayList<>(countingMap.values()).indexOf(4L));

        for (var digit : this.leftDigits) {
            if (digit.length() == 5 && digit.indexOf(this.topRight) == -1 && digit.indexOf(bottomLeft) == -1) {
                this.five = digit;
            } else if (digit.length() == 5 && digit.indexOf(this.topRight) > -1 && digit.indexOf(bottomLeft) > -1 && digit.indexOf(this.bottomRight) == -1) {
                this.two = digit;
            } else if (digit.length() == 6 && digit.indexOf(this.topRight) > -1 && digit.indexOf(bottomLeft) > -1 && digit.indexOf(this.bottomRight) > -1) {
                this.zero = digit;
            } else if (digit.length() == 6 && digit.indexOf(this.topRight) > -1 && digit.indexOf(bottomLeft) == -1) {
                this.nine = digit;
            }
        }
    }

    public void calculateResultValue(final String[] outputValues) {
        var strBuilder = new StringBuilder();
        for (var digit : outputValues) {
            if (digit.length() < 2) {
                continue;
            }
            if (digit.length() == 2) {
                strBuilder.append(1);
            } else if (digit.length() == 3) {
                strBuilder.append(7);
            } else if (digit.length() == 4) {
                strBuilder.append(4);
            } else if (digit.length() == 7) {
                strBuilder.append(8);
            } else if (sameChars(digit, this.zero)) {
                strBuilder.append(0);
            } else if (sameChars(digit, this.two)) {
                strBuilder.append(2);
            } else if (sameChars(digit, this.five)) {
                strBuilder.append(5);
            } else if (sameChars(digit, this.six)) {
                strBuilder.append(6);
            } else if (sameChars(digit, this.nine)) {
                strBuilder.append(9);
            } else {
                strBuilder.append(3);
            }
        }
        this.resultValue = Integer.valueOf(strBuilder.toString());
    }

    public Integer getResultValue() {
        return this.resultValue;
    }

    private boolean sameChars(String firstStr, String secondStr) {
        char[] first = firstStr.toCharArray();
        char[] second = secondStr.toCharArray();
        Arrays.sort(first);
        Arrays.sort(second);
        return Arrays.equals(first, second);
    }


}
