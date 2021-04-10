import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Task2 {

    public boolean matches(String text, String regex) {
        AtomicBoolean result = new AtomicBoolean(false);
        Thread tryMatch = new Thread(() -> {
            try {
                result.set(Pattern.compile(regex).matcher(text).matches());
            } catch (PatternSyntaxException e) {
                // return false <==> do nothing
            }
        });
        tryMatch.start();
        try {
            tryMatch.join(1000);
        } catch (InterruptedException e) { /* ignore */ }
        return result.get();
    }

}
