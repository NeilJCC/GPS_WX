package com.ntrcb.servlet;

public class GPSFormatUtil {
	public static double pi = 3.1415926535897932384626;
	public static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
	public static double a = 6378245.0;
	public static double ee = 0.00669342162296594323;
	
	public static double transformLat(double x, double y) {
	    double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y
	            + 0.2 * Math.sqrt(Math.abs(x));
	    ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
	    ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
	    ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
	    return ret;
	}
	
	public static double transformLon(double x, double y) {
	    double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1
	            * Math.sqrt(Math.abs(x));
	    ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
	    ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
	    ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0
	            * pi)) * 2.0 / 3.0;
	    return ret;
	}
	public static double[] transform(double lat, double lon) {
	    if (outOfChina(lat, lon)) {
	        return new double[]{lat,lon};
	    }
	    double dLat = transformLat(lon - 105.0, lat - 35.0);
	    double dLon = transformLon(lon - 105.0, lat - 35.0);
	    double radLat = lat / 180.0 * pi;
	    double magic = Math.sin(radLat);
	    magic = 1 - ee * magic * magic;
	    double sqrtMagic = Math.sqrt(magic);
	    dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
	    dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
	    double mgLat = lat + dLat;
	    double mgLon = lon + dLon;
	    return new double[]{mgLat,mgLon};
	}
	public static boolean outOfChina(double lat, double lon) {
	    if (lon < 72.004 || lon > 137.8347)
	        return true;
	    if (lat < 0.8293 || lat > 55.8271)
	        return true;
	    return false;
	}

	public static double[] gps84_To_Gcj02(double lat, double lon) {
	    if (outOfChina(lat, lon)) {
	        return new double[]{lat,lon};
	    }
	    double dLat = transformLat(lon - 105.0, lat - 35.0);
	    double dLon = transformLon(lon - 105.0, lat - 35.0);
	    double radLat = lat / 180.0 * pi;
	    double magic = Math.sin(radLat);
	    magic = 1 - ee * magic * magic;
	    double sqrtMagic = Math.sqrt(magic);
	    dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
	    dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
	    double mgLat = lat + dLat;
	    double mgLon = lon + dLon;
	    return new double[]{mgLat, mgLon};
	}
	

	public static double[] gcj02_To_Gps84(double lat, double lon) {
	    double[] gps = transform(lat, lon);
	    double lontitude = lon * 2 - gps[1];
	    double latitude = lat * 2 - gps[0];
	    double[] a = {latitude, lontitude};
	    return a;
	}

	public static double[] gcj02_To_Bd09(double lat, double lon) {
	    double x = lon, y = lat;
	    double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
	    double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
	    double tempLon = z * Math.cos(theta) + 0.0065;
	    double tempLat = z * Math.sin(theta) + 0.006;
	    double[] gps = {tempLat,tempLon};
	    return gps;
	}
	

	public static double[] bd09_To_Gcj02(double lat, double lon) {
	    double x = lon - 0.0065, y = lat - 0.006;
	    double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
	    double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
	    double tempLon = z * Math.cos(theta);
	    double tempLat = z * Math.sin(theta);
	    double[] gps = {tempLat,tempLon};
	    return gps;
	}
	

	public static double[] gps84_To_bd09(double lat,double lon){
	    double[] gcj02 = gps84_To_Gcj02(lat,lon);
	    double[] bd09 = gcj02_To_Bd09(gcj02[0],gcj02[1]);
	    return bd09;
	}
	public static double[] bd09_To_gps84(double lat,double lon){
	    double[] gcj02 = bd09_To_Gcj02(lat, lon);
	    double[] gps84 = gcj02_To_Gps84(gcj02[0], gcj02[1]);
	    //保留小数点后六位
	    gps84[0] = retain6(gps84[0]);
	    gps84[1] = retain6(gps84[1]);
	    return gps84;
	}
	

	private static double retain6(double num){
	    String result = String .format("%.6f", num);
	    return Double.valueOf(result);
	}
	

	public static double[] map84ToSogou(double x,double y){
	    Point a=new Point(x,y);
	    Point b=new Point();
	    map84ToSogou(a,b);
	    return new double[]{b.x,b.y};
	}
	
	public static void map84ToSogou(Point a, Point b) {
	    double[] e = null;
	    for (int i = 0; i < LLBAND.length; i++) {
	        if (a.y > LLBAND[i]) {
	            e = LL2MC[i];
	            break;
	        }
	    }
	    f1(b, a, e);
	}
	
	public static class Point {
	    public double x, y;
	    public Point() {}
	
	    public Point(double x, double y) {
	        this.x = x;
	        this.y = y;
	    }
	}
	
	private static void f1(Point a, Point b, double[] c) {
	    a.x = c[0] + c[1] * W(b.x);
	    double f = W(b.y) / c[9];
	    a.y = c[2] + c[3] * f + c[4] * f * f + c[5] * f * f * f +
	            c[6] * f * f * f * f + c[7] * f * f * f * f * f +
	            c[8] * f * f * f * f * f * f;
	    a.x *= (b.x < 0 ? -1 : 1);
	    a.y *= (b.y < 0 ? -1 : 1);
	}
	
	private static double W(double a) {
	    return Math.abs(a);
	}
	
	private static double[] LLBAND = {75, 60, 45, 30, 15, 0};
	
	private static double[][] LL2MC = { { -0.00157021024440,
	        1.113207020616939e+005,
	        1.704480524535203e+015,
	        -1.033898737604234e+016,
	        2.611266785660388e+016,
	        -3.514966917665370e+016,
	        2.659570071840392e+016,
	        -1.072501245418824e+016,
	        1.800819912950474e+015, 82.50000000000000},
	        {8.277824516172526e-004,
	                1.113207020463578e+005,
	                6.477955746671608e+008,
	                -4.082003173641316e+009,
	                1.077490566351142e+010,
	                -1.517187553151559e+010,
	                1.205306533862167e+010,
	                -5.124939663577472e+009,
	                9.133119359512032e+008, 67.50000000000000},
	        {0.00337398766765, 1.113207020202162e+005,
	                4.481351045890365e+006,
	                -2.339375119931662e+007,
	                7.968221547186455e+007,
	                -1.159649932797253e+008,
	                9.723671115602145e+007,
	                -4.366194633752821e+007,
	                8.477230501135234e+006, 52.50000000000000},
	        {0.00220636496208, 1.113207020209128e+005,
	                5.175186112841131e+004,
	                3.796837749470245e+006,
	                9.920137397791013e+005,
	                -1.221952217112870e+006,
	                1.340652697009075e+006,
	                -6.209436990984312e+005,
	                1.444169293806241e+005, 37.50000000000000},
	        { -3.441963504368392e-004,
	                1.113207020576856e+005,
	                2.782353980772752e+002,
	                2.485758690035394e+006,
	                6.070750963243378e+003,
	                5.482118345352118e+004,
	                9.540606633304236e+003,
	                -2.710553267466450e+003,
	                1.405483844121726e+003, 22.50000000000000},
	        { -3.218135878613132e-004,
	                1.113207020701615e+005,
	                0.00369383431289, 8.237256402795718e+005,
	                0.46104986909093, 2.351343141331292e+003,
	                1.58060784298199, 8.77738589078284,
	                0.37238884252424,
	                7.45000000000000}
	};
}
