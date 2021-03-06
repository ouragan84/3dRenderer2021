package renderer.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

public class ZBuffer {
	
    private float[][] buffer; // array that holds depth for each pixel, you could probably use a Map<Point, Float>
    private Color[][] colors;
    private final int screenWidth; // width of the window.
    private final int screenHeight; // height of the window.
    private static final float BGDepth = 1000000;
    private static final Color BGColor = new Color(0, 0, 0);
 
    public ZBuffer(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.buffer = new float[screenWidth][screenHeight];
        this.colors = new Color[screenWidth][screenHeight];
        for(int i = 0; i < screenWidth; i++) {
        	for(int j = 0; j < screenHeight; j++) {
        		this.buffer[i][j] = BGDepth;
        		this.colors[i][j] = BGColor;
        	}
        }
    }
    
    public void drawBuffer(Graphics g) {
    	Color last;
    	int yLast;
    	for(int i = 0; i < this.screenWidth; i++) {
    		last = colors[i][0];
    		yLast = 0;
    		for(int j = 0; j < this.screenHeight; j++) {
    			if(!colors[i][j].equals(last)) {
    				g.setColor(last);
    				g.drawLine(i, yLast, i, j-1);
    				last = colors[i][j];
    	    		yLast = j;
    			}
    		}
    		g.setColor(last);
    		g.drawLine(i, yLast, i, screenHeight-1);
    	}
//    	for(int i = 0; i < this.screenWidth; i++) {
//    		for(int j = 0; j < this.screenHeight; j++) {
//    				g.setColor(colors[i][j]);
//    				g.drawLine(i, j, i, j);
//    		}
//    	}
    }
 
