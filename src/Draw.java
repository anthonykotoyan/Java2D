import java.awt.Color;
import java.awt.Graphics2D;

public class Draw {

    public static void point(vec2 pos, int rgb){
        if (pos.x < 0 || pos.x >= Main.WIDTH || pos.y < 0 || pos.y >= Main.HEIGHT) return;
        Main.pixels[(int)(pos.y * Main.WIDTH + pos.x)] = rgb & 0xFFFFFF;
    }
    public static void point(int x, int y, int rgb){
        if (x < 0 || x >= Main.WIDTH || y < 0 || y >= Main.HEIGHT) return;
        Main.pixels[y * Main.WIDTH + x] = rgb & 0xFFFFFF;
    }

    public static void line(vec2 start, vec2 end, int rgb){
        if (end.x == start.x){
            float dir = Math.signum(end.y-start.y);
            for (int y = 0; y<(int)Math.abs(end.y-start.y); y++){
                point((int)start.x, (int)(start.y + dir*y), rgb);
            }
            return;
        }
        float slope = (end.y-start.y)/(end.x-start.x);
        int dirX = (int)Math.signum(end.x-start.x);
        int dirY = (int)Math.signum(end.y-start.y);
        float y = start.y;
        for (int x = 0; x<(int)Math.abs(end.x-start.x); x++){
            int prevY = (int)y;
            y += slope*dirX;
            int deltaY = Math.abs(prevY-(int)y);
            for (int dy = 0; dy<deltaY+1; dy++) {
                point((int) (start.x + dirX * x), (prevY+ dirY*dy), rgb);
            }
        }
    }


    public static void triangle(vec2 v1, vec2 v2, vec2 v3, int rgb) {

        vec2[] verts = {v1, v2, v3};
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2 - i; j++) {
                if (verts[j].y > verts[j + 1].y) {
                    vec2 temp = verts[j];
                    verts[j] = verts[j + 1];
                    verts[j + 1] = temp;
                }
            }
        }
        vec2 a = verts[0], b = verts[1], c = verts[2];


        for (int y = (int) Math.ceil(a.y); y < b.y; y++) {
            float x1 = interpolate(a, b, y);
            float x2 = interpolate(a, c, y);
            if (x1 > x2) { float temp = x1; x1 = x2; x2 = temp; }
            for (int x = (int) Math.ceil(x1); x < x2; x++) {
                point(x, y, rgb);
            }
        }

        for (int y = (int) Math.ceil(b.y); y < c.y; y++) {
            float x1 = interpolate(b, c, y);
            float x2 = interpolate(a, c, y);
            if (x1 > x2) { float temp = x1; x1 = x2; x2 = temp; }
            for (int x = (int) Math.ceil(x1); x < x2; x++) {
                point(x, y, rgb);
            }
        }
    }


    public static void rect(vec2 topLeft, int length, int width, int rgb) {
        int startX = (int) Math.ceil(topLeft.x);
        int startY = (int) Math.ceil(topLeft.y);
        int endX = startX + length;
        int endY = startY + width;

        // Clamp to screen
        if (startX < 0) startX = 0;
        if (startY < 0) startY = 0;
        if (endX > Main.WIDTH) endX = Main.WIDTH;
        if (endY > Main.HEIGHT) endY = Main.HEIGHT;

        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                point(x, y, rgb);
            }
        }
    }
    public static void circle(vec2 center, int radius, int rgb)
    {
        int x = 0;
        int y = radius;
        int m = 5 - 4 * radius;

        while (x <= y)
        {
            line(new vec2(center.x - x, center.y - y), new vec2(center.x + x, center.y - y), rgb);
            line(new vec2(center.x - y, center.y - x), new vec2(center.x + y, center.y - x), rgb);
            line(new vec2(center.x - y, center.y + x), new vec2(center.x + y, center.y + x), rgb);
            line(new vec2(center.x - x, center.y + y), new vec2(center.x + x, center.y + y), rgb);

            if (m > 0)
            {
                y--;
                m -= 8 * y;
            }

            x++;
            m += 8 * x + 4;
        }
    }


    public static void text(String text, int x, int y, int rgb) {
        Graphics2D g = Main.img.createGraphics();
        g.setColor(new Color(rgb));
        g.drawString(text, x, y);
        g.dispose();
    }

    private static float interpolate(vec2 p1, vec2 p2, float y) {
        if (p1.y == p2.y) return p1.x;
        return p1.x + (p2.x - p1.x) * (y - p1.y) / (p2.y - p1.y);
    }

}
