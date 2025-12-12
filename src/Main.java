import java.util.Random;

public class Main {

    public static int WIDTH;
    public static int HEIGHT;
    static final int TARGET_FPS = 60;
    static Random rand = new Random();

    static volatile boolean capEnabled = false;
    static int mouseX, mouseY;
    static boolean mousePressed;
    static double currentFPS;

    static int[] pixels;
    public static java.awt.image.BufferedImage img;

    public static void main(String[] args) {

        WIDTH = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
        HEIGHT = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;

        javax.swing.JFrame frame = new javax.swing.JFrame("360NoScope");
        java.awt.Canvas canvas = new java.awt.Canvas();
        canvas.setPreferredSize(new java.awt.Dimension(WIDTH, HEIGHT));

        java.awt.GraphicsDevice gd = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            frame.setUndecorated(true);
        }

        frame.add(canvas);
        frame.pack();
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        if (gd.isFullScreenSupported()) {
            frame.setVisible(true);
            gd.setFullScreenWindow(frame);
            canvas.setBounds(0, 0, WIDTH, HEIGHT);
        } else {
            frame.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        }

        canvas.createBufferStrategy(2);

        img = new java.awt.image.BufferedImage(WIDTH, HEIGHT, java.awt.image.BufferedImage.TYPE_INT_RGB);
        java.awt.image.DataBufferInt db = (java.awt.image.DataBufferInt) img.getRaster().getDataBuffer();
        pixels = db.getData();

