package baseball.core;

import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static camp.nextstep.edu.missionutils.Console.readLine;

public class GameStarter {

    private static boolean run;

    private final Matcher matcher;
    private boolean end;

    private GameStarter() {
        run = true;
        end = false;
        matcher = new Matcher(Randoms.pickUniqueNumbersInRange(1, 9, 3));
    }

    public static void start() {
        do {
            gameStart();
            gameEnd();
        } while (run);
    }

    private static void gameStart() {
        System.out.println("숫자 야구 게임을 시작합니다.");
        GameStarter starter = new GameStarter();

        while (!starter.end) {
            starter.play();
        }
    }

    private void play() {
        System.out.print("숫자를 입력해주세요 : ");
        String number = readLine();

        boolean matched = matcher.match(number);
        if (matched) {
            end = true;
        }
    }

    private static void gameEnd() {
        printEndMessage();
        run = isRetry(askRetry());
    }

    private static void printEndMessage() {
        System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
    }

    private static int askRetry() {
        System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
        return readNumber();
    }

    private static int readNumber() {
        String s = readLine();

        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static boolean isRetry(int retryValue) {
        if (retryValue == 1) return true;
        else if (retryValue == 2) return false;
        else throw new IllegalArgumentException();
    }

    private static class Matcher {

        private final List<Integer> numbers;
        private int ball;
        private int strike;

        private Matcher(List<Integer> numbers) {
            this.numbers = numbers;
            init();
        }

        private boolean match(String numberStr) {
            checkLength(numberStr);
            List<Integer> inputNumbers = getInputNumbers(numberStr);
            checkDuplicated(inputNumbers);
            boolean matched = match(inputNumbers);
            System.out.println(this);
            return matched;
        }

        private boolean match(List<Integer> inputNumbers) {
            init();

            for (int i = 0; i < inputNumbers.size(); i++) {
                Integer inputNumber = inputNumbers.get(i);
                if (numbers.get(i) == inputNumber) strike++;
                else if (numbers.contains(inputNumber)) ball++;
            }

            return strike == 3;
        }

        private void init() {
            ball = 0;
            strike = 0;
        }

        private void checkLength(String numberStr) {
            if (numberStr.length() != 3) throw new IllegalArgumentException();
        }

        private List<Integer> getInputNumbers(String numberStr) {
            List<Integer> inputNumbers = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                int number = numberStr.charAt(i) - '0';
                inputNumbers.add(number);
            }

            return inputNumbers;
        }

        private void checkDuplicated(List<Integer> inputNumbers) {
            Set<Integer> validSet = new HashSet<>(inputNumbers);
            if (validSet.size() != inputNumbers.size()) throw new IllegalArgumentException();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            if (existBall()) sb.append(ball).append("볼 ");
            if (existStrike()) sb.append(strike).append("스트라이크");
            if (sb.length() == 0) return "낫싱";

            return sb.toString();
        }

        private boolean existBall() {
            return ball != 0;
        }

        private boolean existStrike() {
            return strike != 0;
        }
    }
}
