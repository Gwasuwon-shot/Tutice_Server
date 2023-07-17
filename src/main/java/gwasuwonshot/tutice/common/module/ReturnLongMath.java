package gwasuwonshot.tutice.common.module;

public class ReturnLongMath {
    public static Long getPercentage(Long score, Long total) {
        Double result = 0D;
        if (score > 0) {
            result = (score.doubleValue()/total.doubleValue())*100;
        }
        return result.longValue();
    }

}
