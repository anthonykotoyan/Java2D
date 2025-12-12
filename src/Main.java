public class Main {

    public static int WIDTH;
    public static int HEIGHT;
    static final int TARGET_FPS = 60;

    static volatile boolean capEnabled = false;
    public static int mouseX, mouseY;
    public static vec2 mouse = new vec2(0,0);
    public static boolean mousePressed;
    public static double currentFPS;
    public static double deltaTime;
    static long lastTime;

    public static int[] pixels;
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
                mouse.set(mouseX,mouseY);

            }
            public void mouseDragged(java.awt.event.MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                mouse.set(mouseX,mouseY);
            }
        });

        long fpsTimer = System.nanoTime();
        int frames = 0;
        final long targetFrameNanos = TARGET_FPS > 0 ? 1_000_000_000L / TARGET_FPS : 0L;

        start();
        lastTime = System.nanoTime();
        while (frame.isDisplayable()) {
            long frameStart = System.nanoTime();

            update();
            mouse.set(mouseX,mouseY);
            long currentTime = System.nanoTime();
            deltaTime = (currentTime - lastTime) / 1_000_000_000.0;
            lastTime = currentTime;
            
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

    static void start(){
    }

    static void update() {
        clear(0);
    
    }

    public static int rgb(int r, int g, int b) {
        return ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);
    }


    public static void clear(int rgb) {
        int color = rgb & 0xFFFFFF;
        java.util.Arrays.fill(pixels, color);
    }

}
