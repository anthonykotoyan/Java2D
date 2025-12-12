// demo file
// delete
public class Test {
    static vec2 v1;
    static vec2 v2;
    static vec2 v3;
    static vec2 v4;
    static int rand1;
    static int rand2;
    static int rand3;
    static int rgb;
    static double time = 0;

    static void initDemo(){
        Test.time = 0;
        Test.rgb = Main.rgb((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
        int spacing = 100;
        Test.v1 = new vec2((float)(spacing + (Main.WIDTH-spacing)*Math.random()), (float) (spacing + (Main.HEIGHT-spacing)*Math.random()));
        Test.v2 = new vec2((float)(spacing + (Main.WIDTH-spacing)*Math.random()), (float) (spacing + (Main.HEIGHT-spacing)*Math.random()));
        Test.v3 = new vec2((float)(spacing + (Main.WIDTH-spacing)*Math.random()), (float) (spacing + (Main.HEIGHT-spacing)*Math.random()));
        Test.v4 = new vec2((float)(spacing + (Main.WIDTH-spacing)*Math.random()), (float) (spacing + (Main.HEIGHT-spacing)*Math.random()));
        rand1 = (int)((Main.WIDTH-spacing)*Math.random());
        rand2 = (int)((Main.WIDTH-spacing)*Math.random());
        rand3 = (int)((Main.WIDTH-spacing)*Math.random());
    }


    public static void demo() {
        time += Main.deltaTime;
        Main.clear(Main.rgb(0, 0, 0));

        Test.v1.set((float)(Main.WIDTH/2 + Math.sin(time) * 200), (float)(Main.HEIGHT/2 + Math.cos(time) * 150));
        Test.v2.set((float)(Main.WIDTH/2 + Math.sin(time + 2) * 180), (float)(Main.HEIGHT/2 + Math.cos(time + 2) * 130));
        Test.v3.set((float)(Main.WIDTH/2 + Math.sin(time + 4) * 220), (float)(Main.HEIGHT/2 + Math.cos(time + 4) * 170));

        Draw.triangle(Test.v1, Test.v2, Test.v3, Main.rgb((int)(128 + 127 * Math.sin(time)), (int)(128 + 127 * Math.cos(time)), 255));

        Draw.rect(new vec2(100 + (int)(50 * Math.sin(time * 2)), 100 + (int)(50 * Math.cos(time * 2))), 200, 150, Main.rgb(0, 255, (int)(128 + 127 * Math.sin(time * 3))));

        Test.v4.set((float)(Main.WIDTH/2 + Math.sin(time * 1.5) * 300), (float)(Main.HEIGHT/2 + Math.cos(time * 1.5) * 200));
        Draw.circle(Test.v4, 20 + (int)(10 * Math.sin(time * 4)), Main.rgb(255, (int)(128 + 127 * Math.cos(time)), 0));

        Draw.line(new vec2(0, 0), new vec2(Main.WIDTH, Main.HEIGHT), Main.rgb(255, 0, 0));

        Draw.line(new vec2(0, Main.HEIGHT), new vec2(Main.WIDTH, Main.HEIGHT), Main.rgb(0, 255, 0));

        Draw.line(new vec2(Main.WIDTH/4, Main.HEIGHT/2), new vec2(Main.WIDTH/2, Main.HEIGHT/4), Main.rgb(0, 0, 255));
        Draw.line(new vec2(Main.WIDTH/2, Main.HEIGHT/4), new vec2(3*Main.WIDTH/4, Main.HEIGHT/2), Main.rgb(0, 0, 255));

        Draw.line(new vec2(Main.WIDTH/8, Main.HEIGHT), new vec2(Main.WIDTH/8 + 50, Main.HEIGHT - 100), Main.rgb(139, 69, 19));
        Draw.line(new vec2(Main.WIDTH/8 + 50, Main.HEIGHT - 100), new vec2(Main.WIDTH/8 + 100, Main.HEIGHT), Main.rgb(139, 69, 19));

        Draw.line(new vec2(7*Main.WIDTH/8, Main.HEIGHT), new vec2(7*Main.WIDTH/8 - 50, Main.HEIGHT - 80), Main.rgb(139, 69, 19));
        Draw.line(new vec2(7*Main.WIDTH/8 - 50, Main.HEIGHT - 80), new vec2(7*Main.WIDTH/8 - 100, Main.HEIGHT), Main.rgb(139, 69, 19));

        Draw.line(new vec2(Main.WIDTH/2 - 20, Main.HEIGHT - 50), new vec2(Main.WIDTH/2 + 20, Main.HEIGHT - 50), Main.rgb(255, 255, 0));
        Draw.line(new vec2(Main.WIDTH/2, Main.HEIGHT - 70), new vec2(Main.WIDTH/2, Main.HEIGHT - 30), Main.rgb(255, 255, 0));

        Draw.line(new vec2(100, 100), new vec2(150, 50), Main.rgb(255, 0, 255));
        Draw.line(new vec2(150, 50), new vec2(200, 100), Main.rgb(255, 0, 255));

        Draw.line(new vec2(Main.WIDTH - 200, 150), new vec2(Main.WIDTH - 150, 100), Main.rgb(0, 255, 255));
        Draw.line(new vec2(Main.WIDTH - 150, 100), new vec2(Main.WIDTH - 100, 150), Main.rgb(0, 255, 255));

        for (int i = 0; i < 20; i++) {
            float angle = (float)(time * 2 + i * 0.5f);
            int x = (int)(Main.WIDTH/2 + Math.cos(angle) * 100);
            int y = (int)(Main.HEIGHT/2 + Math.sin(angle) * 100);
            Draw.circle(new vec2(x, y), 5, Main.rgb((int)(128 + 127 * Math.sin(time + i)), (int)(128 + 127 * Math.cos(time + i)), 255));
        }

        for (int i = 0; i < 10; i++) {
            int x1 = (int)(Math.sin(time * 3 + i) * Main.WIDTH/4 + Main.WIDTH/2);
            int y1 = (int)(Math.cos(time * 3 + i) * Main.HEIGHT/4 + Main.HEIGHT/2);
            int x2 = (int)(Math.sin(time * 3 + i + 1) * Main.WIDTH/4 + Main.WIDTH/2);
            int y2 = (int)(Math.cos(time * 3 + i + 1) * Main.HEIGHT/4 + Main.HEIGHT/2);
            Draw.line(new vec2(x1, y1), new vec2(x2, y2), Main.rgb(255, (int)(128 + 127 * Math.sin(time + i * 0.1)), 0));
        }

        Draw.triangle(new vec2(Main.WIDTH/4 + (int)(50 * Math.sin(time * 1.2)), Main.HEIGHT/4 + (int)(50 * Math.cos(time * 1.2))), new vec2(Main.WIDTH/4 + 50, Main.HEIGHT/4 - 50), new vec2(Main.WIDTH/4 - 50, Main.HEIGHT/4 + 50), Main.rgb(255, 0, 255));

        Draw.circle(new vec2(Main.WIDTH * 3/4, Main.HEIGHT/4), 40 + (int)(20 * Math.sin(time * 2)), Main.rgb(0, 255, 255));

        for (int i = 0; i < 15; i++) {
            int x = (int)(Main.WIDTH * 0.1 + i * Main.WIDTH * 0.06);
            int y = (int)(Main.HEIGHT * 0.8 + 20 * Math.sin(time * 4 + i * 0.2));
            Draw.rect(new vec2(x, y), 10, 20, Main.rgb((int)(255 * Math.sin(time + i)), 100, 200));
        }

        if (Main.mousePressed){
            Draw.circle(new vec2(Main.mouseX, Main.mouseY), 30 + (int)(20 * Math.sin(time * 5)), Main.rgb(255, 255, 0));
            for (int i = 0; i < 5; i++) {
                int dx = (int)(Math.cos(i * 1.256) * 50);
                int dy = (int)(Math.sin(i * 1.256) * 50);
                Draw.line(new vec2(Main.mouseX, Main.mouseY), new vec2(Main.mouseX + dx, Main.mouseY + dy), Main.rgb(255, 255, 0));
            }
        }

        Draw.text("Mouse: " + Main.mouseX + "," + Main.mouseY, 10, 20, Main.rgb(255,255,255));
        Draw.text("Pressed: " + Main.mousePressed, 10, 40, Main.rgb(255,255,255));
        Draw.text("FPS: " + String.format("%.1f", Main.currentFPS), 10, 60, Main.rgb(255,255,255));
        Draw.text("YMCMB2008", 10, 80, Main.rgb(255,255,255));
    }
}