        canvas.setFocusable(true);
        canvas.requestFocus();
        canvas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_T) capEnabled = !capEnabled;
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) System.exit(0);
            }
        });
        canvas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {
                mousePressed = true;
            }
            public void mouseReleased(java.awt.event.MouseEvent e) {
                mousePressed = false;
            }
        });
        canvas.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
            public void mouseDragged(java.awt.event.MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        long fpsTimer = System.nanoTime();
        int frames = 0;
        final long targetFrameNanos = TARGET_FPS > 0 ? 1_000_000_000L / TARGET_FPS : 0L;

        start();
        while (frame.isDisplayable()) {
            long frameStart = System.nanoTime();

            update();

            java.awt.Graphics2D g = (java.awt.Graphics2D) canvas.getBufferStrategy().getDrawGraphics();
            g.drawImage(img, 0, 0, null);
            g.dispose();
            canvas.getBufferStrategy().show();

            frames++;
            if (frameStart - fpsTimer >= 1_000_000_000L) {
                double fps = frames * 1e9 / (frameStart - fpsTimer);
                frame.setTitle(String.format("360NOSCOPE — %dx%d — FPS: %.1f (target: %d) [%s]", WIDTH, HEIGHT, fps, TARGET_FPS, capEnabled ? "CAP" : "UNCAP"));
                currentFPS = fps;
                frames = 0;
                fpsTimer = frameStart;
            }

            if (capEnabled && targetFrameNanos > 0) {
                long frameEnd = System.nanoTime();
                long elapsed = frameEnd - frameStart;
                long sleepNanos = targetFrameNanos - elapsed;
                if (sleepNanos > 0) {
                    long sleepMillis = sleepNanos / 1_000_000L;
                    int sleepNanosRemainder = (int) (sleepNanos % 1_000_000L);
                    try {
                        Thread.sleep(sleepMillis, sleepNanosRemainder);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    Thread.yield();
                }
            } else {
                Thread.yield();
            }
        }
    }

    static vec2 v1;
    static vec2 v2;
    static vec2 v3;
    static vec2 v4;
    static int rand1;
    static int rand2;
    static int rand3;
    static int rgb;

    static void start(){
        Main.rgb = Main.rgb((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
        int spacing = 100;
        Main.v1 = new vec2((float)(spacing + (WIDTH-spacing)*Math.random()), (float) (spacing + (HEIGHT-spacing)*Math.random()));
        Main.v2 = new vec2((float)(spacing + (WIDTH-spacing)*Math.random()), (float) (spacing + (HEIGHT-spacing)*Math.random()));
        Main.v3 = new vec2((float)(spacing + (WIDTH-spacing)*Math.random()), (float) (spacing + (HEIGHT-spacing)*Math.random()));
        Main.v4 = new vec2((float)(spacing + (WIDTH-spacing)*Math.random()), (float) (spacing + (HEIGHT-spacing)*Math.random()));
        rand1 = (int)((WIDTH-spacing)*Math.random());
        rand2 = (int)((WIDTH-spacing)*Math.random());
        rand3 = (int)((WIDTH-spacing)*Math.random());
    }

    static void update() {
        int t = (int) (System.nanoTime() / 1_000_000L);


        int[] px = pixels;
        clear(rgb(0, 0, 0));


        float time = t * 0.001f;

        Main.v1.set((float)(WIDTH/2 + Math.sin(time) * 200), (float)(HEIGHT/2 + Math.cos(time) * 150));
        Main.v2.set((float)(WIDTH/2 + Math.sin(time + 2) * 180), (float)(HEIGHT/2 + Math.cos(time + 2) * 130));
        Main.v3.set((float)(WIDTH/2 + Math.sin(time + 4) * 220), (float)(HEIGHT/2 + Math.cos(time + 4) * 170));

        Draw.triangle(Main.v1, Main.v2, Main.v3, rgb((int)(128 + 127 * Math.sin(time)), (int)(128 + 127 * Math.cos(time)), 255));

        Draw.rect(new vec2(100 + (int)(50 * Math.sin(time * 2)), 100 + (int)(50 * Math.cos(time * 2))), 200, 150, rgb(0, 255, (int)(128 + 127 * Math.sin(time * 3))));

        Main.v4.set((float)(WIDTH/2 + Math.sin(time * 1.5) * 300), (float)(HEIGHT/2 + Math.cos(time * 1.5) * 200));
        Draw.circle(Main.v4, 20 + (int)(10 * Math.sin(time * 4)), rgb(255, (int)(128 + 127 * Math.cos(time)), 0));

        Draw.line(new vec2(0, 0), new vec2(WIDTH, HEIGHT), rgb(255, 0, 0));

        Draw.line(new vec2(0, HEIGHT), new vec2(WIDTH, HEIGHT), rgb(0, 255, 0));

        Draw.line(new vec2(WIDTH/4, HEIGHT/2), new vec2(WIDTH/2, HEIGHT/4), rgb(0, 0, 255));
        Draw.line(new vec2(WIDTH/2, HEIGHT/4), new vec2(3*WIDTH/4, HEIGHT/2), rgb(0, 0, 255));

        Draw.line(new vec2(WIDTH/8, HEIGHT), new vec2(WIDTH/8 + 50, HEIGHT - 100), rgb(139, 69, 19));
        Draw.line(new vec2(WIDTH/8 + 50, HEIGHT - 100), new vec2(WIDTH/8 + 100, HEIGHT), rgb(139, 69, 19));

        Draw.line(new vec2(7*WIDTH/8, HEIGHT), new vec2(7*WIDTH/8 - 50, HEIGHT - 80), rgb(139, 69, 19));
        Draw.line(new vec2(7*WIDTH/8 - 50, HEIGHT - 80), new vec2(7*WIDTH/8 - 100, HEIGHT), rgb(139, 69, 19));

        Draw.line(new vec2(WIDTH/2 - 20, HEIGHT - 50), new vec2(WIDTH/2 + 20, HEIGHT - 50), rgb(255, 255, 0));
        Draw.line(new vec2(WIDTH/2, HEIGHT - 70), new vec2(WIDTH/2, HEIGHT - 30), rgb(255, 255, 0));

        Draw.line(new vec2(100, 100), new vec2(150, 50), rgb(255, 0, 255));
        Draw.line(new vec2(150, 50), new vec2(200, 100), rgb(255, 0, 255));

        Draw.line(new vec2(WIDTH - 200, 150), new vec2(WIDTH - 150, 100), rgb(0, 255, 255));
        Draw.line(new vec2(WIDTH - 150, 100), new vec2(WIDTH - 100, 150), rgb(0, 255, 255));

        for (int i = 0; i < 20; i++) {
            float angle = time * 2 + i * 0.5f;
            int x = (int)(WIDTH/2 + Math.cos(angle) * 100);
            int y = (int)(HEIGHT/2 + Math.sin(angle) * 100);
            Draw.circle(new vec2(x, y), 5, rgb((int)(128 + 127 * Math.sin(time + i)), (int)(128 + 127 * Math.cos(time + i)), 255));
        }

        for (int i = 0; i < 10; i++) {
            int x1 = (int)(Math.sin(time * 3 + i) * WIDTH/4 + WIDTH/2);
            int y1 = (int)(Math.cos(time * 3 + i) * HEIGHT/4 + HEIGHT/2);
            int x2 = (int)(Math.sin(time * 3 + i + 1) * WIDTH/4 + WIDTH/2);
            int y2 = (int)(Math.cos(time * 3 + i + 1) * HEIGHT/4 + HEIGHT/2);
            Draw.line(new vec2(x1, y1), new vec2(x2, y2), rgb(255, (int)(128 + 127 * Math.sin(time + i * 0.1)), 0));
        }

        Draw.triangle(new vec2(WIDTH/4 + (int)(50 * Math.sin(time * 1.2)), HEIGHT/4 + (int)(50 * Math.cos(time * 1.2))), new vec2(WIDTH/4 + 50, HEIGHT/4 - 50), new vec2(WIDTH/4 - 50, HEIGHT/4 + 50), rgb(255, 0, 255));

        Draw.circle(new vec2(WIDTH * 3/4, HEIGHT/4), 40 + (int)(20 * Math.sin(time * 2)), rgb(0, 255, 255));

        for (int i = 0; i < 15; i++) {
            int x = (int)(WIDTH * 0.1 + i * WIDTH * 0.06);
            int y = (int)(HEIGHT * 0.8 + 20 * Math.sin(time * 4 + i * 0.2));
            Draw.rect(new vec2(x, y), 10, 20, rgb((int)(255 * Math.sin(time + i)), 100, 200));
        }

        if (mousePressed){
            Draw.circle(new vec2(mouseX, mouseY), 30 + (int)(20 * Math.sin(time * 5)), rgb(255, 255, 0));
            for (int i = 0; i < 5; i++) {
                int dx = (int)(Math.cos(i * 1.256) * 50);
                int dy = (int)(Math.sin(i * 1.256) * 50);
                Draw.line(new vec2(mouseX, mouseY), new vec2(mouseX + dx, mouseY + dy), rgb(255, 255, 0));
            }
        }

        Draw.text("Mouse: " + mouseX + "," + mouseY, 10, 20, rgb(255,255,255));
        Draw.text("Pressed: " + mousePressed, 10, 40, rgb(255,255,255));
        Draw.text("FPS: " + String.format("%.1f", currentFPS), 10, 60, rgb(255,255,255));
        Draw.text("YMCMB2008", 10, 80, rgb(255,255,255));

    }



    public static int rgb(int r, int g, int b) {
        return ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);
    }

    public static void setPixel(int x, int y, int rgb) {
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return;
        pixels[y * WIDTH + x] = rgb & 0xFFFFFF;
    }



    public static void clear(int rgb) {
        int color = rgb & 0xFFFFFF;
        java.util.Arrays.fill(pixels, color);
    }

}
