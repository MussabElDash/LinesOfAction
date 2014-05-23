package utilities;

import engine.Point;

public class DxDy {

	public static int getDx(Point s, Point t) {
		int xd = t.getColumn() - s.getColumn();
		if (xd < 0) {
			return -1;
		} else if (xd > 0) {
			return 1;
		}
		return 0;
	}

	public static int getDy(Point s, Point t) {
		int yd = t.getRow() - s.getRow();
		if (yd < 0) {
			return -1;
		} else if (yd > 0) {
			return 1;
		}
		return 0;
	}
}
