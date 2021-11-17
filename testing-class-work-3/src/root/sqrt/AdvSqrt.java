package root.sqrt;

/**
 * @author Victor Kuliamin
 */
public class AdvSqrt extends Sqrt {
    private static double eps = Double.longBitsToDouble(0x3CB0000000000000L);
    private static long dgmask = 0x7FF0000000000000L;
    private static long mtmask = 0x000FFFFFFFFFFFFFL;
    private static int dgshift = 52;
    private static int sqdgadd = 0x1FF;
    // 0.1 * 2^(-1022)
    private static double dnrbnd = Double.longBitsToDouble(0x0010000000000000L);
    private static long odddeg = 0x3FF0000000000000L;
    private static long evndeg = 0x3FE0000000000000L;
    private static double nrmshif = Double.longBitsToDouble(0x4330000000000000L);

    public double sqrt(double x) {
        if (Double.isNaN(x) || x < 0) return Double.NaN;
        else if (x == 0 || x == 1 || Double.isInfinite(x)) return x;
        else {
            boolean dnr = false;
            // Мы пытаемся выяснить денормализовано ли число, поэтому сравнивать надо без учёта знака.
            if (Math.abs(x) < dnrbnd) {
                x = x * nrmshif;
                dnr = true;
                assert (Math.getExponent(x) >= -1022);
            }

            long b = Double.doubleToLongBits(x);
            int d = (int) ((b & dgmask) >> dgshift);
            double res, tmp = -1;
            int i = 0;

            if ((d & 1) != 0)
                b = (b & mtmask) | odddeg;
            else
                b = (b & mtmask) | evndeg;

            x = Double.longBitsToDouble(b);
            res = x;

            // Не добавили условие на случай, если что-то зациклится
            while (Math.abs(x - res * res) / x > eps && tmp != res) {
                i++;
                tmp = res;
                res = (tmp + x / tmp) / 2;
            }

            // Забыли округлить
            double resSqDelta = Math.abs(res * res - x);
            double resUpSqDelta = Math.abs((res + eps) * (res + eps) - x);
            double resDownSqDelta = Math.abs((res - eps) * (res - eps) - x);

            if (resUpSqDelta < resSqDelta && resUpSqDelta < resDownSqDelta ||
                    resSqDelta == resUpSqDelta && (Double.doubleToLongBits(res) & 1) == 1) {
                res += eps;
            } else if (resDownSqDelta < resSqDelta && resDownSqDelta < resUpSqDelta ||
                    resSqDelta == resDownSqDelta && (Double.doubleToLongBits(res) & 1) == 1) {
                res -= eps;
            }

            b = Double.doubleToLongBits(res);
            d = (int) Math.ceil((double) d / 2) + sqdgadd;
            if (dnr) d -= dgshift / 2;

            b = (b & mtmask) | ((long) d << dgshift);
            res = Double.longBitsToDouble(b);
            return res;
        }
    }

}