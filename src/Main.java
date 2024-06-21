import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static AtomicInteger wordsLength3 = new AtomicInteger();
    private static AtomicInteger wordsLength4 = new AtomicInteger();
    private static AtomicInteger wordsLength5 = new AtomicInteger();

    public static void main(String[] args) {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindromeThread = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    switch (text.length()) {
                        case 3:
                            wordsLength3.incrementAndGet();
                            break;
                        case 4:
                            wordsLength4.incrementAndGet();
                            break;
                        case 5:
                            wordsLength5.incrementAndGet();
                            break;
                    }
                }
            }
        });

        Thread sameThread = new Thread(() -> {
            for (String text : texts) {
                if (isSameLetter(text)) {
                    switch (text.length()) {
                        case 3:
                            wordsLength3.incrementAndGet();
                            break;
                        case 4:
                            wordsLength4.incrementAndGet();
                            break;
                        case 5:
                            wordsLength5.incrementAndGet();
                            break;
                    }
                }
            }
        });

        Thread increaseThread = new Thread(() -> {
            for (String text : texts) {
                if (isIncrease(text)) {
                    switch (text.length()) {
                        case 3:
                            wordsLength3.incrementAndGet();
                            break;
                        case 4:
                            wordsLength4.incrementAndGet();
                            break;
                        case 5:
                            wordsLength5.incrementAndGet();
                            break;
                    }
                }
            }
        });

        palindromeThread.start();
        sameThread.start();
        increaseThread.start();

        try {
            palindromeThread.join();
            sameThread.join();
            increaseThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Красивых слов с длиной 3: " + wordsLength3 + " шт.");
        System.out.println("Красивых слов с длиной 4: " + wordsLength4 + " шт.");
        System.out.println("Красивых слов с длиной 5: " + wordsLength5 + " шт.");

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String text) {
        return new StringBuilder(text).reverse().toString().equals(text);
    }

    public static boolean isSameLetter(String text) {
        return text.chars().distinct().count() == 1;
    }

    public static boolean isIncrease(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) > text.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }
}