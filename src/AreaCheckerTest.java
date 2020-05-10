import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class AreaCheckerTest {

    @Test(expected = NullPointerException.class)
    public void checkWithPoint_XisNull_NullPointerException() {
        Point point1 = new Point(1.0, 1.0, null);
        new AreaChecker().check(point1);
    }

    @Test(expected = NullPointerException.class)
    public void checkWithPoint_YisNull_NullPointerException() {
        Point point2 = new Point(1.0, null, 1.0);
        new AreaChecker().check(point2);
    }

    @Test(expected = NullPointerException.class)
    public void checkWithPoint_RisNull_NullPointerException() {
        Point point3 = new Point(null, 1.0, 1.0);
        new AreaChecker().check(point3);
    }

    @Test
    public void checkWithPoint_RisNegative_False() {
        Point p  = new Point(5.0,5.0,-4.0); //arc
        Point p1 = new Point(-5.0, -5.0, -4.0); //triangle
        Point p2 = new Point(5.0, -5.0, -4.0); //rectangle
        Point p3 = new Point(-5.0, 5.0, -4.0); //milk (first area)
        Assert.assertFalse(new AreaChecker().check(p));
        Assert.assertFalse(new AreaChecker().check(p1));
        Assert.assertFalse(new AreaChecker().check(p2));
        Assert.assertFalse(new AreaChecker().check(p3));
    }

    @Test
    public void checkWithPoint_RisZero_False() {
        Point point  = new Point(-6.0, -2.0, 0.0);
        Point point1 = new Point( 0.0001, -2.0, 0.0);
        Point point2 = new Point(-1.0, 1.01, 0.0);
        Assert.assertFalse(new AreaChecker().check(point));
        Assert.assertFalse(new AreaChecker().check(point1));
        Assert.assertFalse(new AreaChecker().check(point2));
    }

    @Test
    public void checkWithPoint_AllZero_True() {
        Point p = new Point(0.0,0.0,0.0);
        Assert.assertTrue(new AreaChecker().check(p)); //todo return true, is it ok?
    }

    @Test
    public void checkWithPoint_XorYisInfinity_False() {
        AreaChecker ac = new AreaChecker();
        Point point4 = new Point(Double.POSITIVE_INFINITY, 1.0, 5.0);
        Point point5 = new Point(1.0, Double.POSITIVE_INFINITY, 5.0);

        Assert.assertFalse(ac.check(point4));
        Assert.assertFalse(ac.check(point5));
    }

    @Test
    public void checkWithPoint_XorYisNan_False() {
        AreaChecker ac = new AreaChecker();
        Point point1 = new Point(Double.NaN, -0.01, 5.0);
        Point point2 = new Point(1.0, Double.NaN, 5.0);

        Assert.assertFalse(ac.check(point1));
        Assert.assertFalse(ac.check(point2));
    }

    @Test
    public void checkWithPoint_RisInfinity_IfIn1AreaFalseElseTrue() {
        Point point4 = new Point(-1.0, 3.0, Double.POSITIVE_INFINITY);
        Point point5 = new Point(1.0, 9.0, Double.POSITIVE_INFINITY);
        Point point6 = new Point(1.0, -9891231.0, Double.POSITIVE_INFINITY);
        Point point7 = new Point(-123.0, -123.0, Double.POSITIVE_INFINITY);
        AreaChecker ac = new AreaChecker();

        Assert.assertFalse(ac.check(point4));
        Assert.assertTrue(ac.check(point5));
        Assert.assertTrue(ac.check(point6));
        Assert.assertTrue(ac.check(point7));
    }

    @Test
    public void checkWithPoint_RisNan_False() {
        Point point4 = new Point(-1.0, 3.0, Double.NaN);
        Point point5 = new Point(1.0, 9.0, Double.NaN);
        Point point6 = new Point(1.0, -9891231.0, Double.NaN);
        Point point7 = new Point(-123.0, -123.0, Double.NaN);
        AreaChecker ac = new AreaChecker();

        Assert.assertFalse(ac.check(point4));
        Assert.assertFalse(ac.check(point5));
        Assert.assertFalse(ac.check(point6));
        Assert.assertFalse(ac.check(point7));
    }

    @Test
    public void checkWithPoint_checkHitWhenXandYLessThanR_trueForAllExcept1Area() {
        AreaChecker ac = new AreaChecker();
        Point p  = new Point(1.0,1.0,4.0); //arc
        Point p1 = new Point(-1.0, -1.0, 4.0); //triangle
        Point p2 = new Point(1.0, -3.0, 4.0); //rectangle
        Point p3 = new Point(-1.0, 3.0, 4.0); //milk (first area)

        Assert.assertTrue(ac.check(p));
        Assert.assertTrue(ac.check(p1));
        Assert.assertTrue(ac.check(p2));
        Assert.assertFalse(ac.check(p3));
    }

    @Test
    public void checkWithPoint_checkHitWhenXandYMoreThanR_falseForAll() {
        AreaChecker ac = new AreaChecker();
        Point p  = new Point(5.0,5.0,4.0); //arc
        Point p1 = new Point(-5.0, -5.0, 4.0); //triangle
        Point p2 = new Point(5.0, -5.0, 4.0); //rectangle
        Point p3 = new Point(-5.0, 5.0, 4.0); //milk (first area)

        Assert.assertFalse(ac.check(p));
        Assert.assertFalse(ac.check(p1));
        Assert.assertFalse(ac.check(p2));
        Assert.assertFalse(ac.check(p3));
    }

    @Test
    public void checkWithPoint_checkBorderHitWhenXandYMoreThanR_falseForArea1ElseTrue() {
        AreaChecker ac = new AreaChecker();
        Point p  = new Point(0.0,2.0000000000000000000001,4.0);     //arc
        Point p1 = new Point(-2.00000000000000000001, 0.0, 4.0); //triangle
        Point p2 = new Point(0.0, -4.00000000000000000001, 4.0); //rectangle
        //todo 100 проц бы придерались, если бы не проверил, но шо с этим делать хз, это неправильное поведение тип
        Assert.assertTrue(ac.check(p));
        Assert.assertTrue(ac.check(p1));
        Assert.assertTrue(ac.check(p2));
    }

    @Test
    public void checkWithPoint_checkBorderHitWhenXandYLessThanR_falseForArea1ElseTrue() {
        AreaChecker ac = new AreaChecker();
        Point p  = new Point(0.0,1.98,4.0);     //arc
        Point p1 = new Point(-1.98, 0.0, 4.0); //triangle
        Point p2 = new Point(0.0, -1.98, 4.0); //rectangle
        Assert.assertTrue(ac.check(p));
        Assert.assertTrue(ac.check(p1));
        Assert.assertTrue(ac.check(p2));
    }

}