    public void pushFilledTriangle(Polygon polygon, float depth, Color color) { 
    	
//    	int[] xPoints = polygon.xpoints;
//    	int[] yPoints = polygon.ypoints;
//    	
//    	int MinYIndex = indexOfSmallest(yPoints);
//    	int MaxYIndex = indexOfLargest(yPoints);
//    	
//    	int pointAIndex = (MinYIndex + 1) % 3;
//    	int pointBIndex = (MinYIndex + 2) % 3;
//    	
//    	int toTheRight = indexOfSmallest((Math.atan2(yPoints[pointAIndex] - yPoints[MinYIndex], xPoints[pointAIndex] - xPoints[MinYIndex])), 
//    			(Math.atan2(yPoints[pointAIndex] - yPoints[MinYIndex], xPoints[pointAIndex] - xPoints[MinYIndex])));
//    	  
//    	if(toTheRight == pointAIndex) {
//    		toTheLeft = pointBIndex;
//    	}else {
//    		toTheLeft = pointAIndex;
//    	}
//    	
//    	double ToTheRightSlope = (yPoints[MinYIndex]-yPoints[toTheRight])/((double) xPoints[MinYIndex]-xPoints[toTheRight]);
//    	double ToTheLeftSlope = (yPoints[MinYIndex]-yPoints[toTheLeft])/((double) xPoints[MinYIndex]-xPoints[toTheLeft]);
//    	
//    	int AvgYIndex;
//    	if(MaxYIndex == pointAIndex) {
//    		AvgYIndex = pointBIndex;
//    	}else {
//    		AvgYIndex = pointAIndex;
//    	}
//    	
//		for(int i = Math.max(0, yPoints[MinYIndex]); i < Math.min(this.screenHeight-1, yPoints[AvgYIndex]); i++) {
//    		for(int j = Math.max(0, (int) (xPoints[MinYIndex] + ToTheRightSlope*i)); j <= Math.min(this.screenWidth-1, (int) (xPoints[MinYIndex] + ToTheLeftSlope*i)); j++) {
//    			if(depth < this.buffer[j][i]) {
//    				this.buffer[j][i] = depth;
//    				this.colors[j][i] = color;
//    			}
//    		}
//    	}
//		
//		if(xPoints[MaxYIndex] > xPoints[AvgYIndex]) {
//			ToTheRightSlope = (yPoints[MaxYIndex]-yPoints[MinYIndex])/((double) xPoints[MaxYIndex]-xPoints[MinYIndex]);
//	    	ToTheLeftSlope = (yPoints[MaxYIndex]-yPoints[AvgYIndex])/((double) xPoints[MaxYIndex]-xPoints[AvgYIndex]);
//	    	for(int i = Math.max(0, yPoints[AvgYIndex]); i <= Math.min(this.screenHeight-1, yPoints[MaxYIndex]); i++) {
//				for(int j = Math.max(0, (int) (xPoints[AvgYIndex] + ToTheRightSlope*i)); j <= Math.min(this.screenWidth-1, (int) (xPoints[MinYIndex] + ToTheLeftSlope*(i + yPoints[AvgYIndex]))); j++) {
//					if(depth < this.buffer[j][i]) {
//	    				this.buffer[j][i] = depth;
//	    				this.colors[j][i] = color;
//	    			}
//	    		}
//	    	}
//		}else {
//			ToTheRightSlope = (yPoints[MaxYIndex]-yPoints[AvgYIndex])/((double) xPoints[MaxYIndex]-xPoints[AvgYIndex]);
//	    	ToTheLeftSlope = (yPoints[MaxYIndex]-yPoints[MinYIndex])/((double) xPoints[MaxYIndex]-xPoints[MinYIndex]);
//	    	for(int i = Math.max(0, yPoints[AvgYIndex]); i <= Math.min(this.screenHeight-1, yPoints[MaxYIndex]); i++) {
//				for(int j = Math.max(0, (int) (xPoints[AvgYIndex] + ToTheRightSlope*i)); j <= Math.min(this.screenWidth-1, (int) (xPoints[MinYIndex] +ToTheLeftSlope*(i + yPoints[AvgYIndex]))); j++) {
//					if(depth < this.buffer[j][i]) {
//	    				this.buffer[j][i] = depth;
//	    				this.colors[j][i] = color;
//	    			}
//	    		}
//	    	}
//		}
		
    	int a = Math.max(0, polygon.xpoints[indexOfSmallest(polygon.xpoints)]);
    	int b = Math.min(this.screenWidth-1, polygon.xpoints[indexOfLargest(polygon.xpoints)]);
    	int c = Math.min(this.screenHeight-1, polygon.ypoints[indexOfLargest(polygon.ypoints)]);
    	for(int i = Math.max(0, polygon.ypoints[indexOfSmallest(polygon.ypoints)]); i <= c; i++) {
			for(int j = a; j <= b; j++) {
				if(polygon.contains(j, i) && depth < this.buffer[j][i]) {
    				this.buffer[j][i] = depth;
    				this.colors[j][i] = color;
    			}
    		}
    	}
    	
    }
    
    public static int indexOfSmallest(int ... array){

        // add this
        if (array.length == 0)
            return -1;

        int index = 0;
        int min = array[index];

        for (int i = 1; i < array.length; i++){
            if (array[i] <= min){
            	min = array[i];
            	index = i;
            }
        }
        return index;
    }
    
    public static int indexOfSmallest(double ... array){

        // add this
        if (array.length == 0)
            return -1;

        int index = 0;
        double min = array[index];

        for (int i = 1; i < array.length; i++){
            if (array[i] <= min){
            	min = array[i];
            	index = i;
            }
        }
        return index;
    }
    
    public static int indexOfLargest(int ... array){

        // add this
        if (array.length == 0)
            return -1;

        int index = 0;
        int max = array[index];

        for (int i = 1; i < array.length; i++){
            if (array[i] >= max){
            	max = array[i];
            	index = i;
            }
        }
        return index;
    }
    
    public void clearBuffer() {
    	for(int i = 0; i < screenWidth; i++) {
        	for(int j = 0; j < screenHeight; j++) {
        		this.colors[i][j] = BGColor;
        		this.buffer[i][j] = BGDepth;
        	}
        }
    }
}